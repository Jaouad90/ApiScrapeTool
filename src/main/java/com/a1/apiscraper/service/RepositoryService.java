package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.Decorator;
import com.a1.apiscraper.domain.ScrapeBehavior;
import com.a1.apiscraper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class RepositoryService implements RepositoryServiceInterface {
    @Autowired
    APIRepository apiRepository;
    @Autowired
    EndpointRepository endpointRepository;
    @Autowired
    ScrapeBehaviorRepository scrapeBehaviorRepository;
    @Autowired
    APIConfigRepository apiConfigRepository;
    @Autowired
    CareTakerRepository careTakerRepository;
    @Autowired
    DecoratorRepository decoratorRepository;

    public RepositoryService(APIRepository apiRepository, EndpointRepository endpointRepository, CareTakerRepository careTakerRepository, DecoratorRepository decoratorRepository) {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
        this.careTakerRepository = careTakerRepository;
        this.decoratorRepository = decoratorRepository;
    }

//<!--
//All
    //<!--
    //Find All
    public Iterable<Decorator> getAllDecorators(){
        return decoratorRepository.findAll();
    }

    public Iterable<ScrapeBehavior> getAllScrapeBehaviors(){
        return scrapeBehaviorRepository.findAll();
    }
    //-->
//-->

//<!--
//Single
    //<!--
    //Find Single
    public API getSingleAPI(Long id){
        return apiRepository.findOne(id);
    }
    //-->

    //<!--
    // Save single entity
    public void saveAPIConfig(APIConfig apiConfig){
        apiConfigRepository.save(apiConfig);
    }

    public void saveAPI(API api) {
        apiRepository.save(api);
    }
}
