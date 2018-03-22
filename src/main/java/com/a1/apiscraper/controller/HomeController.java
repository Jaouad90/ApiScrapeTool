package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.service.RepositoryServiceInterface;
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
    private RepositoryServiceInterface repositoryService;

    private ArrayList<Endpoint> endpoints = new ArrayList<>();

    @GetMapping
    @Transactional
    public ModelAndView home() {
        Iterable<API> apis = repositoryService.getAllAPIs();
        return new ModelAndView("home/home", "apis", apis);
    }


}
