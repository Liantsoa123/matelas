package com.example.matelas.controller;

import com.example.matelas.model.transformationdetails.TransformationDetails;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class DashboardController  {
    private  final TransformationDetailsService transformationDetailsService ;

    public DashboardController( TransformationDetailsService transformationDetailsService) {
        this.transformationDetailsService = transformationDetailsService;
    }

    @GetMapping("/dashboard")
    public String getTotalValeurVente(Model model) {
        Double totalValeurVente = transformationDetailsService.calculateTotalValeurVente();
        model.addAttribute("totalValeurVente", totalValeurVente);


        return "dashboard";
    }
}
