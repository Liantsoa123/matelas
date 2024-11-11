package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.forme.FormUsuelle;
import com.example.matelas.model.forme.FormUsuelleService;
import com.example.matelas.model.transformationdetails.TransformationDetails;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;
import java.util.List;

@Controller
public class FormUsuelleController {
    final FormUsuelleService formUsuelleService;
    final TransformationDetailsService transformationDetailsService;
    private final BlockService blockService;

    public FormUsuelleController(FormUsuelleService formUsuelleService, TransformationDetailsService transformationDetailsService, BlockService blockService) {
        this.formUsuelleService = formUsuelleService;
        this.transformationDetailsService = transformationDetailsService;
        this.blockService = blockService;
    }


    @GetMapping("/formUsuellesList")
    public String getListFormUsuelle(Model model) {
        List<FormUsuelle> allUsuelles = formUsuelleService.getAllFormUsuelles();
        model.addAttribute("formUsuelles", allUsuelles);
        model.addAttribute("formServie", formUsuelleService);
        return "formUsuelleListe";
    }

    @GetMapping("formUsuelle/{id}")
    public String detailFormUsuelle(@PathVariable("id") int idFormUsuelle, Model model) {
        FormUsuelle formUsuelle = formUsuelleService.getFormUsuelleById(idFormUsuelle).orElseThrow(() -> new RuntimeException("FormUsuelle not found with id " + idFormUsuelle));
        HashMap<Block , Double> blockQuantite = formUsuelleService.getAllSrc(formUsuelle.getId());
        model.addAttribute("formUsuelle", formUsuelle);
        model.addAttribute("blockQuantite", blockQuantite);

        return "formUsuelleDetail";
    }
}