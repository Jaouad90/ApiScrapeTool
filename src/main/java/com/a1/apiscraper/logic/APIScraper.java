package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public abstract class APIScraper  {

    private API api;

    private ScrapeBehavior scrapeBehavior;

    APIScraper(API api) {
        this.api = api;
        this.scrapeBehavior = new NormalScrapeBehavior();
    }

    public HashMap<Endpoint, String> scrape() {
        if (scrapeBehavior != null) {
            return scrapeBehavior.scrape(api);
        } else {
            try {
                throw new Exception("ScrapeBehavior is not set");
            } catch (Exception e) {
                e.printStackTrace();
                return new HashMap<>();
            }
        }
    }
}
