package com.a1.apiscraper.controller;


import com.a1.apiscraper.domain.Role;
import com.a1.apiscraper.domain.User;
import com.a1.apiscraper.repository.RoleRepository;
import com.a1.apiscraper.service.SecurityService;
import com.a1.apiscraper.service.UserService;
import com.a1.apiscraper.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @Transactional
    public ModelAndView login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return new ModelAndView("user/login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("userForm") User userForm, BindingResult bindingResult, Model model) {

        securityService.autologin(userForm.getUsername(), userForm.getPassword());
        return "/";
    }


}

