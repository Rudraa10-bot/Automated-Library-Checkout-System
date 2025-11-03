package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.Book;
import com.library.entity.LibraryUser;
import com.library.entity.WishlistItem;
import com.library.repository.BookRepository;
import com.library.repository.WishlistRepository;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@CrossOrigin(origins = "*")
public class WishlistController {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    private List<Map<String, Object>> toDto(List<WishlistItem> items) {
        List<Map<String, Object>> list = new ArrayList<>();
        for (WishlistItem wi : items) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", wi.getId());
            Map<String, Object> book = new HashMap<>();
            book.put("id", wi.getBook().getId());
            book.put("title", wi.getBook().getTitle());
            book.put("author", wi.getBook().getAuthor());
            book.put("barcode", wi.getBook().getBarcode());
            book.put("isbn", wi.getBook().getIsbn());
            m.put("book", book);
            list.add(m);
        }
        return list;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> myWishlist(Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        List<WishlistItem> items = wishlistRepository.findByUserOrderByCreatedAtDesc(user);
        return ResponseEntity.ok(ApiResponse.success("Wishlist loaded", toDto(items)));
    }

    @PostMapping("/{barcode}")
    @Transactional
    public ResponseEntity<ApiResponse<String>> addToWishlist(@PathVariable String barcode, Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        Book book = bookRepository.findByBarcode(barcode).orElseThrow(() -> new RuntimeException("Book not found"));
        if (wishlistRepository.findByUserAndBook(user, book).isPresent()) {
            return ResponseEntity.ok(ApiResponse.success("Already in wishlist"));
        }
        WishlistItem item = new WishlistItem();
        item.setUser(user);
        item.setBook(book);
        wishlistRepository.save(item);
        return ResponseEntity.ok(ApiResponse.success("Added to wishlist"));
    }

    @DeleteMapping("/{barcode}")
    @Transactional
    public ResponseEntity<ApiResponse<String>> removeFromWishlist(@PathVariable String barcode, Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        Book book = bookRepository.findByBarcode(barcode).orElseThrow(() -> new RuntimeException("Book not found"));
        wishlistRepository.deleteByUserAndBook(user, book);
        return ResponseEntity.ok(ApiResponse.success("Removed from wishlist"));
    }
}
