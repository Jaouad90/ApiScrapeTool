package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/v1/api")
public class APIRestController {

    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/list", method= RequestMethod.GET)
    public Iterable APIList(){
        return repositoryService.getAllAPIs();
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public API showAPI(@PathVariable Long id){
        return repositoryService.getSingleAPI(id);
    }

    @RequestMapping(value = "/{id}/results", method= RequestMethod.GET)
    public Iterable showAPIResults(@RequestParam(value = "from", required = false) Long fromTS,
                              @RequestParam(value = "till", required = false) Long tillTS,
                              @PathVariable Long id) {
        Date fromDate;
        Date tillDate;

        if (fromTS == null) {
            fromDate = Date.from(Instant.ofEpochSecond(1514764800));
        } else {
            fromDate = Date.from(Instant.ofEpochSecond(fromTS));
        }

        if (tillTS == null) {
            tillDate = Date.from(Instant.now());
        } else {
            tillDate = Date.from(Instant.ofEpochSecond(tillTS));
        }

        return repositoryService.getAllResultsForApiBetween(id, fromDate, tillDate);

    }
}
