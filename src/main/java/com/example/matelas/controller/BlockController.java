package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class BlockController {
    private final BlockService blockService;

    public BlockController(BlockService blockService) {
        this.blockService = blockService;
    }

    // Handle the form submission
    @PostMapping("/saveBlock")
    public String saveBlock(@ModelAttribute Block block) {
        if ( block.getId() ==0 ){
            blockService.saveBlock(block);
        }else {
            System.out.println("update"+block.getId());
            blockService.updateBlock(block.getId(), block);
        }
        return "redirect:/block";
    }


    @GetMapping("/block")
    public String formBlock(Model model) {
        model.addAttribute("message", "Insertion Block");
        model.addAttribute("block" , new Block());
        return "bolck";
    }

    @GetMapping("/getAllBolck")
    public  String allBlock (Model model ){
        List<Block> blocks = blockService.getAllBlocks();
        model.addAttribute("blocks" , blocks);
        return "blocklist";
    }

    @GetMapping("/edit-block/{id}")
    public String editBlock(@PathVariable("id") int id, Model model) {
        Optional<Block> block = blockService.getBlockById(id);
        if (block.isPresent()) {
            model.addAttribute("block", block.get());
            return "bolck";  // Redirects to the edit-block page
        }
        return "redirect:/all-blocks";  // Redirect back to the all-blocks page if block not found
    }
}
