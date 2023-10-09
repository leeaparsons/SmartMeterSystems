package com.group15.interactivemapservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/mapView")
public class MapPageController {
    @GetMapping("/login")
    ModelAndView login(){
        return new ModelAndView("login",
                "redirect",
                "map"); //replace with the name of your web page
    }

    @GetMapping("/map") //replace with the name of your web page
    String testPage(){
        return "map"; //replace with the name of your web page
    }

}
