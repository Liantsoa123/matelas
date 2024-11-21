package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.csv.CsvService;
import com.example.matelas.model.formuleDetails.FormuleDetails;
import com.example.matelas.model.formuleDetails.FormuleDetailsService;
import com.example.matelas.model.transformation.TransformationService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Controller
public class BlockController {
    private final BlockService blockService;
    private  final TransformationService transformationService ;
    private static final String UPLOADED_FOLDER = "uploads/";
    private final CsvService csvService;
    private final FormuleDetailsService formuleDetailsService;

    public BlockController(BlockService blockService, TransformationService transformationService, CsvService csvService, FormuleDetailsService formuleDetailsService) {
        this.blockService = blockService;
        this.transformationService = transformationService;
        this.csvService = csvService;
        this.formuleDetailsService = formuleDetailsService;
    }

    // Handle the form submission
    @PostMapping("/saveBlock")
    public String saveBlock(@ModelAttribute Block block) {
        if ( block.getId() ==0 ){
            blockService.saveBlock(block);
        }else {
            System.out.println("update  "+block.getId());
            System.out.println(  "id transformation ="+ transformationService.findTransformationByMereBlockId(block.getId()).getId());

            transformationService.updatePrixRevient( block.getPrixRevient() , block.getId());
            //blockService.updateBlock(block.getId(), block);
        }
        return "redirect:/block";
    }

    @GetMapping("/block")
    public String formBlock(Model model) {
        model.addAttribute("message", "Insertion Block");
        model.addAttribute("block" , new Block());
        return "block";
    }

    @GetMapping("/getAllBolck")
    public  String allBlock (Model model ){
        List<Block> blocks = blockService.getAllPrinicpalMere();
        model.addAttribute("blocks" , blocks);
        return "blocklist";
    }

    @GetMapping("/edit-block/{id}")
    public String editBlock(@PathVariable("id") int id, Model model) {
        Optional<Block> block = blockService.getBlockById(id);
        if (block.isPresent()) {
            model.addAttribute("message", "Edit Block");
            model.addAttribute("block", block.get());
            return "block";
        }
        return "redirect:/getAllBolck";
    }

    @PostMapping("/importCsv")
    public String handleFileUpload(@RequestParam("filecsv") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "No file selected for upload.");
            return "upload";
        }

        try {
            // Process the file content without saving it
            String query = csvService.cvsToQueryBlock(file.getInputStream() , formuleDetailsService.getAllFormuleDetails());
            System.out.println("Generated Query: " + query);
            blockService.importCsv(query);

            System.out.println("File processed successfully!");

        } catch (Exception e) {
           System.out.println( "File processing failed: " + e.getMessage());
            model.addAttribute("message", "Insertion Block");
            model.addAttribute("block" , new Block());
            model.addAttribute("error","File processing failed: " + e.getMessage() );
            return "block";
        }

        return "redirect:/block"; // Return to the upload page
    }

}
