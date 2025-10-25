package com.library.controller;

import com.library.dto.ApiResponse;
import com.library.entity.LibraryUser;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/rfid")
@CrossOrigin(origins = "*")
public class RfidController {
    
    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Map<String, Object>>> verifyRfidTag(@RequestBody Map<String, String> request) {
        String rfidTag = request.get("rfidTag");
        
        if (rfidTag == null || rfidTag.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("RFID tag is required"));
        }
        
        Optional<LibraryUser> userOptional = userRepository.findByRfidTag(rfidTag);
        
        Map<String, Object> response = new HashMap<>();
        
        if (userOptional.isPresent()) {
            LibraryUser user = userOptional.get();
            response.put("access", true);
            response.put("username", user.getUsername());
            response.put("fullName", user.getFullName());
            response.put("role", user.getRole().name());
            response.put("message", "Access Granted");
            
            return ResponseEntity.ok(ApiResponse.success("User verified successfully", response));
        } else {
            response.put("access", false);
            response.put("message", "Access Denied");
            
            return ResponseEntity.ok(ApiResponse.success("RFID tag not registered", response));
        }
    }
    
    @GetMapping("/test")
    public ResponseEntity<String> testEndpoint() {
        return ResponseEntity.ok("RFID Controller is working!");
    }
}
