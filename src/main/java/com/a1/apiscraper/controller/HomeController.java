package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.manager.APIManager;
import com.a1.apiscraper.repository.*;
import com.a1.apiscraper.service.UserService;
import com.a1.apiscraper.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private APIRepository apiRepository;
    @Autowired
    private EndpointRepository endpointRepository;
    @Autowired
    private ResultRepository resultRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserServiceImpl userService;


    private ArrayList<Endpoint> endpoints = new ArrayList<>();


    public HomeController(APIRepository apiRepository, EndpointRepository endpointRepository, ResultRepository resultRepository, RoleRepository roleRepository, UserServiceImpl userService) {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
        this.resultRepository = resultRepository;
        this.roleRepository = roleRepository;
        this.userService = userService;

    }

    @GetMapping
    @Transactional
    public ModelAndView home() {
        API api = new API();
        api.setBaseUrl("https://api.coinmarketcap.com/v1/");
        Endpoint endpoint = new Endpoint();
        endpoint.setName("ticker/");
        endpoint.setApi(api);
        endpoints.add(endpoint);
        endpointRepository.save(endpoint);

        Endpoint endpoint2 = new Endpoint();
        endpoint2.setName("ticker/bitcoin");
        endpoint2.setApi(api);
        endpoints.add(endpoint2);
        endpointRepository.save(endpoint2);

        api.setEndpoints(endpoints);
        apiRepository.save(api);

        APIManager apiManager = new APIManager(apiRepository, resultRepository, endpointRepository);
        apiManager.doScrape();
        return new ModelAndView("home/home");
    }
    @GetMapping("detail")
    @Transactional
    public ModelAndView detail() {

        return new ModelAndView("home/detail");
    }

}
