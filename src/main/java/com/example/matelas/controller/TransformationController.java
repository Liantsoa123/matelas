package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.forme.FormUsuelle;
import com.example.matelas.model.forme.FormUsuelleService;
import com.example.matelas.model.transformation.Transformation;
import com.example.matelas.model.transformation.TransformationService;
import com.example.matelas.model.transformationdetails.TransformationDetails;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class TransformationController {
    private  final TransformationService transformationService;
    private final BlockService blockService;
    private final FormUsuelleService formUsuelleService ;
    private final TransformationDetailsService transformationDetailsService;

    public TransformationController(TransformationService transformationService, BlockService blockService, FormUsuelleService formUsuelleService, TransformationDetailsService transformationDetailsService) {
        this.transformationService = transformationService;
        this.blockService = blockService;
        this.formUsuelleService = formUsuelleService;
        this.transformationDetailsService = transformationDetailsService;
    }

    @GetMapping("/formTransformation")
    public String formTransformation(Model model) {
        List<Block> listBlock = blockService.getBlocksNotInMereId();
        List<FormUsuelle> formUsuelleList = formUsuelleService.getAllFormUsuelles();
        Transformation t = new Transformation() ;
        t.setFormUsuelles(formUsuelleList);
        model.addAttribute("listBlock", listBlock);
        model.addAttribute("transformation", t);



        return  "formTransformation";
    }

    @PostMapping("/saveTransformation")
    public String saveTransformation(@ModelAttribute Transformation transformation,
                                     Model model) {
        List<FormUsuelle> formUsuelles = formUsuelleService.getAllFormUsuelles();
        int i =  0 ;
        double totalVoulumeUsuelles = 0;
        for (FormUsuelle formUsuelle : transformation.getFormUsuelles()) {
           formUsuelles.get(i).setQuantiteTransformation(formUsuelle.getQuantiteTransformation());
           //System.out.println(formUsuelles.get(i).getName() +"==" +formUsuelles.get(i).getQuantiteTransformation() );
            totalVoulumeUsuelles += formUsuelles.get(i).volume() * formUsuelles.get(i).getQuantiteTransformation() ;
            i++;
        }


        //Get Block mere
        Optional<Block> mere = blockService.getBlockById(transformation.getMere().getId());

        //Check 2% of reste
        double allReste = mere.get().volume() - totalVoulumeUsuelles - transformation.getReste().volume();
        System.out.println("allReste="+allReste);
        System.out.println("mere="+mere.get().volume());
        System.out.println("totalVoulumeUsuelles="+totalVoulumeUsuelles);
        System.out.println("reste="+transformation.getReste().volume());

        double pourcentage = allReste * 100 / mere.get().volume();
        System.out.println(pourcentage);

        if ( allReste < 0 ){
            String error = "Le volume de block  est insuffisante. Veuillez en réduire le nombre.";
            model.addAttribute("error", error);
            List<Block> listBlock = blockService.getBlocksNotInMereId();
            List<FormUsuelle> formUsuelleList = formUsuelleService.getAllFormUsuelles();
            Transformation t = new Transformation() ;
            t.setFormUsuelles(formUsuelleList);
            t.setReste(transformation.getReste());
            t.setDateTransformation(transformation.getDateTransformation());
            model.addAttribute("listBlock", listBlock);
            model.addAttribute("transformation", t);
            return "formTransformation";
        }

        if ( pourcentage > 30 ){
            String error = "Perte supérieure à 2%";
            model.addAttribute("error", error);
            List<Block> listBlock = blockService.getBlocksNotInMereId();
            List<FormUsuelle> formUsuelleList = formUsuelleService.getAllFormUsuelles();
            Transformation t = new Transformation() ;
            t.setFormUsuelles(formUsuelleList);
            t.setReste(transformation.getReste());
            t.setDateTransformation(transformation.getDateTransformation());
            model.addAttribute("listBlock", listBlock);
            model.addAttribute("transformation", t);
            return "formTransformation";
        }
        try {
            //insert all
            saveTransformationData( transformation , formUsuelles , mere );
        }catch ( Exception e){
            e.printStackTrace();
        }


        return "redirect:/formTransformation"; // Redirect to a success or confirmation page
    }


    @Transactional
    public void saveTransformationData(Transformation transformation, List<FormUsuelle> formUsuelles , Optional<Block> mere ) {
        try {
            // Insert Reste
            Block reste = transformation.getReste();
            reste.setMere(mere.get());
            reste.setCreationBlock(transformation.getDateTransformation());
            reste.setPrixRevient();
            blockService.saveBlock(reste);

            // Insert Transformation
            transformation.setReste(blockService.getBlocksByMereId(mere.get().getId()).get());
            transformation.setMere(mere.get());
            transformationService.saveTransformation(transformation);

            // Insert Transformation Details
            for (FormUsuelle formUsuelle : formUsuelles) {
                TransformationDetails transformationDetails = new TransformationDetails(0, formUsuelle, transformation , formUsuelle.getQuantiteTransformation());
                if ( transformationDetails.getQuantite() > 0 ){
                    transformationDetailsService.saveTransformationDetails(transformationDetails);
                }
            }
        } catch (Exception e) {
            // Log the error if needed
            throw e; // Rethrow to trigger rollback
        }
    }


}
