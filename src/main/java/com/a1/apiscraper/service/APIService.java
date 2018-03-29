package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.APIMemento;
import com.a1.apiscraper.domain.CareTaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

public interface APIService {
    @Transactional
    API saveAPIModel(API api);
    @Transactional
    API restoreAPIFromMemento(API api, APIMemento apiMemento);
}
