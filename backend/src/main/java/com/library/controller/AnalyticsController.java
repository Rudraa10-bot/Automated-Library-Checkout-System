package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.Book;
import com.library.repository.BookRepository;
import com.library.repository.TransactionRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/analytics")
@CrossOrigin(origins = "*")
public class AnalyticsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('LIBRARIAN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> overview() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalUsers", userRepository.count());
        data.put("totalBooks", bookRepository.count());

        LocalDateTime todayStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        long issuesToday = transactionRepository.findTopBooksSince(todayStart).stream().mapToLong(r -> (Long) r[1]).sum();
        data.put("issuesToday", issuesToday);

        // Top 5 books overall
        List<Object[]> top = transactionRepository.findTopBooksOverall();
        List<Long> ids = top.stream().map(r -> (Long) r[0]).limit(5).collect(Collectors.toList());
        List<Book> books = ids.isEmpty() ? Collections.emptyList() : bookRepository.findByIdIn(ids);
        data.put("topBooks", books);

        return ResponseEntity.ok(ApiResponse.success("Analytics", data));
    }
}
