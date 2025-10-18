package com.library.repository;

import com.library.entity.BookRequest;
import com.library.entity.User;
import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRequestRepository extends JpaRepository<BookRequest, Long> {
    
    List<BookRequest> findByUserOrderByRequestDateDesc(User user);
    
    List<BookRequest> findByBookOrderByRequestDateDesc(Book book);
    
    @Query("SELECT br FROM BookRequest br WHERE br.user = :user AND br.book = :book AND br.status = 'PENDING'")
    Optional<BookRequest> findPendingRequestByUserAndBook(@Param("user") User user, @Param("book") Book book);
    
    @Query("SELECT br FROM BookRequest br WHERE br.book = :book AND br.status = 'PENDING' ORDER BY br.requestDate ASC")
    List<BookRequest> findPendingRequestsByBook(@Param("book") Book book);
    
    @Query("SELECT br FROM BookRequest br WHERE br.status = 'PENDING'")
    List<BookRequest> findAllPendingRequests();
    
    @Query("SELECT br FROM BookRequest br WHERE br.user = :user AND br.status = 'PENDING'")
    List<BookRequest> findPendingRequestsByUser(@Param("user") User user);
    
    @Query("SELECT br FROM BookRequest br WHERE br.status = 'NOTIFIED'")
    List<BookRequest> findAllNotifiedRequests();
}










