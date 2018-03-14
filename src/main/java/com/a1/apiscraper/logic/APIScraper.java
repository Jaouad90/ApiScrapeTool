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
