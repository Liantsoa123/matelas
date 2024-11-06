package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("/welcome")
    public String welcome(Model model) {
        model.addAttribute("message", "Welcome to Thymeleaf with Spring Boot!");
        return "welcomePage";
    }
    @GetMapping("/formB")
    public String formB (Model model) {
        model.addAttribute("block", new Block());
        return "formB";
    }
}
