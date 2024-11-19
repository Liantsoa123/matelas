package com.example.matelas.model.util;

public class MeasurementConverter {
    public static double convertToMeters(String input) {
        // Trim spaces and check if the input contains a unit
        input = input.trim();

        // If the input does not contain a unit, assume it's in meters
        if (input.matches("[0-9]+")) {
            return Double.parseDouble(input); // Return the value as meters if no unit is present
        }

        // Otherwise, separate the number and unit
        StringBuilder chiffre = new StringBuilder();
        StringBuilder unit = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                chiffre.append(c);  // Append to chiffre (number) if it's a digit
            } else {
                unit.append(c);      // Append to unit if it's a letter
            }
        }

        // Parse the number and get the unit as a string
        double value = Double.parseDouble(chiffre.toString());
        String unitStr = unit.toString().toLowerCase();

        // Convert to meters based on the unit
        switch (unitStr) {
            case "mm":  // millimeters to meters
                return value / 1000;
            case "cm":  // centimeters to meters
                return value / 100;
            case "dm":  // decimeters to meters
                return value / 10;
            case "m":   // meters (no conversion needed)
                return value;
            case "km":  // kilometers to meters
                return value * 1000;
            case "in":  // inches to meters
                return value * 0.0254;
            case "ft":  // feet to meters
                return value * 0.3048;
            case "yd":  // yards to meters
                return value * 0.9144;
            default:
                throw new IllegalArgumentException("Invalid unit. Supported units: mm, cm, dm, m, km, in, ft, yd.");
        }
    }

}
