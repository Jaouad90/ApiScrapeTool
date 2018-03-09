package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.logic.DecoratedScraper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class APIScraper  {

    private API api;

    APIScraper(API api) {
        this.api = api;
    }

    public HashMap<Endpoint, String> scrape() {
        HashMap<Endpoint, String> endpointResults = new HashMap<Endpoint, String>();
        Map<Long, Endpoint> endpoints =  api.getEndpoints();
        for (Map.Entry<Long, Endpoint> endpoint : endpoints.entrySet() ) {
            HttpResponse<String> result = null;
            try {
                result = Unirest.get(api.getBaseUrl() + endpoint.getValue().getName()).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            String output = result.getBody();
            endpointResults.put(endpoint.getValue(), output );
        }
        return endpointResults;
    }
}
