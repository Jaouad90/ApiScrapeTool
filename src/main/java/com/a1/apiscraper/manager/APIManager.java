package com.a1.apiscraper.manager;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.APIScraper;
import com.a1.apiscraper.logic.DeepScrapeBehavior;
import com.a1.apiscraper.logic.ScrapeBehavior;
import com.a1.apiscraper.logic.SimpleFactory;
import com.a1.apiscraper.logic.SimpleAPIscraper;
import com.a1.apiscraper.repository.HyperMediaRepository;
import com.a1.apiscraper.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalTime;
import java.util.*;

@EnableScheduling
@EnableAsync
@Service
public class APIManager {
    @Autowired
    private RepositoryService repositoryService;

    @Transactional
 //   @Scheduled(cron = "0 0/30 * * * ?")
    @Scheduled(cron = "0/30 * * * * ?")
    public void scrapeTask() {
        ArrayList<API> allAPIs = (ArrayList<API>) repositoryService.getAllAPIs();

        ArrayList<API> apiArrayListToScrape = new ArrayList<>();

        for (API api : allAPIs){
            if (checkAPIHasToBeScraped(api)) {
                apiArrayListToScrape.add(api);
            }
        }

        doScrape(apiArrayListToScrape);
    }

    @Transactional
    public void doScrape(ArrayList<API> apiArrayList) {
        SimpleFactory simpleFactory = new SimpleFactory();
        for(API api : apiArrayList) {
            APIScraper tempScraper = new SimpleAPIscraper(api);

            tempScraper.setScrapeBehavior(simpleFactory.getScrapeBehavior(api.getConfig().getScrapeBehavior().getName()));

            for(Decorator decorator : api.getConfig().getDecorators()){
                tempScraper = simpleFactory.getDecorator(decorator.getName(), tempScraper);
            }

            HashMap<Endpoint, Result> results = tempScraper.scrape();
            tempScraper.saveResults(results, repositoryService);
        }
    }

    @Transactional
    public HashMap<Endpoint, Result> scrapeSingleAPI(API api){
        SimpleFactory simpleFactory = new SimpleFactory();

        APIScraper tempScraper = new SimpleAPIscraper(api);

        tempScraper.setScrapeBehavior(simpleFactory.getScrapeBehavior(api.getConfig().getScrapeBehavior().getName()));

        for(Decorator decorator : api.getConfig().getDecorators()){
            tempScraper = simpleFactory.getDecorator(decorator.getName(), tempScraper);
        }

        HashMap<Endpoint, Result> results = tempScraper.scrape();
        tempScraper.saveResults(results, repositoryService);
        return results;
    }

    private boolean checkAPIHasToBeScraped(API api) {

        Iterator<LocalTime> timeList = api.getTimeInterval().getTimeList().iterator();

        while(timeList.hasNext()) {
            LocalTime localTime = timeList.next();

            if (localTime.isBefore(LocalTime.now().plusMinutes(30)) && localTime.isAfter(LocalTime.now())) {
                return true;
            }
        }

        return  false;
    }
}
