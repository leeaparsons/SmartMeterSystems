package com.group15.interactivemapservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/tableView")
public class TablePageController {
    @GetMapping("/login")
    ModelAndView login(){
        return new ModelAndView("login",
                "redirect",
                "table"); //replace with the name of your web page
    }

    @GetMapping("/table") //replace with the name of your web page
    String testPage() {
        return "table"; //replace wit
    }
}
