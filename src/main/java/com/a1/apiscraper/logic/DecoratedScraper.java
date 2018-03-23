package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;

import java.util.HashMap;


public abstract class DecoratedScraper extends APIScraper {
    private APIScraper apiScraper;

    public DecoratedScraper(API api, APIScraper apiScraper) {
        super(api);
        this.apiScraper = apiScraper;
    }

    public HashMap<Endpoint, String> scrape() {
        HashMap<Endpoint, String> results = apiScraper.scrape();
        return results;
    }

    public abstract void communicate ();
}
