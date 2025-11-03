package com.library.service;

import com.library.entity.LibraryUser;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LibraryUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user;
    }
    
    public Optional<LibraryUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public Optional<LibraryUser> findById(Long id) {
        return userRepository.findById(id);
    }
    
    public LibraryUser save(LibraryUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Save an existing user without changing password (for updates like points)
    public LibraryUser saveExisting(LibraryUser user) {
        return userRepository.save(user);
    }

    public void addPoints(LibraryUser user, int delta) {
        if (user == null) return;
        Integer points = getPoints(user);
        setPoints(user, points + delta);
        saveExisting(user);
    }

    private Integer getPoints(LibraryUser user) {
        try {
            var field = LibraryUser.class.getDeclaredField("points");
            field.setAccessible(true);
            Object val = field.get(user);
            return val == null ? 0 : (Integer) val;
        } catch (Exception e) {
            return 0;
        }
    }

    private void setPoints(LibraryUser user, int value) {
        try {
            var field = LibraryUser.class.getDeclaredField("points");
            field.setAccessible(true);
            field.set(user, value);
        } catch (Exception ignored) {}
    }
    
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    
    public LibraryUser createDefaultUser() {
        if (!existsByUsername("student1")) {
            LibraryUser defaultUser = new LibraryUser();
            defaultUser.setUsername("student1");
            defaultUser.setPassword("pass123");
            defaultUser.setEmail("student1@library.com");
            defaultUser.setFullName("Default Student");
            defaultUser.setRole(LibraryUser.Role.STUDENT);
            return save(defaultUser);
        }
        return userRepository.findByUsername("student1").orElse(null);
    }
}













