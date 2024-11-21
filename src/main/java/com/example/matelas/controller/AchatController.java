package com.example.matelas.controller;


import com.example.matelas.model.achat.AchatMatierePremiereService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AchatController {

    private static final String UPLOADED_FOLDER = "uploads/";
    private  final AchatMatierePremiereService achatMatierePremiereService;

    public AchatController(AchatMatierePremiereService achatMatierePremiereService) {
        this.achatMatierePremiereService = achatMatierePremiereService;
    }

    @GetMapping("uploadAchat")
    public String formAchat (Model model){
        return  "huhu";
    }

    @PostMapping("/importCsvAchat")
    public  String inmportFile(@RequestParam("filecsv") MultipartFile file , Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "No file selected for upload.");
        }
        return "uploadAchat";
    }
}
