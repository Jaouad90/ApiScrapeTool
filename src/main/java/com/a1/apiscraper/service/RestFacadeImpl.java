package com.a1.apiscraper.service;


import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.manager.APIManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class RestFacadeImpl implements RestFacade {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    APIManager apiManager;
    @Autowired
    APIService apiService;

    public HashMap<String, Result> scrapeSingleApi(long apiID){
        API api = repositoryService.getSingleAPI(apiID);
        apiManager.scrapeSingleAPI(api);
        return apiService.getLatestResultsForEndpoints(api.getEndpoints(), api.getBaseUrl());
    }
}