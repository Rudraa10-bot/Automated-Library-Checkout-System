-- Add your custom books with barcodes
-- Replace the values below with your book details

USE library_system;

-- Example: Add a Computer Science book with barcode CS001
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) 
VALUES ('CS001', 'Introduction to Algorithms', 'Thomas H. Cormen', '9780262033848', 'Comprehensive guide to algorithms and data structures', 3, 3, 2009, 'MIT Press');

-- Example: Add a Programming book with barcode PROG001  
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) 
VALUES ('PROG001', 'JavaScript: The Definitive Guide', 'David Flanagan', '9781491952023', 'Master JavaScript programming', 4, 4, 2020, 'O\'Reilly Media');

-- Example: Add a Web Development book with barcode WEB001
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) 
VALUES ('WEB001', 'HTML and CSS: Design and Build Websites', 'Jon Duckett', '9781118008188', 'Beautiful guide to web design', 5, 5, 2011, 'Wiley');

-- Verify the books were added
SELECT barcode, title, author, available_copies FROM books ORDER BY barcode;






