package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.Book;
import com.library.entity.LibraryUser;
import com.library.entity.Review;
import com.library.repository.BookRepository;
import com.library.repository.ReviewRepository;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getReviews(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        List<Review> reviews = reviewRepository.findByBookOrderByCreatedAtDesc(book);
        Double avg = reviewRepository.findAverageRatingForBook(book);
        Map<String, Object> data = new HashMap<>();
        data.put("reviews", reviews);
        data.put("averageRating", avg == null ? 0.0 : avg);
        return ResponseEntity.ok(ApiResponse.success("Reviews loaded", data));
    }

    @PostMapping("/book/{bookId}")
    public ResponseEntity<ApiResponse<String>> addReview(
            @PathVariable Long bookId,
            @RequestParam @Min(1) @Max(5) Integer rating,
            @RequestParam(required = false) String comment,
            Authentication authentication
    ) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        if (reviewRepository.existsByUserAndBook(user, book)) {
            return ResponseEntity.badRequest().body(ApiResponse.error("You already reviewed this book"));
        }
        Review r = new Review();
        r.setUser(user);
        r.setBook(book);
        r.setRating(rating);
        r.setComment(comment);
        reviewRepository.save(r);
        return ResponseEntity.ok(ApiResponse.success("Review added"));
    }
}
