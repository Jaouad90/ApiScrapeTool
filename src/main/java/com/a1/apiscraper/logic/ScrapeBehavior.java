package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.domain.Result;

import java.util.HashMap;

public interface ScrapeBehavior {
    HashMap<Endpoint, Result> scrape(API api);
}
