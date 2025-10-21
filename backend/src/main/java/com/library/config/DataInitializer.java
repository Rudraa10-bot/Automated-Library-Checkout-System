package com.library.config;

import com.library.service.UserService;

import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DataInitializer implements CommandLineRunner {

    private final DataSource dataSource;
    private final UserService userService;

    public DataInitializer(DataSource dataSource, UserService userService) {
        this.dataSource = dataSource;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUser();
        seedBooksFromSQL();
    }

    private void initializeDefaultUser() {
    try {
        User defaultUser = (User) userService.createDefaultUser();
        System.out.println("Default user initialized successfully!");
        System.out.println("Username: " + defaultUser.getUsername());
        System.out.println("Password: pass123"); // Only for testing
    } catch (Exception e) {
        System.out.println("Error initializing default user: " + e.getMessage());
    }
}


    private void seedBooksFromSQL() {
        try (Connection conn = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(conn, new ClassPathResource("database/add_books_with_barcodes.sql"));
            System.out.println("Books seeded successfully from SQL file!");
        } catch (Exception e) {
            System.out.println("Error seeding books: " + e.getMessage());
        }
    }
}

