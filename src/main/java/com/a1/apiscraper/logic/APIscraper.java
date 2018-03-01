package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;

public class APIscraper {
    private API api;

    public APIscraper(API api) {
        this.api = api;
    }

    public HashMap<Endpoint, String> scrape() {
        HashMap<Endpoint, String> endpointResults = new HashMap<Endpoint, String>();
        for (Endpoint endpoint : api.getEndpoints()) {
            HttpResponse<String> result = null;
            try {
                result = Unirest.get(api.getBaseUrl() + endpoint.getName()).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            String output = result.getBody();
            endpointResults.put(endpoint, output );
       }
       return endpointResults;
    }
}
