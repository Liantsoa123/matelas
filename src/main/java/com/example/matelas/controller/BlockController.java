package com.example.matelas.controller;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BlockController {
    @Autowired
    private BlockService blockService;

    @GetMapping("/blockForm")
    public String showForm(Model model) {
        model.addAttribute("block", new Block());
        List<Block> blocks = blockService.getAllBlocks();
        model.addAttribute("blocks", blocks);
        return "blockform";
    }

    // Handle the form submission
    @PostMapping("/saveBlock")
    public String saveBlock(@ModelAttribute Block block) {
        blockService.saveBlock(block);
        return "redirect:/blockForm";
    }
}
