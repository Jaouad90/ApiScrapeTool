package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.*;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface RepositoryService {

//<!--
//All
    //<!--
    //Find All
    Iterable<Decorator> getAllDecorators();
    Iterable<ScrapeBehavior> getAllScrapeBehaviors();
    Iterable<Role> getAllRoles();
    Iterable<API> getAllAPIs();
    List getAllResultsForApiBetween(Long id, Date from, Date till);
    Iterable<TimeInterval> getAllTimeIntervals();
    //-->
//-->

//<!--
//Single
    //<!--
    //Find Single
    API getSingleAPI(Long id);
    User getUserByUsername(String username);
    ScrapeBehavior getScrapeBehavior(Long id);
    APIConfig getAPIConfig(Long id );
    Result getResultByID(Long id);
    List getLastResultsByAPI(API api);
    //-->

    //<!--
    // Save single entity
    void saveAPIConfig(APIConfig apiConfig);
    void saveAPI(API api);
    void saveUser(User user);
    void saveHyperMedia(HyperMedia hyperMedia);
    void saveResult(Result result);
    void saveEndpoint(Endpoint endpoint);
    void saveRole(Role role);
    void saveDecorator(Decorator decorator);
    void saveScrapeBehavior(ScrapeBehavior scrapeBehavior);
    void saveTimeInterval(TimeInterval timeInterval);

    //-->
//-->

//<!--
//Some
    //<!--
    //Find some
    Result getLatestResultForEndpoint(Long id);
    //-->
//-->
}
