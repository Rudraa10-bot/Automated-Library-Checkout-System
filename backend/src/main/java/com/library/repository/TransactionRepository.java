package com.library.repository;

import com.library.entity.Transaction;
import com.library.entity.User;
import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    
    List<Transaction> findByUserOrderByTransactionDateDesc(User user);
    
    List<Transaction> findByBookOrderByTransactionDateDesc(Book book);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.type = 'ISSUE' AND t.status = 'ACTIVE'")
    List<Transaction> findActiveIssuesByUser(@Param("user") User user);
    
    @Query("SELECT t FROM Transaction t WHERE t.book = :book AND t.type = 'ISSUE' AND t.status = 'ACTIVE'")
    Optional<Transaction> findActiveIssueByBook(@Param("book") Book book);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user AND t.book = :book AND t.type = 'ISSUE' AND t.status = 'ACTIVE'")
    Optional<Transaction> findActiveIssueByUserAndBook(@Param("user") User user, @Param("book") Book book);
    
    @Query("SELECT t FROM Transaction t WHERE t.dueDate < :currentDate AND t.status = 'ACTIVE' AND t.type = 'ISSUE'")
    List<Transaction> findOverdueTransactions(@Param("currentDate") LocalDateTime currentDate);
    
    @Query("SELECT COUNT(t) FROM Transaction t WHERE t.user = :user AND t.type = 'ISSUE' AND t.status = 'ACTIVE'")
    Long countActiveIssuesByUser(@Param("user") User user);
    
    @Query("SELECT t FROM Transaction t WHERE t.user = :user ORDER BY t.transactionDate DESC")
    List<Transaction> findUserHistory(@Param("user") User user);
}











