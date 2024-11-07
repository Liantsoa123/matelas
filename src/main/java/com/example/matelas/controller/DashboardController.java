package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.forme.FormUsuelle;
import com.example.matelas.model.forme.FormUsuelleService;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController  {
    private  final TransformationDetailsService transformationDetailsService ;
    private final FormUsuelleService formUsuelleService;
    private final BlockService blockService;

    public DashboardController(TransformationDetailsService transformationDetailsService, FormUsuelleService formUsuelleService, BlockService blockService) {
        this.transformationDetailsService = transformationDetailsService;
        this.formUsuelleService = formUsuelleService;
        this.blockService = blockService;
    }

    @GetMapping("/dashboard")
    public String getTotalValeurVente(Model model) {
        Double totalValeurVente = transformationDetailsService.calculateTotalValeurVente();
        model.addAttribute("totalValeurVente", totalValeurVente);

        FormUsuelle formUsuelle = formUsuelleService.bestRationVenteVolumn();
        model.addAttribute("bestRatio" , formUsuelle);


        List<Block> allReste = blockService.getBlocksNotInMereId();
        Optional<FormUsuelle> smallEstVolum = formUsuelleService.getFormUsuelleWithSmallestVolume();
        System.out.println(smallEstVolum.get().getName());


        int q = smallEstVolum.get().getQuantiteReste(allReste);
        model.addAttribute("formPetit" , smallEstVolum);
        model.addAttribute("quantite" , q);
        model.addAttribute("prixTotalVente" , q* smallEstVolum.get().getPrixVente());
        return "dashboard";
    }
}
