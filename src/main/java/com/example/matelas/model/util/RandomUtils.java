package com.example.matelas.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomUtils {
    public static List<Integer> divideVolume(double totalVolume, int parts) {
        if (totalVolume <= 0 || parts <= 0) {
            throw new IllegalArgumentException("Total volume and parts must be greater than 0.");
        }

        List<Integer> volumes = new ArrayList<>();
        Random random = new Random();
        double remainingVolume = totalVolume;

        // Generate random weights
        List<Double> weights = new ArrayList<>();
        for (int i = 0; i < parts; i++) {
            weights.add(random.nextDouble());
        }

        // Normalize weights to sum to 1
        double weightSum = weights.stream().mapToDouble(Double::doubleValue).sum();
        for (int i = 0; i < weights.size(); i++) {
            weights.set(i, weights.get(i) / weightSum);
        }

        // Assign volumes based on weights
        for (int i = 0; i < parts - 1; i++) {
            int part = (int) Math.round(weights.get(i) * totalVolume);
            part = Math.max(1, Math.min(part, (int) remainingVolume - (parts - i - 1))); // Ensure each part >= 1
            volumes.add(part);
            remainingVolume -= part;
        }

        // Ensure the last part matches the remaining volume
        volumes.add((int) Math.round(remainingVolume));

        // Shuffle to mix the values
        Collections.shuffle(volumes);

        return volumes;
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
}
