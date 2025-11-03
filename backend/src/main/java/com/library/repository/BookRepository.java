package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    Optional<Book> findByBarcode(String barcode);
    
    Optional<Book> findByIsbn(String isbn);
    
    boolean existsByBarcode(String barcode);
    
    boolean existsByIsbn(String isbn);
    
    @Query("SELECT b FROM Book b WHERE b.title LIKE %:title% OR b.author LIKE %:author%")
    List<Book> findByTitleOrAuthorContaining(@Param("title") String title, @Param("author") String author);
    
    @Query("SELECT b FROM Book b WHERE b.availableCopies > 0 AND b.status = 'AVAILABLE'")
    List<Book> findAvailableBooks();
    
    @Query("SELECT b FROM Book b WHERE b.availableCopies = 0 OR b.status = 'UNAVAILABLE'")
    List<Book> findUnavailableBooks();
    
    @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE %:q% OR LOWER(b.author) LIKE %:q% OR LOWER(b.isbn) LIKE %:q% OR LOWER(b.barcode) LIKE %:q%")
    List<Book> searchBooks(@Param("q") String q);
    
    @Query("SELECT b FROM Book b WHERE b.barcode IS NULL OR b.barcode = ''")
    List<Book> findBooksWithoutBarcodes();

    // Additional helpers
    List<Book> findByIdIn(List<Long> ids);

    @Query("SELECT b FROM Book b WHERE b.author IN :authors AND b.id NOT IN :excludeIds")
    List<Book> findByAuthorsExcluding(@Param("authors") List<String> authors, @Param("excludeIds") List<Long> excludeIds);

    List<Book> findTop20ByOrderByCreatedAtDesc();

    List<Book> findTop20ByOrderByIdDesc();
}






