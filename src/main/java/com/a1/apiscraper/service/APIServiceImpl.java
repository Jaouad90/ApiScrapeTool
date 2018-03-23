package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.CareTaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Service
public class APIServiceImpl implements APIService {

    @Autowired
    RepositoryService repositoryService;
    DateTimeFormatter formatter;

    public APIServiceImpl()
    {
        formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.ENGLISH)
                .withZone( ZoneId.systemDefault() );
    }

    /**
     * Saves New api or updates existing API
     * @param apiModel
     * @return saved api
     */
    public API saveAPI(API apiModel){
        API api = null;
        if (apiModel.getId() == null) {
            //Create API
            APIConfig apiConfig = apiModel.getConfig();
            //api.getConfig().setDecorators(apiModel.getConfig().getDecorators());
            repositoryService.saveAPIConfig(apiConfig);
            apiModel.setConfig(apiConfig);

            repositoryService.saveAPI(apiModel);
            api = apiModel;
        } else {
            //Update API
            api = repositoryService.getSingleAPI(apiModel.getId());
            api.setEndpoints(apiModel.getEndpoints());
            api.getConfig().setScrapeBehavior(apiModel.getConfig().getScrapeBehavior());
            api.getConfig().setDecorators(apiModel.getConfig().getDecorators());
            api.setName(apiModel.getName());
            api.setBaseUrl(apiModel.getBaseUrl());
            String out = formatter.format(Instant.now());
            api.setState("" + out);
            api.setTimeInterval(apiModel.timeInterval);
            CareTaker careTaker = api.getCareTaker();
            careTaker.add(api.saveStateToMemente());
            repositoryService.saveAPI(api);
        }
        return api;
    }

}
