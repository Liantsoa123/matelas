package com.example.matelas.model.csv;

import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.formuleDetails.FormuleDetails;
import com.example.matelas.model.util.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Service
public class CsvService {

    private final BlockService blockService;

    public CsvService(BlockService blockService) {
        this.blockService = blockService;
    }
   /* public String cvsToQueryBlock(String filePath) {
        StringBuilder query = new StringBuilder("INSERT INTO block (longueur, largeur, epaisseur, prix_revient, creation_block, name , machine_id) VALUES ");

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                query.append("(")
                        .append(record.get("longueur")).append(", ")
                        .append(record.get("largeur")).append(", ")
                        .append(record.get("epaisseur")).append(", ")
                        .append(record.get("prix_revient")).append(", ")
                        .append("'").append(record.get("creation_block")).append("', ")
                        .append("'").append(record.get("name")).append("',")
                        .append(record.get("machine_id")).append("), ");
            }

            // Remove trailing comma and space
            if (query.length() > 0) {
                query.setLength(query.length() - 2);
            }

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return query.toString();
    }*/

    public String cvsToQueryBlock(InputStream inputStream , List<FormuleDetails> formuleDetails ) {
        StringBuilder query = new StringBuilder("INSERT INTO block (longueur, largeur, epaisseur, prix_revient, creation_block, name, machine_id , prix_theorique) VALUES ");

        try (CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                double volume = Double.parseDouble(record.get("longueur")) * Double.parseDouble(record.get("largeur")) * Double.parseDouble(record.get("epaisseur"));
                double prixTheorique = blockService.getprixRevientTheorique(StringUtils.convertStringToDate(record.get("creation_block")), volume ,formuleDetails  );
                query.append("(")
                        .append(record.get("longueur")).append(", ")
                        .append(record.get("largeur")).append(", ")
                        .append(record.get("epaisseur")).append(", ")
                        .append(record.get("prix_revient")).append(", ")
                        .append("'").append(record.get("creation_block")).append("', ")
                        .append("'").append(record.get("name")).append("', ")
                        .append(record.get("machine_id")).append(", ")
                        .append(prixTheorique).append("), ");
            }

            // Remove trailing comma and space
            if (query.length() > 0) {
                query.setLength(query.length() - 2);
            }

        } catch (Exception e) {
            System.err.println("Error processing the CSV file: " + e.getMessage());
            throw new RuntimeException("Error processing the CSV file: " + e.getMessage());
        }

        return query.toString();
    }



}
