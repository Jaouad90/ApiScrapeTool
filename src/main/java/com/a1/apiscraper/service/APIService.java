package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIConfig;
import com.a1.apiscraper.domain.CareTaker;
import com.a1.apiscraper.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Service
public class APIService implements APIServiceInterface{

    @Autowired
    APIRepository apiRepository;
    @Autowired
    EndpointRepository endpointRepository;
    @Autowired
    ScrapeBehaviorRepository scrapeBehaviorRepository;
    @Autowired
    APIConfigRepository apiConfigRepository;
    @Autowired
    CareTakerRepository careTakerRepository;
    @Autowired
    DecoratorRepository decoratorRepository;
    DateTimeFormatter formatter;

    public APIService(APIRepository apiRepository, EndpointRepository endpointRepository, CareTakerRepository careTakerRepository, DecoratorRepository decoratorRepository)
    {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
        this.careTakerRepository = careTakerRepository;
        this.decoratorRepository = decoratorRepository;
        formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                .withLocale( Locale.ENGLISH)
                .withZone( ZoneId.systemDefault() );
    }

    /**
     * Saves New api or updates existing API
     * @param api
     * @return saved api
     */
    public API saveAPI(API api){
        if (api.getId() == null) {
            //Create API
            APIConfig apiConfig = api.getConfig();
            apiConfigRepository.save(apiConfig);
            api.setConfig(apiConfig);
            return api;
        } else {
            //Update API
            api = apiRepository.findOne(api.getId());
            api.setEndpoints(api.getEndpoints());
            api.getConfig().setScrapeBehavior(api.getConfig().getScrapeBehavior());
            api.getConfig().setDecorators(api.getConfig().getDecorators());
            api.setName(api.getName());
            String out = formatter.format(Instant.now());
            api.setState("" + out);
            CareTaker careTaker = api.getCareTaker();
            careTaker.add(api.saveStateToMemente());
            apiRepository.save(api);
            return api;
        }
    }

}
