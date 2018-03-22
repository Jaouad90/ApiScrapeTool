package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Decorator;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.logic.APIScraper;
import com.a1.apiscraper.logic.DecoratorFactory;
import com.a1.apiscraper.logic.SimpleAPIscraper;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.a1.apiscraper.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalTime;
import java.util.*;

@EnableScheduling
@EnableAsync
@Service
public class APIManager {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HyperMediaRepository hyperMediaRepository;

    @Transactional
    @Scheduled(cron = "0 0/30 * * * ?")
//    @Scheduled(cron = "0/30 * * * * ?")
    public void scrapeTask() {
        ArrayList<API> apiArrayList = (ArrayList<API>) repositoryService.getAllAPIs();
        doScrape(getApisToScrape(apiArrayList));
    }

    @Transactional
    public void doScrape(ArrayList<API> apiArrayList) {
        for(API api : apiArrayList) {
            APIScraper tempScraper = new SimpleAPIscraper(api);
            DecoratorFactory decoratorFactory = new DecoratorFactory();
            for(Decorator decorator : api.getConfig().getDecorators()){
                tempScraper = decoratorFactory.getDecorator(decorator.getName(), tempScraper);
            }

            HashMap<Endpoint, String> hash = tempScraper.scrape();
            Date date = Date.from(Instant.now());
            for (Endpoint endpoint: hash.keySet()) {
                Map<Long, Result> results = new HashMap<>();
                Result result = new Result();
                result.setDateTimeStamp(date);
                result.setResult(hash.get(endpoint));
                repositoryService.saveResult(result);
                endpoint.addResult(result);
            }
        }
    }

    private ArrayList<API> getApisToScrape(ArrayList<API> apis) {
        ArrayList<API> apiArrayListToScrape = new ArrayList<>();
        for (API api : apis) {
            List<LocalTime> timeList = api.getTimeInterval().getTimeList();
            int i = 0;
            for (LocalTime localTime : timeList) {
                i++;
                if (i < timeList.size()) {
                    if (timeList.get(i).isBefore(LocalTime.now().plusMinutes(30)) && timeList.get(i).isAfter(LocalTime.now())) {
                        apiArrayListToScrape.add(api);
                    }
                }
            }
        }
        return apiArrayListToScrape;
    }
}
