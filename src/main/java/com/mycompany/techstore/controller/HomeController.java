package com.mycompany.techstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "home"; // Trỏ đến file /WEB-INF/views/home.jsp [cite: 317, 320]
    }
}