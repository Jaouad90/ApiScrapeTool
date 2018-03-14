package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.HyperMedia;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class DeepScrapeBehavior implements ScrapeBehavior {

    @Autowired
    HyperMediaRepository hyperMediaRepository;

    public DeepScrapeBehavior(HyperMediaRepository hyperMediaRepository) {
        this.hyperMediaRepository = hyperMediaRepository;
    }

    @Override
    public HashMap<Endpoint, String> scrape(API api) {
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

            UrlDetector parser = new UrlDetector(output, UrlDetectorOptions.Default);

            for (Url url : parser.detect()) {
                HyperMedia hyperMedia = new HyperMedia();
                hyperMedia.setURL(url.getFullUrl());
                hyperMediaRepository.save(hyperMedia);
                endpoint.getValue().addFoundHyperMedia(hyperMedia);
            }

            endpointResults.put(endpoint.getValue(), output );
        }
        return endpointResults;
    }
}
