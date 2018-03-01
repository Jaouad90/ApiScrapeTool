package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.mashape.unirest.http.Unirest;

import java.util.HashMap;

public class APIscraper {
    private API api;

    public APIscraper(API api) {
        this.api = api;
    }

    public void scrape() {} {
        HashMap<String, String> endpointResults = new HashMap<String, String>();
       for (String endpoint : api.getEndpoints()) {
           endpointResults.put(endpoint, Unirest.get(api.getBaseUrl() + endpoint).toString());
           System.out.println(Unirest.get(api.getBaseUrl()+ endpoint).toString());
       }
    }
}
