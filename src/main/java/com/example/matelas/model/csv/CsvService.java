package com.example.matelas.model.csv;

import com.example.matelas.model.achat.AchatMatierePremiere;
import com.example.matelas.model.achat.AchatMatierePremiereService;
import com.example.matelas.model.block.Block;
import com.example.matelas.model.block.BlockService;
import com.example.matelas.model.formuleDetails.FormuleDetails;
import com.example.matelas.model.formuleDetails.FormuleDetailsService;
import com.example.matelas.model.util.RandomUtils;
import com.example.matelas.model.util.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.DoubleBuffer;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

@Service
public class CsvService {

    private final BlockService blockService;
    private final AchatMatierePremiereService achatMatierePremiereService;

    final LocalDate startDate = LocalDate.of(2022, 1, 1);
    final LocalDate endDate = LocalDate.of(2024, 12, 31);
    // Constantes pour les bornes
    final double MIN_LONGUEUR = 20.0, MAX_LONGUEUR = 25.0;
    final double MIN_LARGEUR = 5.0, MAX_LARGEUR = 7.0;
    final double MIN_EPAISSEUR = 10.0, MAX_EPAISSEUR = 15.0;
    final double MIN_VARIATION = 0.9, MAX_VARIATION = 1.1;
    private final FormuleDetailsService formuleDetailsService;

    public CsvService(BlockService blockService, AchatMatierePremiereService achatMatierePremiereService, FormuleDetailsService formuleDetailsService) {
        this.blockService = blockService;
        this.achatMatierePremiereService = achatMatierePremiereService;
        this.formuleDetailsService = formuleDetailsService;
    }

