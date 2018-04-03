package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;
import com.a1.apiscraper.service.RepositoryService;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public abstract class APIScraper  {
    private AbstractLogger loggerChain = getLoggerChain();
    private API api;

    private ScrapeBehavior scrapeBehavior;

    APIScraper(API api) {
        this.api = api;
        this.scrapeBehavior = new NormalScrapeBehavior();
    }

    APIScraper(API api, ScrapeBehavior scrapeBehavior) {
        this.api = api;
        this.scrapeBehavior = scrapeBehavior;
    }

    public HashMap<Endpoint, Result> scrape() {
        if (scrapeBehavior != null) {
            return scrapeBehavior.scrape(api);
        } else {
            try {
                throw new Exception("ScrapeBehavior is not set");
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new HashMap<>();
            }
        }
    }

    public void saveResults(HashMap<Endpoint, Result> results, RepositoryService repositoryService) {
        if (scrapeBehavior != null) {
            scrapeBehavior.saveResults(results, repositoryService);
        } else {
            try {
                throw new Exception("ScrapeBehavior is not set");
            } catch (Exception e) {
                loggerChain.logMessage(AbstractLogger.WARNING, e.getMessage());
            }
        }
    }
}
