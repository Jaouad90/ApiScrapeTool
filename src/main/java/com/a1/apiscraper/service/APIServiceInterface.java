package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;

import javax.transaction.Transactional;

public interface APIServiceInterface {
    @Transactional
    API saveAPI(API api);
}