    public String cvsToQueryBlock(InputStream inputStream, List<FormuleDetails> formuleDetails , List<AchatMatierePremiere> achatMatierePremiereList )   {
        StringBuilder query = new StringBuilder("INSERT INTO block (longueur, largeur, epaisseur, prix_revient, creation_block, name, machine_id, prix_theorique) VALUES ");
        try (CSVParser csvParser = new CSVParser(new InputStreamReader(inputStream), CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            int id = 1 ;
            for (CSVRecord record : csvParser) {
                try {
                    // Parse values from the CSV
                    Double longueur = Double.valueOf(record.get("longueur"));

                    Double largeur = Double.valueOf(record.get("largeur"));

                    Double epaisseur = Double.valueOf(record.get("epaisseur"));

                    Double prix_revient = Double.valueOf(record.get("prix_revient"));

                    Date creation_block = StringUtils.convertStringToDate(record.get("creation_block"));  // Assuming you have a utility method to convert string to date
                    String name = "Block"+id; // Corrected here to get the actual name

                    int machine_id = Integer.parseInt(record.get("machine_id"));


                    // Calculate volume and prix_theoriqu
                   double volume = longueur * largeur * epaisseur;

                   double prixTheorique = blockService.getprixRevientTheorique(creation_block, volume, formuleDetails , achatMatierePremiereList);

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
                    System.out.println("ligne id = " + id);
                    id++;

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




    //GENERATE CSV RANDOM FOR BLOCK
    public void genererBlockCSV(int numBlocks, Double prixVolumique, long minMachineId, long maxMachineId, String filePath) throws Exception {
        if (numBlocks <= 0 || minMachineId > maxMachineId) {
            throw new IllegalArgumentException("Invalid input parameters.");
        }

        if ( prixVolumique == 0 || prixVolumique.isNaN() ){
            throw new Exception("the prixVolumique should not be 0 ");
        }

        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<String> csvLines = new ArrayList<>(numBlocks);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;

        for (int i = 1; i <= numBlocks; i++) {
            // Generate block name
            String blocName = "Block" + i;

            // Generate dimensions
            double longueur = Math.round((MIN_LONGUEUR + random.nextDouble() * (MAX_LONGUEUR - MIN_LONGUEUR)) * 100.0) / 100.0;
            double largeur = Math.round((MIN_LARGEUR + random.nextDouble() * (MAX_LARGEUR - MIN_LARGEUR)) * 100.0) / 100.0;
            double epaisseur = Math.round((MIN_EPAISSEUR + random.nextDouble() * (MAX_EPAISSEUR - MIN_EPAISSEUR)) * 100.0) / 100.0;

            // Calculate volume and production cost
            double volume = longueur * largeur * epaisseur;
            double variation = MIN_VARIATION + random.nextDouble() * (MAX_VARIATION - MIN_VARIATION);
            variation = Math.round(variation * 10.0) / 10.0;
            double prix_revient = prixVolumique * volume * variation;

            // Generate machine ID
            long machineId = random.nextLong(minMachineId, maxMachineId + 1);

            // Générer une date aléatoire entre startDate et endDate
            LocalDate randomDate = null;
            do {
                long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
                randomDate = startDate.plusDays(ThreadLocalRandom.current().nextLong(daysBetween + 1));
            } while (randomDate.getDayOfWeek() == DayOfWeek.SATURDAY || randomDate.getDayOfWeek() == DayOfWeek.SUNDAY);


            // Add CSV line
            csvLines.add(String.format(Locale.US,
                    "%s,%.2f,%.2f,%.2f,%.2f,%d,%s",
                    blocName, longueur, largeur, epaisseur, prix_revient, machineId, randomDate.format(formatter)));
        }

        // Ensure the parent directories of the file exist
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            if (!parentDir.mkdirs()) {
                throw new IOException("Failed to create directories for file path: " + filePath);
            }
        }

        // Write data to a CSV file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("name,longueur,largeur,epaisseur,prix_revient,machine_id,creation_block\n");
            int progession = 0 ;
            for (String line : csvLines) {
                writer.write(line + "\n");
                System.out.println("Generated at line = "  + progession );
                progession++;
            }
            System.out.println("CSV file generated: " + file.getAbsolutePath());
        } catch (IOException e) {
            System.err.println("Error generating CSV file: " + e.getMessage());
            throw new Exception("Error generating CSV file: " + e.getMessage());
        }
    }


    public String generateBlockQueryWithReste(int numblock , Double prixVolumique , int minMachineId, int maxMachineId , String filePath ,  List<AchatMatierePremiere> achatMatierePremiereList) throws Exception {

        if ( prixVolumique == 0 || prixVolumique.isNaN() ){
            throw new Exception("Prix Volumique 0");
        }

        List<FormuleDetails> formuleDetails = formuleDetailsService.getAllFormuleDetails();
        StringBuilder query = new StringBuilder("INSERT INTO block (longueur, largeur, epaisseur, prix_revient, creation_block, name, machine_id, prix_theorique) VALUES ");
        List<String> csvLines = new ArrayList<>(numblock);
        try {
            double maxVolume = blockService.maxVolume();
            System.out.println("maxVolume = " + maxVolume);
            if (maxVolume <= 0 ){
                throw new  RuntimeException("reste stock = 0 ") ;
            }else {
                List<Double> volumes = RandomUtils.divideVolume(numblock, numblock);
                Random random = new Random();
                int id = 1 ;
                for (Double volmue : volumes){
                    double [] dimensions = RandomUtils.generateDimensions(volmue);
                    double longueur = dimensions[0];
                    double largeur = dimensions[1];
                    double epaisseur = dimensions[2];
                    double prix_revient = prixVolumique * volmue;
                    long machine_id = random.nextInt(minMachineId, maxMachineId);

                    // Generate a random date between startDate and endDate
                    LocalDate randomDate = null;
                    do {
                        long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
                        randomDate = startDate.plusDays(ThreadLocalRandom.current().nextLong(daysBetween + 1));
                    } while (randomDate.getDayOfWeek() == DayOfWeek.SATURDAY || randomDate.getDayOfWeek() == DayOfWeek.SUNDAY);
                    Date creation_block = Date.valueOf(randomDate) ;

                    //Block name
                    String name = "Bock"+id ;

                    double prixTheorique = blockService.getprixRevientTheorique(creation_block, volmue, formuleDetails , achatMatierePremiereList);
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
                    System.out.println("ligne id = " + id);
                    // Add CSV line
                    csvLines.add(String.format(Locale.US,
                            "%s,%.2f,%.2f,%.2f,%.2f,%d,%s,%.2f",
                            name, longueur, largeur, epaisseur, prix_revient, machine_id,creation_block , prixTheorique ));
                    id++;
            }
                // Remove trailing comma and space
                if (query.length() > 0) {
                    query.setLength(query.length() - 2);
                }

                // Ensure the parent directories of the file exist
                File file = new File(filePath);
                File parentDir = file.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    if (!parentDir.mkdirs()) {
                        throw new IOException("Failed to create directories for file path: " + filePath);
                    }
                }

                // Write data to a CSV file
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("name,longueur,largeur,epaisseur,prix_revient,machine_id,creation_block,prixTheorique\n");
                    int progession = 0 ;
                    for (String line : csvLines) {
                        writer.write(line + "\n");
                        System.out.println("Generated at line = "  + progession );
                        progession++;
                    }
                    System.out.println("CSV file generated: " + file.getAbsolutePath());
                } catch (IOException e) {
                    System.err.println("Error generating CSV file: " + e.getMessage());
                    throw new Exception("Error generating CSV file: " + e.getMessage());
                }
                return query.toString();
            }
        } catch (Exception e) {
            // Log any errors that happen outside the loop (like CSV parsing issues)
            System.err.println("Error generating the CSV file: " + e.getMessage());
            throw new RuntimeException("Error generating  the CSV file: " + e.getMessage(), e);
        }
    }


}
