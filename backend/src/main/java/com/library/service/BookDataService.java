package com.library.service;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import com.library.util.BarcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing book data and barcodes
 */
@Service
@Transactional
public class BookDataService {
    
    @Autowired
    private BookRepository bookRepository;
    
    /**
     * Add barcodes to books that don't have them
     */
    public int addMissingBarcodes() {
        List<Book> booksWithoutBarcodes = bookRepository.findBooksWithoutBarcodes();
        int updatedCount = 0;
        
        for (Book book : booksWithoutBarcodes) {
            String barcode = generateUniqueBarcode();
            book.setBarcode(barcode);
            bookRepository.save(book);
            updatedCount++;
        }
        
        return updatedCount;
    }
    
    /**
     * Generate a unique barcode that doesn't exist in the database
     */
    private String generateUniqueBarcode() {
        String barcode;
        do {
            barcode = BarcodeGenerator.generateBookBarcode();
        } while (bookRepository.findByBarcode(barcode).isPresent());
        
        return barcode;
    }
    
    /**
     * Validate that all books have unique barcodes
     */
    public boolean validateBarcodeUniqueness() {
        List<Book> allBooks = bookRepository.findAll();
        return allBooks.stream()
                .map(Book::getBarcode)
                .distinct()
                .count() == allBooks.size();
    }
    
    /**
     * Get books without barcodes
     */
    public List<Book> getBooksWithoutBarcodes() {
        return bookRepository.findBooksWithoutBarcodes();
    }
    
    /**
     * Check if a barcode exists
     */
    public boolean barcodeExists(String barcode) {
        return bookRepository.findByBarcode(barcode).isPresent();
    }
    
    /**
     * Generate barcode for a specific category
     */
    public String generateBarcodeForCategory(String category) {
        String barcode;
        do {
            switch (category.toUpperCase()) {
                case "PROGRAMMING":
                    barcode = BarcodeGenerator.generateProgrammingBarcode();
                    break;
                case "COMPUTER_SCIENCE":
                    barcode = BarcodeGenerator.generateComputerScienceBarcode();
                    break;
                case "WEB":
                    barcode = BarcodeGenerator.generateWebBarcode();
                    break;
                case "DATA_SCIENCE":
                    barcode = BarcodeGenerator.generateDataScienceBarcode();
                    break;
                case "SOFTWARE_ENGINEERING":
                    barcode = BarcodeGenerator.generateSoftwareEngineeringBarcode();
                    break;
                default:
                    barcode = BarcodeGenerator.generateBookBarcode();
            }
        } while (bookRepository.findByBarcode(barcode).isPresent());
        
        return barcode;
    }
}







