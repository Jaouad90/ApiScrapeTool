package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class RepositoryService implements RepositoryServiceInterface {
    @Autowired
    private APIRepository apiRepository;
    @Autowired
    private EndpointRepository endpointRepository;
    @Autowired
    private ScrapeBehaviorRepository scrapeBehaviorRepository;
    @Autowired
    private APIConfigRepository apiConfigRepository;
    @Autowired
    private CareTakerRepository careTakerRepository;
    @Autowired
    private DecoratorRepository decoratorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private HyperMediaRepository hyperMediaRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private TimeIntervalRepository timeIntervalRepository;

    @Autowired
    private UserServiceImpl userService;

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

    public Iterable<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    public Iterable<API> getAllAPIs(){
        return apiRepository.findAll();
    }

    public Iterable<TimeInterval> getAllTimeIntervals() {
        return timeIntervalRepository.findAll();
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
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username);
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
    public void saveUser(User user) {
        userRepository.save(user);
    }
    public void saveHyperMedia(HyperMedia hyperMedia){
        hyperMediaRepository.save(hyperMedia);
    }
    public void saveResult(Result result){
        resultRepository.save(result);
    }
    public void saveEndpoint(Endpoint endpoint){
        endpointRepository.save(endpoint);
    }
    public void saveRole(Role role){
        roleRepository.save(role);
    }
    public void saveDecorator(Decorator decorator){
        decoratorRepository.save(decorator);
    }
    public void saveScrapeBehavior(ScrapeBehavior scrapeBehavior){
        scrapeBehaviorRepository.save(scrapeBehavior);
    }
    public void saveTimeInterval(TimeInterval timeInterval) {
        timeIntervalRepository.save(timeInterval);
    }
    //-->
//-->
}
