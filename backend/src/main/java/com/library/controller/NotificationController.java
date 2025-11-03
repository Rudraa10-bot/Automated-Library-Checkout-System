package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.LibraryUser;
import com.library.entity.Notification;
import com.library.repository.NotificationRepository;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> myNotifications(Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        List<Notification> list = notificationRepository.findByUserOrderByCreatedAtDesc(user);
        long unread = notificationRepository.countByUserAndReadFalse(user);
        Map<String, Object> data = new HashMap<>();
        data.put("items", list);
        data.put("unread", unread);
        return ResponseEntity.ok(ApiResponse.success("Notifications loaded", data));
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<String>> markRead(@PathVariable Long id, Authentication authentication) {
        LibraryUser user = userService.findByUsername(authentication.getName()).orElseThrow();
        Notification n = notificationRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
        if (!n.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(403).body(ApiResponse.error("Forbidden"));
        }
        n.setRead(true);
        notificationRepository.save(n);
        return ResponseEntity.ok(ApiResponse.success("Marked as read"));
    }
}
