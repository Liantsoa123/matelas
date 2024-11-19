package com.example.matelas.model.csv;

import com.example.matelas.model.block.Block;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Service
public class CsvService {
    public String cvsToQueryBlock(String filePath) {
        StringBuilder query = new StringBuilder("INSERT INTO block (longueur, largeur, epaisseur, prix_revient, creation_block, name) VALUES ");

        try (FileReader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                query.append("(")
                        .append(record.get("longueur")).append(", ")
                        .append(record.get("largeur")).append(", ")
                        .append(record.get("epaisseur")).append(", ")
                        .append(record.get("prix_revient")).append(", ")
                        .append("'").append(record.get("creation_block")).append("', ")
                        .append("'").append(record.get("name")).append("'), ");
            }

            // Remove trailing comma and space
            if (query.length() > 0) {
                query.setLength(query.length() - 2);
            }

        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
        }

        return query.toString();
    }


}