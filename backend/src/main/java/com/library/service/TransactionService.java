package com.library.service;

import com.library.dto.TransactionDto;
import com.library.entity.Transaction;
import com.library.entity.User;
import com.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    public List<TransactionDto> getUserHistory(User user) {
        List<Transaction> transactions = transactionRepository.findUserHistory(user);
        return transactions.stream()
                .map(TransactionDto::fromTransaction)
                .collect(Collectors.toList());
    }
    
    public List<Transaction> findActiveIssuesByUser(User user) {
        return transactionRepository.findActiveIssuesByUser(user);
    }
    
    public Long countActiveIssuesByUser(User user) {
        return transactionRepository.countActiveIssuesByUser(user);
    }
    
    public List<Transaction> findOverdueTransactions() {
        return transactionRepository.findOverdueTransactions(LocalDateTime.now());
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
    
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }
}
