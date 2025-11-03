package com.library.repository;

import com.library.entity.WishlistItem;
import com.library.entity.Book;
import com.library.entity.LibraryUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByUserOrderByCreatedAtDesc(LibraryUser user);
    Optional<WishlistItem> findByUserAndBook(LibraryUser user, Book book);
    void deleteByUserAndBook(LibraryUser user, Book book);
}
