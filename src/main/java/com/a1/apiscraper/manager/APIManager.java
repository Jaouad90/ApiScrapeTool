package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.logic.APIscraper;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import com.a1.apiscraper.repository.ResultRepository;
import com.fasterxml.jackson.databind.util.TypeKey;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class APIManager {
    private ArrayList<API> apiArrayList;


    @Autowired
    private APIRepository apiRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private EndpointRepository endpointRepository;

    public APIManager(APIRepository apiRepository, ResultRepository resultRepository, EndpointRepository endpointRepository) {
        this.apiRepository = apiRepository;
        this.resultRepository = resultRepository;
        this.endpointRepository = endpointRepository;
        apiArrayList = (ArrayList<API>) apiRepository.findAll();
    }

    public void doScrape() {
        ArrayList<Result> results = new ArrayList<>();
        for(API api : apiArrayList) {
            APIscraper tempScraper = new APIscraper(api);
            HashMap<Endpoint, String> hash = tempScraper.scrape();
            for (Endpoint endpoint: hash.keySet()) {

                Result result = new Result();
                result.setResult(hash.get(endpoint));
                results.add(result);
                endpoint.setResults(results);

            }
        }
        resultRepository.save(results);
    }
}
