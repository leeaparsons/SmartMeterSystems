package com.group15.interactivemapservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestPageController {

    @GetMapping("/test") //replace with the name of your web page
    String testPage(){
        return "test"; //replace with the name of your web page
    }

}