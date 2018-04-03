package com.a1.apiscraper.service;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.logic.AbstractLogger;
import com.a1.apiscraper.logic.ConsoleLogger;
import com.a1.apiscraper.logic.ErrorLogger;
import com.a1.apiscraper.logic.WarningLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.a1.apiscraper.ApiscraperApplication.getChainOfLoggers;

@Service
public class ProxyImpl implements Proxy{
    @Autowired
    private RestFacade restFacade;
    @Autowired
    private APIService apiService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private SecurityService securityService;

    private AbstractLogger logger = getChainOfLoggers();

    public ProxyImpl(){
        this.logger = getChainOfLoggers();
    }

    public HashMap<Endpoint, Result> proxyScrapeSingleAPI(long apiID){
        logger.logMessage(1,"Er wordt via onze RESTAPI een scrape aan gezet met APIID: " + apiID);
        return restFacade.scrapeSingleApi(apiID);
    }

    public API proxySaveAPIModel(API apiModel){
        logger.logMessage(1,"API " + apiModel + "wordt opgeslagen");
        return apiService.saveAPIModel(apiModel);
    }

    public API proxyRestoreAPIFromMemento(API api, APIMemento apiMemento){
        logger.logMessage(1,"De memento met ID: " + apiMemento.getId() + "wordt teruggezet voor api met ID: " + api.getId());
        return apiService.restoreAPIFromMemento(api, apiMemento);
    }

    public void proxySaveAPIConfig(APIConfig config){
        logger.logMessage(1,"De APIConfig met ID: " + config.getId() + "wordt geupdate vanaf de RESTAPI");
        repositoryService.saveAPIConfig(config);
    }

    public void proxyAutoLogin(String username, String password){
        logger.logMessage(1,"De user met username: " + username + "probeert in te loggen");
        securityService.autologin(username, password);
    }
}