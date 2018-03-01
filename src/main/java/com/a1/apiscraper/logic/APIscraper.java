package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.util.HashMap;

public class APIscraper {
    private API api;

    public APIscraper(API api) {
        this.api = api;
    }

    public void scrape() {
        HashMap<String, String> endpointResults = new HashMap<String, String>();
        for (String endpoint : api.getEndpoints()) {
            HttpResponse<String> result = null;
            try {
                result = Unirest.get(api.getBaseUrl() + endpoint).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            String output = result.getBody();
            endpointResults.put(endpoint, output );
            System.out.println(output);
       }
    }
}
