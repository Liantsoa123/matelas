package com.example.matelas.controller;



import com.example.matelas.model.marge.Marge;
import com.example.matelas.model.marge.MargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller

public class MargeController {

    private final MargeService margeService;

    @Autowired
    public MargeController(MargeService margeService) {
        this.margeService = margeService;
    }



    // Show form for adding a new Marge
   /* @GetMapping("/marge")
    public String showCreateForm(Model model) {
        model.addAttribute("marge", new Marge());
        return "marge";
    }*/

    /*// Handle saving a new Marge
    @PostMapping
    public String saveMarge(@ModelAttribute("marge") Marge marge) {
        margeService.saveMarge(marge);
        return "redirect:/marges";
    }*/

    // Show form for editing an existing Marge
    @GetMapping("/marge")
    public String showEditForm( Model model) {
        System.out.println("marge ");
        Optional<Marge> marge = margeService.findMargeById(1);
        if (marge.isPresent()) {
            model.addAttribute("marge", marge.get());
            return "marge";
        } else {
            return "redirect:/block";
        }
    }

    // Handle updating an existing Marge
    @PostMapping("/editmarge")
    public String updateMarge( @ModelAttribute("marge") Marge marge) {
        margeService.saveMarge(marge);
        return "redirect:/marge";
    }

    // Handle deleting a Marge
    @GetMapping("/delete/{id}")
    public String deleteMarge(@PathVariable int id) {
        margeService.deleteMargeById(id);
        return "redirect:/marges";
    }
}
