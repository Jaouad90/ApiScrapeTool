package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.repository.*;
import com.a1.apiscraper.service.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Controller
public class APIController {

    DateTimeFormatter formatter;
    AbstractLogger loggerChain;

    @Autowired
    private RepositoryServiceInterface repositoryService;

    public APIController() {
        formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.ENGLISH)
                        .withZone( ZoneId.systemDefault() );

        //Benodigd voor gescheiden service/controller
        //this.apiService = apiService;

        this.loggerChain = getChainOfLoggers();
    }

    private static AbstractLogger getChainOfLoggers(){

        AbstractLogger errorLogger = new ErrorLogger(AbstractLogger.ERROR);
        AbstractLogger fileLogger = new WarningLogger(AbstractLogger.DEBUG);
        AbstractLogger consoleLogger = new ConsoleLogger(AbstractLogger.INFO);

        errorLogger.setNextLogger(fileLogger);
        fileLogger.setNextLogger(consoleLogger);

        return errorLogger;
    }

    @RequestMapping(value = "/api/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("decorators", repositoryService.getAllDecorators());
        model.addAttribute("scrapebehaviors", repositoryService.getAllScrapeBehaviors());
        model.addAttribute("api", new API());
        //model.addAttribute("decorators", decoratorRepository.findAll());
        return "api/edit";
    }

    @Transactional
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("api") API apiModel, BindingResult result) {
            if (result.hasErrors()) {
                loggerChain.logMessage(1, "Niet alle velden correct ingevoerd");
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("api/edit");
                modelAndView.addObject("formErrors", result.getAllErrors());
                modelAndView.addObject("scrapebehaviors", repositoryService.getAllScrapeBehaviors());
                modelAndView.addObject("decorators", repositoryService.getAllDecorators());
                modelAndView.addObject("timeintervals", repositoryService.getAllTimeIntervals());
                return modelAndView;
            }
            API api;

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

        return new ModelAndView("redirect:/api/" + api.getId());
    }

    @Transactional
    @RequestMapping(value = "/api/{id}")
    public ModelAndView view(@PathVariable("id") API api) { ;
        api.getTimeInterval().getTimeList();
        return new ModelAndView("home/detail", "api", api);
    }

    @Transactional
    @RequestMapping(value = "/api/restore/{apiid}/{mementoid}")
    public ModelAndView restoreState(@PathVariable("apiid") API api, @PathVariable("mementoid") APIMemento apiMemento ) {
        //Restore Memento
        api.getId();
        apiMemento.getId();
        api.getStateFromMemento(apiMemento);
        repositoryService.saveAPI(api);
        return new ModelAndView("redirect:api/" + api.getId());
    }

    @Transactional
    @RequestMapping(value = "/api/edit/{id}")
    public ModelAndView edit(@PathVariable("id") API api) {
        //create editView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("api/edit");
        modelAndView.addObject("timeintervals", repositoryService.getAllTimeIntervals());
        modelAndView.addObject("api", api);
        modelAndView.addObject("scrapebehaviors", repositoryService.getAllScrapeBehaviors());
        modelAndView.addObject("decorators", repositoryService.getAllDecorators());
        api.getEndpoints();
        System.out.println(api.getEndpoints().entrySet().size());
        return modelAndView;
    }
}