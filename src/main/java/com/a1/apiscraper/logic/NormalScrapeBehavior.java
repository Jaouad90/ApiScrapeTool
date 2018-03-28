package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NormalScrapeBehavior implements ScrapeBehavior {
    @Override
    public HashMap<Endpoint, Result> scrape(API api) {
        HashMap<Endpoint, Result> endpointResults = new HashMap<>();
        Map<Long, Endpoint> endpoints =  api.getEndpoints();
        for (Map.Entry<Long, Endpoint> endpoint : endpoints.entrySet() ) {
            HttpResponse<String> resultString = null;

            Result result = new Result();

            try {
                resultString = Unirest.get(api.getBaseUrl() + endpoint.getValue().getName()).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }

            String output = resultString.getBody();
            result.setResult(output);
            Date date = Date.from(Instant.now());
            result.setDateTimeStamp(date);

            endpointResults.put(endpoint.getValue(), result);
        }
        return endpointResults;
    }
}
