package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.domain.CareTaker;
import com.a1.apiscraper.domain.Endpoint;
import com.a1.apiscraper.repository.APIRepository;
import com.a1.apiscraper.repository.CareTakerRepository;
import com.a1.apiscraper.repository.EndpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class APIController {

    @Autowired
    APIRepository apiRepository;
    @Autowired
    EndpointRepository endpointRepository;
    @Autowired
    CareTakerRepository careTakerRepository;

    public APIController(APIRepository apiRepository, EndpointRepository endpointRepository, CareTakerRepository careTakerRepository) {
        this.apiRepository = apiRepository;
        this.endpointRepository = endpointRepository;
        this.careTakerRepository = careTakerRepository;
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
//        CareTaker careTaker = new CareTaker();
//        api.setState("state 0");
//        api.setCareTaker(careTaker);
//        careTaker.add(api.saveStateToMemente());
//        careTakerRepository.save(careTaker);
//        System.out.println(api.getState());
          apiRepository.save(api);
        return new ModelAndView("api/edit", "api", api);
    }

    @RequestMapping(value = "/api/{id}")
    public ModelAndView view(@PathVariable("id") API api) {
//          api.setName("GOOGLE");
//           api.setBaseUrl("https://google.nl");
//           Endpoint endpoint1 = new Endpoint();
//           endpoint1.setName("/historicalprice.json");
//           endpointRepository.save(endpoint1);
//           api.addEndpoint(endpoint1);
//           CareTaker careTaker = api.getCareTaker();
//           api.getStateFromMemento(careTaker.get(careTaker.getMementoList().size() - 1));
        return new ModelAndView("home/detail", "api", api);
    }

    @RequestMapping(value = "/api/edit/{id}")
    public ModelAndView edit(@PathVariable("id") API api) {
        return new ModelAndView("api/edit", "api", api);
    }

}