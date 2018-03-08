package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;

public abstract class APIScraperDecorator extends APIScraper {

    public APIScraperDecorator(API api) {
        super(api);
    }

}
