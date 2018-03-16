package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.*;

public interface RepositoryServiceInterface {

//<!--
//All
    //<!--
    //Find All
    Iterable<Decorator> getAllDecorators();
    Iterable<ScrapeBehavior> getAllScrapeBehaviors();
    Iterable<Role> getAllRoles();
    Iterable<API> getAllAPIs();
    //-->
//-->

//<!--
//Single
    //<!--
    //Find Single
    API getSingleAPI(Long id);
    User getUserByUsername(String username);
    //-->

    //<!--
    // Save single entity
    void saveAPIConfig(APIConfig apiConfig);
    void saveAPI(API api);
    void saveUser(User user);
    void saveHashedUser(User user);
    void saveHyperMedia(HyperMedia hyperMedia);
    void saveResult(Result result);
    void saveEndpoint(Endpoint endpoint);
    void saveRole(Role role);
    void saveDecorator(Decorator decorator);
    void saveScrapeBehavior(ScrapeBehavior scrapeBehavior);
    //-->
//-->
}
