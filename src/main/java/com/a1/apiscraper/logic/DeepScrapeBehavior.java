package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.HyperMedia;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.service.RepositoryService;
import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DeepScrapeBehavior implements ScrapeBehavior {

    @Override
    public HashMap<Endpoint, Result> scrape(API api) {
        HashMap<Endpoint, Result> endpointResults = new HashMap<>();
        Map<Long, Endpoint> endpoints =  api.getEndpoints();
        for (Map.Entry<Long, Endpoint> endpoint : endpoints.entrySet() ) {
            Result result = new Result();

            HttpResponse<String> resultString = null;
            try {
                resultString = Unirest.get(api.getBaseUrl() + endpoint.getValue().getName()).asString();
            } catch (UnirestException e) {
                e.printStackTrace();
            }
            String output = resultString.getBody();
            result.setResult(output);
            Date date = Date.from(Instant.now());
            result.setDateTimeStamp(date);

            UrlDetector parser = new UrlDetector(output, UrlDetectorOptions.Default);

            for (Url url : parser.detect()) {
                HyperMedia hyperMedia = new HyperMedia();
                hyperMedia.setURL(url.getFullUrl());
                result.addFoundHyperMedia(hyperMedia);
            }

            endpointResults.put(endpoint.getValue(), result );
        }
        return endpointResults;
    }
}
