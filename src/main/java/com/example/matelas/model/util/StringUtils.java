package com.example.matelas.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtils {
    public static java.sql.Date convertStringToDate(String dateString) throws ParseException {
        // Define the possible date formats
        String[] dateFormats = {
                "yyyy-MM-dd", // Corrected format: yyyy-dd-MM (months are MM, not mm)
                "dd/MM/yyyy"  // Second format: MM/dd/yyyy
        };

        // Try each date format
        for (String format : dateFormats) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                dateFormat.setLenient(false); // Enforce strict parsing
                java.util.Date utilDate = dateFormat.parse(dateString);
                return new java.sql.Date(utilDate.getTime());
            } catch (ParseException e) {
                // Suppress error and continue to the next format
            }
        }
        // If no format worked, throw an exception
        throw new ParseException("Invalid date format: " + dateString, 0);
    }

}
