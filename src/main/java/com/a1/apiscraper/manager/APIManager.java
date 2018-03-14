package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.*;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.a1.apiscraper.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        apiArrayList = (ArrayList<API>) apiRepository.findAll();
    }

    @Transactional
    public void doScrape() {
        for(API api : apiArrayList) {
            APIScraper tempScraper = new SimpleAPIscraper(api);

            DecoratorFactory decoratorFactory = new DecoratorFactory();
            for(Decorator decorator : api.getConfig().getDecorators()){
                tempScraper = decoratorFactory.getDecorator(decorator.getName(), tempScraper);
            }

            tempScraper.setScrapeBehavior(new DeepScrapeBehavior(hyperMediaRepository));

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
        }
    }
}
