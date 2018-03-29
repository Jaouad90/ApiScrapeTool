package com.a1.apiscraper.service;


import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
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

    //TODO: facade zelf opnieuw oplaten halen van data "voor de lekker"
    public HashMap<Endpoint, Result> scrapeSingleApi(long apiID){
        API api = repositoryService.getSingleAPI(apiID);
        return apiManager.scrapeSingleAPI(api);
        //return repositoryService.getLastResultsByAPI(api);
    }
}
