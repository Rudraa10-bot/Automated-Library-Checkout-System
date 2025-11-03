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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<ApiResponse<List<WishlistItem>>> myWishlist(Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        List<WishlistItem> items = wishlistRepository.findByUserOrderByCreatedAtDesc(user);
        return ResponseEntity.ok(ApiResponse.success("Wishlist loaded", items));
    }

    @PostMapping("/{barcode}")
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
    public ResponseEntity<ApiResponse<String>> removeFromWishlist(@PathVariable String barcode, Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        Book book = bookRepository.findByBarcode(barcode).orElseThrow(() -> new RuntimeException("Book not found"));
        wishlistRepository.deleteByUserAndBook(user, book);
        return ResponseEntity.ok(ApiResponse.success("Removed from wishlist"));
    }
}
