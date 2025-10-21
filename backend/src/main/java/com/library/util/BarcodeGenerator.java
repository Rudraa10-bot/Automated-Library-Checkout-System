package com.library.util;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique barcodes for books
 */
@Component
public class BarcodeGenerator {
    
    private static final AtomicLong counter = new AtomicLong(1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    /**
     * Generate a unique barcode with prefix
     * Format: PREFIX + YYYYMMDD + 4-digit counter
     * Example: BOOK202412010001
     */
    public static String generateBarcode(String prefix) {
        String date = LocalDateTime.now().format(DATE_FORMATTER);
        String counterStr = String.format("%04d", counter.getAndIncrement());
        return prefix + date + counterStr;
    }
    
    /**
     * Generate a book barcode
     * Format: BOOK + YYYYMMDD + 4-digit counter
     */
    public static String generateBookBarcode() {
        return generateBarcode("BOOK");
    }
    
    /**
     * Generate a programming book barcode
     * Format: PROG + YYYYMMDD + 4-digit counter
     */
    public static String generateProgrammingBarcode() {
        return generateBarcode("PROG");
    }
    
    /**
     * Generate a computer science book barcode
     * Format: CS + YYYYMMDD + 4-digit counter
     */
    public static String generateComputerScienceBarcode() {
        return generateBarcode("CS");
    }
    
    /**
     * Generate a web development book barcode
     * Format: WEB + YYYYMMDD + 4-digit counter
     */
    public static String generateWebBarcode() {
        return generateBarcode("WEB");
    }
    
    /**
     * Generate a data science book barcode
     * Format: DATA + YYYYMMDD + 4-digit counter
     */
    public static String generateDataScienceBarcode() {
        return generateBarcode("DATA");
    }
    
    /**
     * Generate a software engineering book barcode
     * Format: SE + YYYYMMDD + 4-digit counter
     */
    public static String generateSoftwareEngineeringBarcode() {
        return generateBarcode("SE");
    }
    
    /**
     * Validate barcode format
     * @param barcode the barcode to validate
     * @return true if valid format
     */
    public static boolean isValidBarcode(String barcode) {
        if (barcode == null || barcode.trim().isEmpty()) {
            return false;
        }
        
        // Basic validation: should be at least 8 characters and contain alphanumeric
        return barcode.length() >= 8 && barcode.matches("^[A-Z0-9]+$");
    }
    
    /**
     * Reset the counter (useful for testing)
     */
    public static void resetCounter() {
        counter.set(1);
    }
}







