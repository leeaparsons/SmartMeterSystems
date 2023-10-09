package com.group15.interactivemapservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboardView")
public class DashboardPageController {
    @GetMapping("/login")
    ModelAndView login(){
        return new ModelAndView("login",
                "redirect",
                "dashboard"); //replace with the name of your web page
    }

    @GetMapping("/dashboard") //replace with the name of your web page
    String testPage(){
        return "dashboard"; //replace with the name of your web page
    }
}