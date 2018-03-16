package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.*;
import com.a1.apiscraper.repository.*;
import com.a1.apiscraper.service.APIServiceInterface;
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


    //APIServiceInterface apiService;

    public APIController(APIRepository apiRepository, EndpointRepository endpointRepository, CareTakerRepository careTakerRepository, DecoratorRepository decoratorRepository
                         /*APIServiceInterface apiService*/) {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
        this.careTakerRepository = careTakerRepository;
        this.decoratorRepository = decoratorRepository;
        formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.ENGLISH)
                        .withZone( ZoneId.systemDefault() );

        //Benodigd voor gescheiden service/controller
        //this.apiService = apiService;
    }

    @RequestMapping(value = "/api/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("scrapebehaviors", scrapeBehaviorRepository.findAll());
        model.addAttribute("api", new API());
        model.addAttribute("decorators", decoratorRepository.findAll());
        return "api/edit";
    }

    @Transactional
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("api") API apiModel, BindingResult result) {
            if (result.hasErrors()) {
                return new ModelAndView("api/edit", "formErrors", result.getAllErrors());
            }
            API api;

            if (apiModel.getId() == null) {
                //Create API
                APIConfig apiConfig = apiModel.getConfig();
                //api.getConfig().setDecorators(apiModel.getConfig().getDecorators());
                apiConfigRepository.save(apiConfig);
                apiModel.setConfig(apiConfig);

                apiRepository.save(apiModel);
                api = apiModel;
            } else {
                //Update API
                api = apiRepository.findOne(apiModel.getId());
                api.setEndpoints(apiModel.getEndpoints());
                api.getConfig().setScrapeBehavior(apiModel.getConfig().getScrapeBehavior());
                api.getConfig().setDecorators(apiModel.getConfig().getDecorators());
                api.setName(apiModel.getName());
                String out = formatter.format(Instant.now());
                api.setState("" + out);
                CareTaker careTaker = api.getCareTaker();
                careTaker.add(api.saveStateToMemente());
                apiRepository.save(api);
            }

        return new ModelAndView("redirect:/api/" + api.getId());
    }

    @Transactional
    @RequestMapping(value = "/api/{id}")
    public ModelAndView view(@PathVariable("id") API api) {
        return new ModelAndView("home/detail", "api", api);
    }

    @Transactional
    @RequestMapping(value = "/api/restore/{apiid}/{mementoid}")
    public ModelAndView restoreState(@PathVariable("apiid") API api, @PathVariable("mementoid") APIMemento apiMemento ) {
        //Restore Memento
        api.getId();
        apiMemento.getId();
        api.getStateFromMemento(apiMemento);
        apiRepository.save(api);
        return new ModelAndView("redirect:api/" + api.getId());
    }

    @Transactional
    @RequestMapping(value = "/api/edit/{id}")
    public ModelAndView edit(@PathVariable("id") API api) {
        //create editView
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("api/edit");
        modelAndView.addObject("api", api);
        modelAndView.addObject("scrapebehaviors", scrapeBehaviorRepository.findAll());
        modelAndView.addObject("decorators", decoratorRepository.findAll());
        api.getEndpoints();
        System.out.println(api.getEndpoints().entrySet().size());
        return modelAndView;
    }
}