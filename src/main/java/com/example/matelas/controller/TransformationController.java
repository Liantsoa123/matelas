package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.forme.FormUsuelle;
import com.example.matelas.model.forme.FormUsuelleService;
import com.example.matelas.model.marge.Marge;
import com.example.matelas.model.marge.MargeService;
import com.example.matelas.model.transformation.Transformation;
import com.example.matelas.model.transformation.TransformationService;
import com.example.matelas.model.transformationdetails.TransformationDetails;
import com.example.matelas.model.transformationdetails.TransformationDetailsService;
import com.example.matelas.model.util.MeasurementConverter;
import org.hibernate.annotations.Parameter;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class TransformationController {
    private  final TransformationService transformationService;
    private final BlockService blockService;
    private final FormUsuelleService formUsuelleService ;
    private final TransformationDetailsService transformationDetailsService;
    private final MargeService margeService;

    public TransformationController(TransformationService transformationService, BlockService blockService, FormUsuelleService formUsuelleService, TransformationDetailsService transformationDetailsService, MargeService margeService) {
        this.transformationService = transformationService;
        this.blockService = blockService;
        this.formUsuelleService = formUsuelleService;
        this.transformationDetailsService = transformationDetailsService;
        this.margeService = margeService;
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
                                     Model model,
                                     @RequestParam(name="longueurReste") String longueurReste,
                                     @RequestParam(name="larguerReste") String largeurReste,
                                     @RequestParam(name="epaisseurReste") String hauteurReste
                                     ) {
        List<FormUsuelle> formUsuelles = formUsuelleService.getAllFormUsuelles();

        int i =  0 ;
        double totalVoulumeUsuelles = 0;
        for (FormUsuelle formUsuelle : transformation.getFormUsuelles()) {
           formUsuelles.get(i).setQuantiteTransformation(formUsuelle.getQuantiteTransformation());
           //System.out.println(formUsuelles.get(i).getName() +"==" +formUsuelles.get(i).getQuantiteTransformation() );
            totalVoulumeUsuelles += formUsuelles.get(i).volume() * formUsuelles.get(i).getQuantiteTransformation() ;
            i++;
        }
        double longueurResteInMeters = MeasurementConverter.convertToMeters(longueurReste);
        System.out.println("longueurResteInMeters="+longueurResteInMeters);
        double largeurResteInMeters = MeasurementConverter.convertToMeters(largeurReste);
        double hauteurResteInMeters = MeasurementConverter.convertToMeters(hauteurReste);



        //Get Block mere
        Optional<Block> mere = blockService.getBlockById(transformation.getMere().getId());

        Block reste = new Block(mere.get().getName() +"R",longueurResteInMeters , largeurResteInMeters , hauteurResteInMeters , 0 , transformation.getDateTransformation());
        transformation.setReste(reste);

        //Check 2% of reste
        double allReste = mere.get().volume() - totalVoulumeUsuelles - transformation.getReste().volume();
        System.out.println("allReste="+allReste);
        System.out.println("mere="+mere.get().volume());
        System.out.println("totalVoulumeUsuelles="+totalVoulumeUsuelles);
        System.out.println("reste="+transformation.getReste().volume());
        System.out.println("longueurResteInMeters="+longueurResteInMeters);



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

        Marge marge = margeService.findMargeById(1).get();


        if ( pourcentage >  marge.getMarge()){
            String error = "Perte supérieure à "+marge.getMarge();
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
            reste.setName(mere.get().getName() + "-R");
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
