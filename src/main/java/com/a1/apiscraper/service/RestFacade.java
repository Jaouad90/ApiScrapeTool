package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.Result;

import java.util.HashMap;

public interface RestFacade {
    HashMap<String, Result> scrapeSingleApi(long apiID);
}
