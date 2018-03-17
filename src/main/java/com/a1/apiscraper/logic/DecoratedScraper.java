package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.HashMap;


public abstract class DecoratedScraper extends APIScraper {

    public DecoratedScraper(API api) {
        super(api);
    }

    public HashMap<Endpoint, String> scrape() {
        HashMap<Endpoint, String> results = super.scrape();
        communicate();
        return results;
    }

    public abstract void communicate ();
}
