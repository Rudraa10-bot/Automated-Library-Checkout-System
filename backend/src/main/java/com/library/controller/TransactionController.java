package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.TransactionDto;
import com.library.entity.Transaction;
import com.library.entity.User;
import com.library.service.TransactionService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "*")
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<TransactionDto>>> getUserHistory(Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            List<TransactionDto> history = transactionService.getUserHistory(user);
            return ResponseEntity.ok(ApiResponse.success("Transaction history retrieved successfully", history));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve transaction history: " + e.getMessage()));
        }
    }
    
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<Transaction>>> getActiveIssues(Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            List<Transaction> activeIssues = transactionService.findActiveIssuesByUser(user);
            return ResponseEntity.ok(ApiResponse.success("Active issues retrieved successfully", activeIssues));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve active issues: " + e.getMessage()));
        }
    }
    
    @GetMapping("/count")
    public ResponseEntity<ApiResponse<Long>> getActiveIssuesCount(Authentication authentication) {
        try {
            User user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            Long count = transactionService.countActiveIssuesByUser(user);
            return ResponseEntity.ok(ApiResponse.success("Active issues count retrieved successfully", count));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve active issues count: " + e.getMessage()));
        }
    }
    
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Transaction>>> getAllTransactions() {
        try {
            List<Transaction> transactions = transactionService.getAllTransactions();
            return ResponseEntity.ok(ApiResponse.success("All transactions retrieved successfully", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve transactions: " + e.getMessage()));
        }
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<ApiResponse<List<Transaction>>> getOverdueTransactions() {
        try {
            List<Transaction> overdueTransactions = transactionService.findOverdueTransactions();
            return ResponseEntity.ok(ApiResponse.success("Overdue transactions retrieved successfully", overdueTransactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve overdue transactions: " + e.getMessage()));
        }
    }
}











