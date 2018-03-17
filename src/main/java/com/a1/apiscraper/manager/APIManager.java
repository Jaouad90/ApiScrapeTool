package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.*;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.a1.apiscraper.repository.ResultRepository;
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
    private ArrayList<API> apiArrayList;


    @Autowired
    private APIRepository apiRepository;

    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private HyperMediaRepository hyperMediaRepository;

    public APIManager(APIRepository apiRepository, ResultRepository resultRepository, EndpointRepository endpointRepository, HyperMediaRepository hyperMediaRepository) {
        this.apiRepository = apiRepository;
        this.resultRepository = resultRepository;
        this.endpointRepository = endpointRepository;
        this.hyperMediaRepository = hyperMediaRepository;
        apiArrayList = new ArrayList<>();
    }

    @org.springframework.transaction.annotation.Transactional
    @Scheduled(cron = "*/30 * * * * *")
    public void CheckScrape() {
        for (API api : apiRepository.findAll()) {
            List<LocalTime> timeList = api.getTimeInterval().getTimeList();
            int i = 0;
            for (LocalTime localTime : timeList) {
                i++;
             if (i < timeList.size())  {
               if (LocalTime.now().isAfter(localTime) &&  LocalTime.now().isBefore(timeList.get(i))) {
                  apiArrayList.add(api);
                 }
               }
            }
        }
        doScrape(apiArrayList);
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
                resultRepository.save(result);
                result.setResult(hash.get(endpoint));
                results.put(result.getId(), result);
                endpoint.setResults(results);
                endpointRepository.save(endpoint);
            }
            this.apiArrayList.clear();
        }
    }
}
