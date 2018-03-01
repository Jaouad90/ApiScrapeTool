package com.a1.apiscraper.controller;

import com.a1.apiscraper.repository.APIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private APIRepository apiRepository;
    public HomeController(APIRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @GetMapping
    public ModelAndView home() {
        API api = new API();
        apiRepository.save()
        return new ModelAndView("home/home");
    }
}
