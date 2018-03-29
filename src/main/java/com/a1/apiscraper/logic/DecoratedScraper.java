package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;

import java.util.HashMap;


public abstract class DecoratedScraper extends APIScraper {
    private APIScraper apiScraper;

    public DecoratedScraper(API api, APIScraper apiScraper) {
        super(api, apiScraper.getScrapeBehavior());
        this.apiScraper = apiScraper;
    }

    public HashMap<Endpoint, Result> scrape() {
        HashMap<Endpoint, Result> results = apiScraper.scrape();
        return results;
    }

    public abstract void communicate ();
}
