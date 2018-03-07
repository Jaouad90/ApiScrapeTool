package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
public class APIController {

    @Autowired
    APIRepository apiRepository;
    @Autowired
    EndpointRepository endpointRepository;

    public APIController(APIRepository apiRepository, EndpointRepository endpointRepository) {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
    }



    @RequestMapping(value = "/api/add", method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("api", new API());
        return "api/edit";
    }

    @Transactional
    @RequestMapping(value = "/api", method = RequestMethod.POST)
    public ModelAndView submit(@Valid API api, BindingResult result) {
        if (result.hasErrors()) {
            return new ModelAndView("api/edit", "formErrors", result.getAllErrors());
        }
        List<Endpoint> endpoints = (List<Endpoint>) api.getEndpoints();
        api.setEndpoints(endpoints);
        endpointRepository.save(endpoints);
        apiRepository.save(api);
        return new ModelAndView("api/edit", "api", api);
    }

    @RequestMapping(value = "/api/{id}")
    public ModelAndView view(@PathVariable("id") API api) {
        return new ModelAndView("home/detail", "api", api);
    }

    @RequestMapping(value = "/api/edit/{id}")
    public ModelAndView edit(@PathVariable("id") API api) {
        return new ModelAndView("api/edit", "api", api);
    }

}