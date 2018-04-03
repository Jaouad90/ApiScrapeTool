package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.service.RepositoryService;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.a1.apiscraper.ApiscraperApplication.getChainOfLoggers;

public class NormalScrapeBehavior implements ScrapeBehavior {

    AbstractLogger loggerChain = getChainOfLoggers();
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
                loggerChain.logMessage(AbstractLogger.WARNING, "Unirest exception tijdens ophalen : " + e.getMessage());
            }
            String output =  "";
            if (resultString != null) {
                output = resultString.getBody();
            }
            result.setResult(output);
            Date date = Date.from(Instant.now());
            result.setDateTimeStamp(date);

            endpointResults.put(endpoint.getValue(), result);
        }
        return endpointResults;
    }

    @Override
    public void saveResults(HashMap<Endpoint, Result> results, RepositoryService repositoryService) {
        for (Map.Entry<Endpoint, Result> endpointResultEntry: results.entrySet()) {
            Result result = endpointResultEntry.getValue();

            repositoryService.saveResult(result);

            Endpoint endpoint = endpointResultEntry.getKey();
            endpoint.addResult(result);
            repositoryService.saveEndpoint(endpoint);
        }
    }
}
