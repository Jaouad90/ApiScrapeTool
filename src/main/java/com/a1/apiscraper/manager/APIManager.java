package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.*;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.a1.apiscraper.repository.ResultRepository;
import com.a1.apiscraper.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EnableScheduling
@EnableAsync
@Service
public class APIManager {
    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private HyperMediaRepository hyperMediaRepository;

    @org.springframework.transaction.annotation.Transactional
//    @Scheduled(cron = "0 0/30 * * * ?")
    @Scheduled(cron = "0/30 * * * * ?")
    public void CheckScrape() {
        ArrayList<API> apiArrayList = (ArrayList<API>) repositoryService.getAllAPIs();
        ArrayList<API> apiArrayListToScrape = new ArrayList<>();
        for (API api : apiArrayList) {
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
        doScrape(apiArrayListToScrape);
    }

    @org.springframework.transaction.annotation.Transactional
    public void doScrape(ArrayList<API> apiArrayList) {
        for(API api : apiArrayList) {
            APIScraper tempScraper = new SimpleAPIscraper(api);
            DecoratorFactory decoratorFactory = new DecoratorFactory();
            for(Decorator decorator : api.getConfig().getDecorators()){
                tempScraper = decoratorFactory.getDecorator(decorator.getName(), tempScraper);
            }

            HashMap<Endpoint, String> hash = tempScraper.scrape();
            for (Endpoint endpoint: hash.keySet()) {
                Map<Long, Result> results = new HashMap<>();
                Result result = new Result();
                repositoryService.saveResult(result);
                result.setResult(hash.get(endpoint));
                results.put(result.getId(), result);
                endpoint.setResults(results);
                repositoryService.saveEndpoint(endpoint);
            }
        }
    }
}
