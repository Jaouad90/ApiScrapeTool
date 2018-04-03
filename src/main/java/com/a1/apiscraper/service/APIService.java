package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIMemento;

import javax.transaction.Transactional;

public interface APIService {
    @Transactional
    API saveAPIModel(API api);
    @Transactional
    API restoreAPIFromMemento(API api, APIMemento apiMemento);
}
