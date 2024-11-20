package com.example.matelas.model.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class StringUtils {
    public static java.sql.Date convertStringToDate(String dateString) throws ParseException {
        // Define the possible date formats
        String[] dateFormats = {
                "yyyy-dd-yy", // First format: yyyy-dd-yy
                "MM/dd/yyyy"  // Second format: MM/dd/yyyy
        };

        // Try each date format
        for (String format : dateFormats) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat(format);
                java.util.Date utilDate = dateFormat.parse(dateString);
                return new java.sql.Date(utilDate.getTime());
            } catch (Exception e) {
                // Continue to the next format if parsing fails
                e.printStackTrace();
            }
        }

        // If no format worked, throw an exception
        throw new ParseException("Invalid date format: " + dateString, 0);
    }

}
