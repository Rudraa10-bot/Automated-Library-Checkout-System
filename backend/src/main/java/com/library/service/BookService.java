package com.library.service;

import com.library.entity.Book;
import com.library.entity.BookRequest;
import com.library.entity.Transaction;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.BookRequestRepository;
import com.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private BookRequestRepository bookRequestRepository;
    
    public Optional<Book> findByBarcode(String barcode) {
        return bookRepository.findByBarcode(barcode);
    }
    
    public List<Book> findAll() {
        return bookRepository.findAll();
    }
    
    public List<Book> findAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
    
    public List<Book> searchBooks(String searchTerm) {
        return bookRepository.searchBooks(searchTerm);
    }
    
    public Book save(Book book) {
        return bookRepository.save(book);
    }
    
    public boolean isBookAvailable(String barcode) {
        Optional<Book> bookOpt = findByBarcode(barcode);
        return bookOpt.isPresent() && bookOpt.get().isAvailable();
    }
    
    public Transaction issueBook(User user, String barcode) {
        Book book = findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Book not found with barcode: " + barcode));
        
        if (!book.isAvailable()) {
            throw new RuntimeException("Book is not available for issue");
        }
        
        // Check if user already has this book issued
        Optional<Transaction> existingIssue = transactionRepository
                .findActiveIssueByUserAndBook(user, book);
        if (existingIssue.isPresent()) {
            throw new RuntimeException("User already has this book issued");
        }
        
        // Issue the book
        book.issueBook();
        bookRepository.save(book);
        
        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setBook(book);
        transaction.setType(Transaction.TransactionType.ISSUE);
        transaction.setStatus(Transaction.TransactionStatus.ACTIVE);
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction returnBook(User user, String barcode) {
        Book book = findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Book not found with barcode: " + barcode));
        
        // Find active issue for this user and book
        Transaction activeIssue = transactionRepository
                .findActiveIssueByUserAndBook(user, book)
                .orElseThrow(() -> new RuntimeException("No active issue found for this book"));
        
        // Return the book
        book.returnBook();
        bookRepository.save(book);
        
        // Update transaction
        activeIssue.setType(Transaction.TransactionType.RETURN);
        activeIssue.setStatus(Transaction.TransactionStatus.COMPLETED);
        activeIssue.setReturnDate(java.time.LocalDateTime.now());
        
        Transaction returnTransaction = transactionRepository.save(activeIssue);
        
        // Check for pending requests and notify users
        notifyPendingRequests(book);
        
        return returnTransaction;
    }
    
    public BookRequest requestBook(User user, String barcode) {
        Book book = findByBarcode(barcode)
                .orElseThrow(() -> new RuntimeException("Book not found with barcode: " + barcode));
        
        // Check if user already has a pending request for this book
        Optional<BookRequest> existingRequest = bookRequestRepository
                .findPendingRequestByUserAndBook(user, book);
        if (existingRequest.isPresent()) {
            throw new RuntimeException("User already has a pending request for this book");
        }
        
        // Create book request
        BookRequest bookRequest = new BookRequest();
        bookRequest.setUser(user);
        bookRequest.setBook(book);
        bookRequest.setStatus(BookRequest.RequestStatus.PENDING);
        
        return bookRequestRepository.save(bookRequest);
    }
    
    private void notifyPendingRequests(Book book) {
        List<BookRequest> pendingRequests = bookRequestRepository
                .findPendingRequestsByBook(book);
        
        for (BookRequest request : pendingRequests) {
            request.setStatus(BookRequest.RequestStatus.NOTIFIED);
            request.setNotificationSent(true);
            request.setNotificationDate(java.time.LocalDateTime.now());
            bookRequestRepository.save(request);
        }
    }
    
    public List<BookRequest> getPendingRequests() {
        return bookRequestRepository.findAllPendingRequests();
    }
    
    public List<BookRequest> getNotifiedRequests() {
        return bookRequestRepository.findAllNotifiedRequests();
    }
}










