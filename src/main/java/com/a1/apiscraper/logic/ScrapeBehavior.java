package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.service.RepositoryService;

import java.util.HashMap;

public interface ScrapeBehavior {
    HashMap<Endpoint, Result> scrape(API api);
    void saveResults(HashMap<Endpoint, Result> results, RepositoryService repositoryService);
}
