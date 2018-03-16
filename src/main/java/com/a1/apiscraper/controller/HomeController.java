package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.manager.APIManager;
import com.a1.apiscraper.repository.*;
import com.a1.apiscraper.service.RepositoryServiceInterface;
import com.a1.apiscraper.service.UserService;
import com.a1.apiscraper.service.UserServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private RepositoryServiceInterface repositoryService;

    private ArrayList<Endpoint> endpoints = new ArrayList<>();

    @GetMapping
    @Transactional
    public ModelAndView home() {
        Iterable<API> apis = repositoryService.getAllAPIs();
        APIManager apiManager = new APIManager();
        apiManager.doScrape();
        return new ModelAndView("home/home", "apis", apis);
    }


}
