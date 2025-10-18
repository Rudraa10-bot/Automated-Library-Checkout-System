package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Barcode is required")
    @Column(unique = true, nullable = false)
    private String barcode;
    
    @NotBlank(message = "Title is required")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "Author is required")
    @Column(nullable = false)
    private String author;
    
    @NotBlank(message = "ISBN is required")
    @Column(unique = true, nullable = false)
    private String isbn;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @NotNull(message = "Total copies is required")
    @Column(name = "total_copies", nullable = false)
    private Integer totalCopies;
    
    @NotNull(message = "Available copies is required")
    @Column(name = "available_copies", nullable = false)
    private Integer availableCopies;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus status = BookStatus.AVAILABLE;
    
    @Column(name = "publication_year")
    private Integer publicationYear;
    
    @Column(name = "publisher")
    private String publisher;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
    
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookRequest> bookRequests;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (availableCopies == null) {
            availableCopies = totalCopies;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    public enum BookStatus {
        AVAILABLE, UNAVAILABLE, MAINTENANCE
    }
    
    // Helper methods
    public boolean isAvailable() {
        return status == BookStatus.AVAILABLE && availableCopies > 0;
    }
    
    public void issueBook() {
        if (availableCopies > 0) {
            availableCopies--;
            if (availableCopies == 0) {
                status = BookStatus.UNAVAILABLE;
            }
        }
    }
    
    public void returnBook() {
        availableCopies++;
        if (status == BookStatus.UNAVAILABLE && availableCopies > 0) {
            status = BookStatus.AVAILABLE;
        }
    }
}










