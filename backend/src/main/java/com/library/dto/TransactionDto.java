package com.library.dto;

import com.library.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    
    private Long id;
    private String username;
    private String bookTitle;
    private String bookBarcode;
    private String type;
    private LocalDateTime transactionDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private String status;
    private Double fineAmount;
    private String notes;
    
    public static TransactionDto fromTransaction(Transaction transaction) {
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setUsername(transaction.getUser().getUsername());
        dto.setBookTitle(transaction.getBook().getTitle());
        dto.setBookBarcode(transaction.getBook().getBarcode());
        dto.setType(transaction.getType().name());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setDueDate(transaction.getDueDate());
        dto.setReturnDate(transaction.getReturnDate());
        dto.setStatus(transaction.getStatus().name());
        dto.setFineAmount(transaction.getFineAmount());
        dto.setNotes(transaction.getNotes());
        return dto;
    }
}











