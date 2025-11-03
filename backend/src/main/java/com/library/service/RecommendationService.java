package com.library.service;

import com.library.entity.Book;
import com.library.entity.LibraryUser;
import com.library.repository.BookRepository;
import com.library.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BookRepository bookRepository;

    public Map<String, Object> getRecommendationsFor(LibraryUser user, int limit) {
        Map<String, Object> result = new HashMap<>();

        // Popular overall (last 30 days)
        LocalDateTime since = LocalDateTime.now().minusDays(30);
        List<Object[]> rows = transactionRepository.findTopBooksSince(since);
        List<Long> topIds = rows.stream()
                .map(r -> (Long) r[0])
                .limit(limit)
                .collect(Collectors.toList());
        List<Book> popularOverall = topIds.isEmpty() ? Collections.emptyList() : bookRepository.findByIdIn(topIds);

        // Because you borrowed (authors you read, exclude already borrowed)
        List<String> authors = transactionRepository.findDistinctAuthorsBorrowedByUser(user);
        List<Long> userBookIds = transactionRepository.findDistinctBookIdsBorrowedByUser(user);
        List<Book> becauseYouBorrowed = Collections.emptyList();
        if (!authors.isEmpty()) {
            List<Long> exclude = userBookIds.isEmpty() ? Collections.singletonList(-1L) : userBookIds; // hack to avoid empty IN
            becauseYouBorrowed = bookRepository.findByAuthorsExcluding(authors, exclude)
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toList());
        }

        result.put("popularOverall", popularOverall);
        result.put("becauseYouBorrowed", becauseYouBorrowed);
        return result;
    }

    public Map<String, Object> getDiscover(int limit) {
        Map<String, Object> result = new HashMap<>();
        LocalDateTime since = LocalDateTime.now().minusDays(14);
        List<Object[]> topRows = transactionRepository.findTopBooksSince(since);
        List<Long> trendingIds = topRows.stream().map(r -> (Long) r[0]).limit(limit).collect(Collectors.toList());
        List<Book> trending = trendingIds.isEmpty() ? Collections.emptyList() : bookRepository.findByIdIn(trendingIds);
        List<Book> newArrivals = bookRepository.findTop20ByOrderByCreatedAtDesc()
                .stream().limit(limit).collect(Collectors.toList());
        if (newArrivals.isEmpty()) {
            newArrivals = bookRepository.findTop20ByOrderByIdDesc()
                    .stream().limit(limit).collect(Collectors.toList());
        }
        result.put("trending", trending);
        result.put("newArrivals", newArrivals);
        return result;
    }
}
