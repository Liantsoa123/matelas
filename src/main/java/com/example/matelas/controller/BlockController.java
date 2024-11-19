package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.transformation.TransformationService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class BlockController {
    private final BlockService blockService;
    private  final TransformationService transformationService ;

    public BlockController(BlockService blockService, TransformationService transformationService) {
        this.blockService = blockService;
        this.transformationService = transformationService;
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
    public String importCsv(@RequestParam("pathCsv" )String pathCsv ){
        System.out.println("Path Csv = " + pathCsv);
        blockService.importCsv(pathCsv);
        return  "redirect:/block" ;
    }

}
