package com.library.config;

import com.library.entity.Book;
import com.library.entity.User;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import com.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private BookRepository bookRepository;
    
    @Override
    public void run(String... args) throws Exception {
        initializeDefaultUser();
        initializeSampleBooks();
    }
    
    private void initializeDefaultUser() {
        try {
            userService.createDefaultUser();
            System.out.println("Default user initialized successfully");
        } catch (Exception e) {
            System.out.println("Error initializing default user: " + e.getMessage());
        }
    }
    
    private void initializeSampleBooks() {
        if (bookRepository.count() == 0) {
            // Sample books
            Book book1 = new Book();
            book1.setBarcode("BOOK001");
            book1.setTitle("Introduction to Java Programming");
            book1.setAuthor("Y. Daniel Liang");
            book1.setIsbn("9780134672817");
            book1.setDescription("A comprehensive introduction to Java programming");
            book1.setTotalCopies(5);
            book1.setAvailableCopies(5);
            book1.setPublicationYear(2018);
            book1.setPublisher("Pearson");
            bookRepository.save(book1);
            
            Book book2 = new Book();
            book2.setBarcode("BOOK002");
            book2.setTitle("Clean Code");
            book2.setAuthor("Robert C. Martin");
            book2.setIsbn("9780132350884");
            book2.setDescription("A Handbook of Agile Software Craftsmanship");
            book2.setTotalCopies(3);
            book2.setAvailableCopies(3);
            book2.setPublicationYear(2008);
            book2.setPublisher("Prentice Hall");
            bookRepository.save(book2);
            
            Book book3 = new Book();
            book3.setBarcode("BOOK003");
            book3.setTitle("Design Patterns");
            book3.setAuthor("Gang of Four");
            book3.setIsbn("9780201633610");
            book3.setDescription("Elements of Reusable Object-Oriented Software");
            book3.setTotalCopies(2);
            book3.setAvailableCopies(2);
            book3.setPublicationYear(1994);
            book3.setPublisher("Addison-Wesley");
            bookRepository.save(book3);
            
            Book book4 = new Book();
            book4.setBarcode("BOOK004");
            book4.setTitle("Spring Boot in Action");
            book4.setAuthor("Craig Walls");
            book4.setIsbn("9781617292545");
            book4.setDescription("A comprehensive guide to Spring Boot");
            book4.setTotalCopies(4);
            book4.setAvailableCopies(4);
            book4.setPublicationYear(2016);
            book4.setPublisher("Manning Publications");
            bookRepository.save(book4);
            
            Book book5 = new Book();
            book5.setBarcode("BOOK005");
            book5.setTitle("React: Up & Running");
            book5.setAuthor("Stoyan Stefanov");
            book5.setIsbn("9781491931820");
            book5.setDescription("Building Web Applications");
            book5.setTotalCopies(3);
            book5.setAvailableCopies(3);
            book5.setPublicationYear(2016);
            book5.setPublisher("O'Reilly Media");
            bookRepository.save(book5);
            
            System.out.println("Sample books initialized successfully");
        }
    }
}











