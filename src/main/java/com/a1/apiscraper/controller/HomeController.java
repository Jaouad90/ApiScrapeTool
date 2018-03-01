package com.a1.apiscraper.controller;

import com.a1.apiscraper.domain.API;
import com.a1.apiscraper.manager.APIManager;
import com.a1.apiscraper.repository.APIRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private APIRepository apiRepository;

    private APIManager apiManager = new APIManager(apiRepository);

    public HomeController(APIRepository apiRepository) {
        this.apiRepository = apiRepository;
    }

    @GetMapping
    @Transactional
    public ModelAndView home() {
        API api = new API();
        api.setBaseUrl("https://api.coinmarketcap.com/v1/");
        api.addEndpoint("ticker/");
        apiRepository.save(api);
        apiManager.doScrape();
        return new ModelAndView("home/home");
    }
}
