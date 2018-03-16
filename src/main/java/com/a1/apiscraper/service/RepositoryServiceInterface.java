package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.Decorator;
import com.a1.apiscraper.domain.ScrapeBehavior;

public interface RepositoryServiceInterface {

//<!--
//All
    //<!--
    //Find All
    Iterable<Decorator> getAllDecorators();
    Iterable<ScrapeBehavior> getAllScrapeBehaviors();
    //-->
//-->

//<!--
//Single
    //<!--
    //Find Single
    API getSingleAPI(Long id);
    //-->

    //<!--
    // Save single entity
    void saveAPIConfig(APIConfig apiConfig);
    void saveAPI(API api);
    //-->


//-->
}
