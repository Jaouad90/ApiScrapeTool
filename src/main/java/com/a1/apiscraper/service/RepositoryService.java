package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.*;
import org.springframework.stereotype.Repository;
import com.a1.apiscraper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
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
    Result getResultByID(Long id);
    ScrapeBehavior getScrapeBehavior(Long id);
    APIConfig getAPIConfig(Long id );
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
}
