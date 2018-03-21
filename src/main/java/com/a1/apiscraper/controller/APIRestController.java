package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.service.RepositoryService;
import com.a1.apiscraper.service.RepositoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
public class APIRestController {

    @Autowired
    private RepositoryServiceInterface repositoryService;

    public APIRestController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public Iterable list(){
        Iterable apiList = repositoryService.getAllAPIs();
        return apiList;
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public API showAPI(@PathVariable Long id){
        API api = repositoryService.getSingleAPI(id);
        return api;
    }

}
