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
import java.nio.DoubleBuffer;
import java.sql.Date;
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
   public String cvsToQueryBlock(InputStream inputStream, List<FormuleDetails> formuleDetails) {
       StringBuilder query = new StringBuilder("INSERT INTO block (longueur, largeur, epaisseur, prix_revient, creation_block, name, machine_id, prix_theorique) VALUES ");

       try (CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

           for (CSVRecord record : csvParser) {
               try {
                   // Parse values from the CSV
                   Double longueur = Double.valueOf(record.get("longueur"));
                   Double largeur = Double.valueOf(record.get("largeur"));
                   Double epaisseur = Double.valueOf(record.get("epaisseur"));
                   Double prix_revient = Double.valueOf(record.get("prix_revient"));
                   Date creation_block = StringUtils.convertStringToDate(record.get("creation_block"));  // Assuming you have a utility method to convert string to date
                   String name = record.get("name"); // Corrected here to get the actual name
                   int machine_id = Integer.parseInt(record.get("machine_id"));

                   // Calculate volume and prix_theorique
                   double volume = longueur * largeur * epaisseur;
                   double prixTheorique = blockService.getprixRevientTheorique(creation_block, volume, formuleDetails);

                   // Append the values to the query
                   query.append("(")
                           .append(longueur).append(", ")
                           .append(largeur).append(", ")
                           .append(epaisseur).append(", ")
                           .append(prix_revient).append(", ")
                           .append("'").append(creation_block).append("', ")
                           .append("'").append(name).append("', ")
                           .append(machine_id).append(", ")
                           .append(prixTheorique).append("), ");

               } catch (Exception e) {
                   // Log the error with the line number and error message
                   System.err.println("Error at line " + (csvParser.getCurrentLineNumber()) + ": " + e.getMessage());
                   throw new RuntimeException("Error at line " + (csvParser.getCurrentLineNumber()) + ": " + e.getMessage(), e);
               }
           }

           // Remove trailing comma and space
           if (query.length() > 0) {
               query.setLength(query.length() - 2);
           }

       } catch (Exception e) {
           // Log any errors that happen outside the loop (like CSV parsing issues)
           System.err.println("Error processing the CSV file: " + e.getMessage());
           throw new RuntimeException("Error processing the CSV file: " + e.getMessage(), e);
       }

       return query.toString();
   }



    public String cvsToQueryAchat(InputStream inputStream) {
        StringBuilder query = new StringBuilder("INSERT INTO achat_matiere_premiere (date_achat, matiere_premier_id, prix_revient, quantite) VALUES ");

        try (CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            for (CSVRecord record : csvParser) {
                query.append("(")
                        .append("'").append(record.get("date_achat")).append("', ") // Ensure proper quoting for string values
                        .append(record.get("matiere_premier_id")).append(", ")
                        .append(record.get("prix_revient")).append(", ")
                        .append(record.get("quantite"))
                        .append("), ");
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
