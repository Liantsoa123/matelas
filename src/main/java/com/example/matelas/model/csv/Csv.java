package com.example.matelas.model.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Csv {

    // Constants
    private static final LocalDate START_DATE = LocalDate.of(2022, 1, 1);
    private static final LocalDate END_DATE = LocalDate.of(2024, 12, 31);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_DATE;

    private static final double MIN_LONGUEUR = 20.0, MAX_LONGUEUR = 25.0;
    private static final double MIN_LARGEUR = 5.0, MAX_LARGEUR = 7.0;
    private static final double MIN_EPAISSEUR = 10.0, MAX_EPAISSEUR = 15.0;
    private static final double MIN_VARIATION = 0.9, MAX_VARIATION = 1.1;


    public void generateBlockCSV(int numBlocks, double prixVolumique, long minMachineId, long maxMachineId, String filePath) throws Exception {
        validateInputs(numBlocks, prixVolumique, minMachineId, maxMachineId);

        List<String> csvLines = generateCsvLines(numBlocks, prixVolumique, minMachineId, maxMachineId);

        writeToFile(csvLines, filePath);
    }


    private void validateInputs(int numBlocks, double prixVolumique, long minMachineId, long maxMachineId) {
        if (numBlocks <= 0) {
            throw new IllegalArgumentException("The number of blocks must be greater than zero.");
        }
        if (minMachineId > maxMachineId) {
            throw new IllegalArgumentException("Minimum Machine ID cannot be greater than Maximum Machine ID.");
        }
        if (prixVolumique <= 0 || Double.isNaN(prixVolumique)) {
            throw new IllegalArgumentException("The prixVolumique must be a positive number.");
        }
    }


    private List<String> generateCsvLines(int numBlocks, double prixVolumique, long minMachineId, long maxMachineId) {
        List<String> csvLines = new ArrayList<>(numBlocks);

        for (int i = 1; i <= numBlocks; i++) {
            String blockName = "Block" + i;

            double longueur = generateRandomDouble(MIN_LONGUEUR, MAX_LONGUEUR);
            double largeur = generateRandomDouble(MIN_LARGEUR, MAX_LARGEUR);
            double epaisseur = generateRandomDouble(MIN_EPAISSEUR, MAX_EPAISSEUR);

            double volume = longueur * largeur * epaisseur;
            double variation = generateRandomDouble(MIN_VARIATION, MAX_VARIATION);
            double prixRevient = prixVolumique * volume * variation;

            long machineId = ThreadLocalRandom.current().nextLong(minMachineId, maxMachineId + 1);
            LocalDate creationDate = generateRandomDate();

            csvLines.add(String.format(Locale.US, "%s,%.2f,%.2f,%.2f,%.2f,%d,%s",
                    blockName, longueur, largeur, epaisseur, prixRevient, machineId, creationDate.format(DATE_FORMATTER)));
        }

        return csvLines;
    }


    private double generateRandomDouble(double min, double max) {
        return Math.round((min + ThreadLocalRandom.current().nextDouble() * (max - min)) * 100.0) / 100.0;
    }

    // Generate a random date between START_DATE and END_DATE (excluding weekends)
    private LocalDate generateRandomDate() {
        LocalDate randomDate;
        do {
            long daysBetween = ChronoUnit.DAYS.between(START_DATE, END_DATE);
            randomDate = START_DATE.plusDays(ThreadLocalRandom.current().nextLong(daysBetween + 1));
        } while (randomDate.getDayOfWeek() == DayOfWeek.SATURDAY || randomDate.getDayOfWeek() == DayOfWeek.SUNDAY);
        return randomDate;
    }

    // Write CSV lines to a file
    private void writeToFile(List<String> csvLines, String filePath) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists() && !parentDir.mkdirs()) {
            throw new IOException("Failed to create directories for the file path: " + filePath);
        }

        try (FileWriter writer = new FileWriter(file)) {
            writer.write("name,longueur,largeur,epaisseur,prix_revient,machine_id,creation_block\n");
            for (int i = 0; i < csvLines.size(); i++) {
                writer.write(csvLines.get(i) + "\n");
                if ((i + 1) % 100 == 0 || i == csvLines.size() - 1) {
                    System.out.printf("Progress: %d/%d blocks generated%n", i + 1, csvLines.size());
                }
            }
            System.out.println("CSV file successfully generated: " + file.getAbsolutePath());
        }
    }
}
