package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.Book;
import com.library.service.BookDataService;
import com.library.service.BookService;
import com.library.util.BarcodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for book management operations (Admin only)
 */
@RestController
@RequestMapping("/api/admin/books")
@CrossOrigin(origins = "*")
public class BookManagementController {
    
    @Autowired
    private BookDataService bookDataService;
    
    @Autowired
    private BookService bookService;
    
    /**
     * Add missing barcodes to books
     */
    @PostMapping("/add-missing-barcodes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> addMissingBarcodes() {
        try {
            int updatedCount = bookDataService.addMissingBarcodes();
            Map<String, Object> result = new HashMap<>();
            result.put("updatedBooks", updatedCount);
            result.put("message", "Successfully added barcodes to " + updatedCount + " books");
            
            return ResponseEntity.ok(ApiResponse.success("Barcodes added successfully", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to add barcodes: " + e.getMessage()));
        }
    }
    
    /**
     * Validate barcode uniqueness
     */
    @GetMapping("/validate-barcodes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> validateBarcodes() {
        try {
            boolean isValid = bookDataService.validateBarcodeUniqueness();
            List<Book> booksWithoutBarcodes = bookDataService.getBooksWithoutBarcodes();
            
            Map<String, Object> result = new HashMap<>();
            result.put("isValid", isValid);
            result.put("booksWithoutBarcodes", booksWithoutBarcodes.size());
            result.put("booksWithoutBarcodesList", booksWithoutBarcodes);
            
            String message = isValid ? "All barcodes are unique" : "Found duplicate or missing barcodes";
            return ResponseEntity.ok(ApiResponse.success(message, result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to validate barcodes: " + e.getMessage()));
        }
    }
    
    /**
     * Generate a new barcode for a specific category
     */
    @PostMapping("/generate-barcode")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateBarcode(@RequestParam String category) {
        try {
            String barcode = bookDataService.generateBarcodeForCategory(category);
            Map<String, String> result = new HashMap<>();
            result.put("barcode", barcode);
            result.put("category", category);
            
            return ResponseEntity.ok(ApiResponse.success("Barcode generated successfully", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to generate barcode: " + e.getMessage()));
        }
    }
    
    /**
     * Check if a barcode exists
     */
    @GetMapping("/barcode-exists/{barcode}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> checkBarcodeExists(@PathVariable String barcode) {
        try {
            boolean exists = bookDataService.barcodeExists(barcode);
            Map<String, Object> result = new HashMap<>();
            result.put("barcode", barcode);
            result.put("exists", exists);
            
            return ResponseEntity.ok(ApiResponse.success("Barcode check completed", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to check barcode: " + e.getMessage()));
        }
    }
    
    /**
     * Get books without barcodes
     */
    @GetMapping("/without-barcodes")
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksWithoutBarcodes() {
        try {
            List<Book> books = bookDataService.getBooksWithoutBarcodes();
            return ResponseEntity.ok(ApiResponse.success("Books without barcodes retrieved", books));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve books: " + e.getMessage()));
        }
    }
}




