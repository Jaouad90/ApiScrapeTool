package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.*;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.a1.apiscraper.repository.ResultRepository;
import com.a1.apiscraper.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APIManager {
    @Autowired
    private RepositoryService repositoryService;
    private ArrayList<API> apiArrayList;


    public APIManager() {
        apiArrayList = (ArrayList<API>) repositoryService.getAllAPIs();
    }

    @Transactional
    public void doScrape() {
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
