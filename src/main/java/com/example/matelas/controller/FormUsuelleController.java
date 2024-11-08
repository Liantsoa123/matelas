package com.example.matelas.controller;

import com.example.matelas.model.forme.FormUsuelle;
import com.example.matelas.model.forme.FormUsuelleService;
import com.example.matelas.model.transformationdetails.TransformationDetails;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class FormUsuelleController {
    final FormUsuelleService formUsuelleService ;
    final TransformationDetailsService transformationDetailsService ;

    public FormUsuelleController(FormUsuelleService formUsuelleService, TransformationDetailsService transformationDetailsService) {
        this.formUsuelleService = formUsuelleService;
        this.transformationDetailsService = transformationDetailsService;
    }


    @GetMapping("/formUsuellesList")
    public String getListFormUsuelle (Model model){
        List<FormUsuelle> allUsuelles = formUsuelleService.getAllFormUsuelles();
        model.addAttribute("formUsuelles" , allUsuelles);
        model.addAttribute("formServie" , formUsuelleService);
        return "formUsuelleListe";
    }

   /* @GetMapping("formUsuelle/{id}")
    public String detailFormUsuelle (@PathVariable("id") int idFormUsuelle, Model model){
        List<TransformationDetails> td = transformationDetailsService.findAllByUsuelleId(idFormUsuelle);
        model.addAttribute("transformationDetails" , td);


    */
}
