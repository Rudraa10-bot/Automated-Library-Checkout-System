package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.LibraryUser;
import com.library.service.RecommendationService;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private UserService userService;

    @GetMapping("/recommendations/me")
    public ResponseEntity<ApiResponse<Map<String, Object>>> myRecommendations(Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Map<String, Object> data = recommendationService.getRecommendationsFor(user, 10);
        return ResponseEntity.ok(ApiResponse.success("Recommendations ready", data));
    }

    @GetMapping("/discover")
    public ResponseEntity<ApiResponse<Map<String, Object>>> discover() {
        Map<String, Object> data = recommendationService.getDiscover(12);
        return ResponseEntity.ok(ApiResponse.success("Discover data ready", data));
    }
}
