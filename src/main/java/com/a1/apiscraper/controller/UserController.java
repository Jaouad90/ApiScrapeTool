package com.a1.apiscraper.controller;


import com.a1.apiscraper.domain.User;
import com.a1.apiscraper.service.SecurityService;
import com.a1.apiscraper.service.UserService;
import com.a1.apiscraper.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView registration(Model model) {
        model.addAttribute("userForm", new User());

        return new ModelAndView("user/login", "model", model);
    }
}
