package com.example.matelas.controller;

import com.example.matelas.dto.blockgroupbymachinedto.BlockGroupDTO;
import com.example.matelas.model.achat.AchatMatierePremiere;
import com.example.matelas.model.achat.AchatMatierePremiereService;
import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.csv.Csv;
import com.example.matelas.model.csv.CsvService;
import com.example.matelas.model.formuleDetails.FormuleDetails;
import com.example.matelas.model.formuleDetails.FormuleDetailsService;
import com.example.matelas.model.transformation.TransformationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class BlockController {
    private final BlockService blockService;
    private  final TransformationService transformationService ;
    private static final String UPLOADED_FOLDER = "uploads/";
    private final CsvService csvService;
    private final FormuleDetailsService formuleDetailsService;
    private final AchatMatierePremiereService achatMatierePremiereService;

    public BlockController(BlockService blockService, TransformationService transformationService, CsvService csvService, FormuleDetailsService formuleDetailsService, AchatMatierePremiereService achatMatierePremiereService) {
        this.blockService = blockService;
        this.transformationService = transformationService;
        this.csvService = csvService;
        this.formuleDetailsService = formuleDetailsService;
        this.achatMatierePremiereService = achatMatierePremiereService;
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
    public String handleFileUpload( @RequestParam("path") String path ,  Model model) {
        System.out.println("uploading file");
        model.addAttribute("message", "Insertion Block");
        model.addAttribute("block", new Block());

        File file = new File(path);

        // Check if the file exists
        if (!file.exists()) {
            model.addAttribute("errorI" , "File not found at the given path: " + path);
            return "block";
        }

        try (InputStream inputStream = new FileInputStream(file)) {
            if (inputStream.available() == 0) {
                model.addAttribute("message", "No file content provided for upload.");
                return "block";
            }
            // Process the file content without saving it
            List<AchatMatierePremiere> achatMatierePremiereList = achatMatierePremiereService.getAllAchatMatierePremieres();
            String query = csvService.cvsToQueryBlock(inputStream, formuleDetailsService.getAllFormuleDetails(), achatMatierePremiereList);
            System.out.println("Generated Query: " + query);
            blockService.importCsv(query, achatMatierePremiereList);
            System.out.println("File processed successfully!");
            model.addAttribute("messageI", "File processed successfully!");
        } catch (Exception e) {
            System.out.println("File processing failed: " + e.getMessage());
            model.addAttribute("errorI", "File processing failed: " + e.getMessage());
        }

        return "block";
    }



    @GetMapping("/groupebymachienDate")
    public  String getBlocksGroupedByMachineWithDate ( @RequestParam( value = "year" , defaultValue = "0"  ) int year   , Model model ){
        List<BlockGroupDTO> blockGroupDTOS = new ArrayList<>();

        if ( year == 0   ){
            blockGroupDTOS = blockService.getAllBlocksGroupedByMachine();
            model.addAttribute("annee" , "Tous");
        }else {
            blockGroupDTOS = blockService.getAllBlocksGroupedByMachine(year);
            model.addAttribute("annee" , year);
        }
        model.addAttribute("blockGroupDTO" , blockGroupDTOS);
        return "blockmachinelist";
    }

    @PostMapping("/generateBlockCSV")
    public String generateBlockCSV(@RequestParam("numBlock") int numBlock, Model model) {
        Double prixVolumique = blockService.prixRevientVolumique(4);
        String filePath = "C:\\Users\\rakot\\OneDrive\\Documents\\S5\\Architecture Logiciel\\matelas\\Data\\GeneratedCSV\\blocks.csv";
        try {
              Csv csv = new Csv();
              csv.generateBlockCSV(numBlock , prixVolumique , 1 , 4 , filePath);

            model.addAttribute("messageG", "CSV file generated successfully at: " + filePath);
        } catch (Exception e) {
            model.addAttribute("errorG", "Error generating CSV file: " + e.getMessage());
        }
        model.addAttribute("block", new Block());
        return "block";
    }

}
