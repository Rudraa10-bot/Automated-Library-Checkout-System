-- Library Management System Database Setup
-- Run this script to create the database and user

-- Create database
CREATE DATABASE IF NOT EXISTS library_system;
USE library_system;

-- Create user for the application (optional - you can use root)
-- CREATE USER 'library_user'@'localhost' IDENTIFIED BY 'library_password';
-- GRANT ALL PRIVILEGES ON library_system.* TO 'library_user'@'localhost';
-- FLUSH PRIVILEGES;

-- The tables will be created automatically by Hibernate when the application starts
-- due to the spring.jpa.hibernate.ddl-auto=update setting

-- You can also manually create tables if needed:

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    role ENUM('STUDENT', 'LIBRARIAN', 'ADMIN') NOT NULL DEFAULT 'STUDENT',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Books table
CREATE TABLE IF NOT EXISTS books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    barcode VARCHAR(50) UNIQUE NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE NOT NULL,
    description TEXT,
    total_copies INT NOT NULL,
    available_copies INT NOT NULL,
    status ENUM('AVAILABLE', 'UNAVAILABLE', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    publication_year INT,
    publisher VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Transactions table
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    type ENUM('ISSUE', 'RETURN', 'RENEWAL') NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    due_date TIMESTAMP,
    return_date TIMESTAMP,
    status ENUM('ACTIVE', 'COMPLETED', 'OVERDUE', 'CANCELLED') NOT NULL DEFAULT 'ACTIVE',
    fine_amount DECIMAL(10,2) DEFAULT 0.00,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Book requests table
CREATE TABLE IF NOT EXISTS book_requests (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    status ENUM('PENDING', 'NOTIFIED', 'FULFILLED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    request_date TIMESTAMP NOT NULL,
    notification_sent BOOLEAN DEFAULT FALSE,
    notification_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_books_barcode ON books(barcode);
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
CREATE INDEX idx_transactions_book_id ON transactions(book_id);
CREATE INDEX idx_transactions_type ON transactions(type);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transactions_due_date ON transactions(due_date);
CREATE INDEX idx_book_requests_user_id ON book_requests(user_id);
CREATE INDEX idx_book_requests_book_id ON book_requests(book_id);
CREATE INDEX idx_book_requests_status ON book_requests(status);

-- Insert default user (password is 'pass123' encoded with BCrypt)
INSERT IGNORE INTO users (username, password, email, full_name, role) 
VALUES ('student1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'student1@library.com', 'Default Student', 'STUDENT');

-- Insert sample books
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) VALUES
('BOOK001', 'Introduction to Java Programming', 'Y. Daniel Liang', '9780134672817', 'A comprehensive introduction to Java programming', 5, 5, 2018, 'Pearson'),
('BOOK002', 'Clean Code', 'Robert C. Martin', '9780132350884', 'A Handbook of Agile Software Craftsmanship', 3, 3, 2008, 'Prentice Hall'),
('BOOK003', 'Design Patterns', 'Gang of Four', '9780201633610', 'Elements of Reusable Object-Oriented Software', 2, 2, 1994, 'Addison-Wesley'),
('BOOK004', 'Spring Boot in Action', 'Craig Walls', '9781617292545', 'A comprehensive guide to Spring Boot', 4, 4, 2016, 'Manning Publications'),
('BOOK005', 'React: Up & Running', 'Stoyan Stefanov', '9781491931820', 'Building Web Applications', 3, 3, 2016, 'O\'Reilly Media');

COMMIT;











