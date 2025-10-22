package com.example.msa_project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlController {

    @GetMapping({"/", "/home"})
    public String home() {
        return "redirect:/html/index.html";
    }

    @GetMapping("/html")
    public String htmlHome() {
        return "redirect:/html/index.html";
    }
}


