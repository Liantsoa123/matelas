package com.example.matelas.controller;

import com.example.matelas.model.csv.CsvService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/csv")
public class CsvController {
    private final CsvService csvService ;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

}
