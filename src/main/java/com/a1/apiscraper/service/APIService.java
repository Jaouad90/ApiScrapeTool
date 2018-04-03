package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIMemento;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

public interface APIService {
    @Transactional
    API saveAPIModel(API api);
    @Transactional
    API restoreAPIFromMemento(API api, APIMemento apiMemento);
    HashMap<String, Result> getLatestResultsForEndpoints(Map<Long, Endpoint> endpoints, String baseUrl);
}
