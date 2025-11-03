package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.dto.BookIssueRequest;
import com.library.dto.BookReturnRequest;
import com.library.dto.TransactionDto;
import com.library.entity.Book;
import com.library.entity.Transaction;
import com.library.entity.LibraryUser;
import com.library.entity.BookRequest;
import com.library.service.BookService;
import com.library.service.UserService;
import com.library.repository.BookRequestRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookRequestRepository bookRequestRepository;
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        try {
            List<Book> books = bookService.findAll();
            return ResponseEntity.ok(ApiResponse.success("Books retrieved successfully", books));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve books: " + e.getMessage()));
        }
    }
    
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Book>>> getAvailableBooks() {
        try {
            List<Book> books = bookService.findAvailableBooks();
            return ResponseEntity.ok(ApiResponse.success("Available books retrieved successfully", books));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve available books: " + e.getMessage()));
        }
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Book>>> searchBooks(@RequestParam String query) {
        try {
            List<Book> books = bookService.searchBooks(query);
            return ResponseEntity.ok(ApiResponse.success("Search results retrieved successfully", books));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Search failed: " + e.getMessage()));
        }
    }

    @GetMapping("/advanced-search")
    public ResponseEntity<ApiResponse<List<Book>>> advancedSearch(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean availableOnly,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String order
    ) {
        try {
            List<Book> books = bookService.advancedSearch(query, availableOnly, yearFrom, yearTo, sortBy, order);
            return ResponseEntity.ok(ApiResponse.success("Advanced search results", books));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Advanced search failed: " + e.getMessage()));
        }
    }
    
    @GetMapping("/barcode/{barcode}")
    public ResponseEntity<ApiResponse<Book>> getBookByBarcode(@PathVariable String barcode) {
        try {
            Book book = bookService.findByBarcode(barcode)
                    .orElseThrow(() -> new RuntimeException("Book not found with barcode: " + barcode));
            return ResponseEntity.ok(ApiResponse.success("Book retrieved successfully", book));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to retrieve book: " + e.getMessage()));
        }
    }
    
    @PostMapping("/issue")
    public ResponseEntity<ApiResponse<TransactionDto>> issueBook(@Valid @RequestBody BookIssueRequest request, 
                                                             Authentication authentication) {
        try {
            LibraryUser user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("LibraryUser not found"));
            
            Transaction transaction = bookService.issueBook(user, request.getBarcode());
            TransactionDto transactionDto = TransactionDto.fromTransaction(transaction);
            return ResponseEntity.ok(ApiResponse.success("Book issued successfully", transactionDto));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to issue book: " + e.getMessage()));
        }
    }
    
    @PostMapping("/return")
    public ResponseEntity<ApiResponse<TransactionDto>> returnBook(@Valid @RequestBody BookReturnRequest request, 
                                                              Authentication authentication) {
        try {
            LibraryUser user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("LibraryUser not found"));
            
            Transaction transaction = bookService.returnBook(user, request.getBarcode());
            TransactionDto transactionDto = TransactionDto.fromTransaction(transaction);
            return ResponseEntity.ok(ApiResponse.success("Book returned successfully", transactionDto));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to return book: " + e.getMessage()));
        }
    }
    
    @PostMapping("/request")
    public ResponseEntity<ApiResponse<String>> requestBook(@Valid @RequestBody BookIssueRequest request, 
                                                           Authentication authentication) {
        try {
            LibraryUser user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("LibraryUser not found"));
            
            bookService.requestBook(user, request.getBarcode());
            return ResponseEntity.ok(ApiResponse.success("Book request submitted successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to request book: " + e.getMessage()));
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<ApiResponse<List<BookRequest>>> myBookRequests(Authentication authentication) {
        try {
            LibraryUser user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("LibraryUser not found"));
            List<BookRequest> list = bookService.getPendingRequests().stream()
                    .filter(r -> r.getUser().getId().equals(user.getId()))
                    .toList();
            return ResponseEntity.ok(ApiResponse.success("Requests loaded", list));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to load requests: " + e.getMessage()));
        }
    }

    @DeleteMapping("/requests/{id}")
    public ResponseEntity<ApiResponse<String>> cancelRequest(@PathVariable Long id, Authentication authentication) {
        try {
            LibraryUser user = userService.findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("LibraryUser not found"));
            var opt = bookService.getPendingRequests().stream().filter(r -> r.getId().equals(id)).findFirst();
            if (opt.isEmpty() || !opt.get().getUser().getId().equals(user.getId())) {
                return ResponseEntity.status(404).body(ApiResponse.error("Request not found"));
            }
            // Soft-cancel by setting status
            var req = opt.get();
            req.setStatus(BookRequest.RequestStatus.CANCELLED);
            bookRequestRepository.save(req);
            return ResponseEntity.ok(ApiResponse.success("Request cancelled"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to cancel request: " + e.getMessage()));
        }
    }
    
    @GetMapping("/check-availability/{barcode}")
    public ResponseEntity<ApiResponse<Boolean>> checkAvailability(@PathVariable String barcode) {
        try {
            boolean isAvailable = bookService.isBookAvailable(barcode);
            return ResponseEntity.ok(ApiResponse.success("Availability checked successfully", isAvailable));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Failed to check availability: " + e.getMessage()));
        }
    }
}






