package com.a1.apiscraper.logic;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;

import java.util.HashMap;

public interface ScrapeBehavior {
    HashMap<Endpoint, String> scrape(API api);
}
