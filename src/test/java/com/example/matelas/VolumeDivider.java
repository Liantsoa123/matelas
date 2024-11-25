package com.example.matelas;

import com.example.matelas.model.block.BlockService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VolumeDivider {
    public static List<Double> divideVolume(double totalVolume, int parts) {
        if (totalVolume <= 0 || parts <= 0) {
            throw new IllegalArgumentException("Total volume and parts must be greater than 0.");
        }

        List<Double> volumes = new ArrayList<>();
        Random random = new Random();
        double remainingVolume = totalVolume;

        // Generate random weights for each part
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < parts; i++) {
            weights.add(random.nextDouble());
        }

        // Normalize weights so they sum to 1
        double weightSum = weights.stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) / weightSum);
        }

        // Assign volumes based on normalized weights
        for (int i = 0; i < parts - 1; i++) {
            double part = weights.get(i) * totalVolume;
            part = Math.max(0.01, Math.min(part, remainingVolume - (parts - i - 1) * 0.01)); // Ensure each part >= 0.01
            volumes.add(roundToTwoDecimalPlaces(part));
            remainingVolume -= part;
        }

        // Assign the last part as the remaining volume and round to two decimal places
        volumes.add(roundToTwoDecimalPlaces(remainingVolume));

        // Shuffle the volumes to mix the values
        Collections.shuffle(volumes);

        return volumes;
    }

    // Helper method to round to two decimal places
    private static double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    public static double[] generateDimensions(double volume) {
        if (volume <= 0) {
            throw new IllegalArgumentException("Volume must be greater than 0.");
        }

        Random random = new Random();
        double longueur, largeur = 1, epaisseur = 1;

        // Limiter les tentatives
        int maxIterations = 10000;
        int attempts = 0;

        while (attempts++ < maxIterations) {
            // Générer longueur et largeur comme des fractions du volume
            longueur = Math.round((1 + (volume - 1) * random.nextDouble()) * 100.0) / 100.0;
            largeur = Math.round((1 + (volume / longueur - 1) * random.nextDouble()) * 100.0) / 100.0;
            epaisseur = Math.round((volume / (longueur * largeur)) * 100.0) / 100.0;

            // Vérifier que les dimensions sont valides
            if (longueur > 0 && largeur > 0 && epaisseur > 0 && (longueur * largeur * epaisseur) <= volume) {
                System.out.println("longueur = " + longueur + ", largeur = " + largeur + ", epaisseur = " + epaisseur);
                return new double[]{longueur, largeur, epaisseur};
            }
        }

        // Si le nombre d'essais maximum est atteint, retourner les dimensions par défaut
        System.out.println("Nombre d'essais maximum atteint, retour avec largeur = 1 et épaisseur = 1.");
        return new double[]{1, 1, volume};
    }
    public static void main(String[] args) {
            List<Double> volumes = divideVolume(100, 50);
            for (Double d : volumes) {
                System.out.println(d);
            }
        }
    }

