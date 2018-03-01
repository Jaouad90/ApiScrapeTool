package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.logic.APIscraper;
import com.a1.apiscraper.repository.APIRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class APIManager {
    private ArrayList<API> apiArrayList = new ArrayList<>();


    @Autowired
    private APIRepository apiRepository;

    public APIManager(APIRepository apiRepository) {
        this.apiRepository = apiRepository;
        apiArrayList = (ArrayList<API>) apiRepository.findAll();
        doScrape();
    }

    public void doScrape() {
        for(API api : apiArrayList) {
            APIscraper tempScraper = new APIscraper(api);
            tempScraper.scrape();

        }
    }
}
