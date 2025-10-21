-- Quick script to add CS001 book to your database


-- Add the CS001 book
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) 
VALUES ('CS001', 'Introduction to Algorithms', 'Thomas H. Cormen', '9780262033848', 'Comprehensive guide to algorithms and data structures', 3, 3, 2009, 'MIT Press');

-- Verify the book was added
SELECT barcode, title, author, available_copies FROM books WHERE barcode = 'CS001';

-- Show all available barcodes
SELECT barcode, title FROM books ORDER BY barcode;







