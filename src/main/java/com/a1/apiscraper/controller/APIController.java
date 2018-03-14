package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.APIMemento;
import com.a1.apiscraper.domain.CareTaker;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.CareTakerRepository;
import com.a1.apiscraper.repository.EndpointRepository;
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
    CareTakerRepository careTakerRepository;
    DateTimeFormatter formatter;

    public APIController(APIRepository apiRepository, EndpointRepository endpointRepository, CareTakerRepository careTakerRepository) {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
        this.careTakerRepository = careTakerRepository;
        formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT )
                        .withLocale( Locale.ENGLISH)
                        .withZone( ZoneId.systemDefault() );
    }



    @RequestMapping(value = "/api/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("api", new API());
        return "api/edit";
    }

    @Transactional
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public ModelAndView submit(@Valid @ModelAttribute("api") API api, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("api/edit", "formErrors", result.getAllErrors());
        }
          String out = formatter.format(Instant.now());
          api.setState("" + out);
          CareTaker careTaker = api.getCareTaker();
          careTaker.add(api.saveStateToMemente());
          apiRepository.save(api);
        return new ModelAndView("redirect:/api/" + api.getId(), "api", api);
    }

    @Transactional
    @RequestMapping(value = "/api/{id}")
    public ModelAndView view(@PathVariable("id") API api) {
        api.getCareTaker().getMementos();
        return new ModelAndView("home/detail", "api", api);
    }

    @Transactional
    @RequestMapping(value = "/api/restore", method = RequestMethod.POST)
    public ModelAndView restoreState(@Valid APIMemento api) {

        return new ModelAndView("api/edit", "api", api);
    }

    @Transactional
    @RequestMapping(value = "/api/edit/{id}")
    public ModelAndView edit(@PathVariable("id") API api) {
        api.getEndpoints();
        System.out.println(api.getEndpoints().entrySet().size());
        return new ModelAndView("api/edit", "api", api);
    }

}