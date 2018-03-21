package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.ScrapeBehavior;
import com.a1.apiscraper.service.RepositoryService;
import com.a1.apiscraper.service.RepositoryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api/config")
public class ConfigRestController {
    @Autowired
    private RepositoryServiceInterface repositoryService;

    public ConfigRestController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public APIConfig showAPIConfig(@PathVariable Long id){
        return repositoryService.getAPIConfig(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public @ResponseBody APIConfig updateAPIConfig (@PathVariable("id") Long id, @RequestParam Long scrapeId) {
        APIConfig config = repositoryService.getAPIConfig(id);
        ScrapeBehavior scrapeBehavior = repositoryService.getScrapeBehavior(scrapeId);
        config.setScrapeBehavior(scrapeBehavior);
        repositoryService.saveAPIConfig(config);
        return config;
    }

}
