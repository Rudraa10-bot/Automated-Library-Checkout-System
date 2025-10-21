-- ADD YOUR CUSTOM BOOKS TO THE LIBRARY DATABASE
-- Replace the values in the examples below with YOUR book details



-- ==============================================
-- TEMPLATE: Copy and modify this for each book
-- ==============================================

-- Book 1: Replace these values with YOUR book details
INSERT IGNORE INTO books (
    barcode,           -- YOUR barcode (e.g., 'CS001', 'PROG001', 'WEB001')
    title,             -- YOUR book title
    author,            -- YOUR author name
    isbn,              -- YOUR ISBN number (can be any 10-13 digit number)
    description,       -- YOUR book description
    total_copies,      -- YOUR total number of copies
    available_copies,  -- YOUR available copies (usually same as total_copies)
    publication_year,  -- YOUR publication year
    publisher          -- YOUR publisher name
) VALUES (
    'YOUR001',                                    -- Change this barcode
    'Your Book Title Here',                       -- Change this title
    'Your Author Name Here',                      -- Change this author
    '1234567890',                                 -- Change this ISBN
    'Your book description here',                 -- Change this description
    5,                                            -- Change this total copies
    5,                                            -- Change this available copies
    2024,                                         -- Change this year
    'Your Publisher Name'                         -- Change this publisher
);

-- ==============================================
-- EXAMPLES: Copy and modify these examples
-- ==============================================

-- Example 1: Computer Science Book
INSERT IGNORE INTO books (
    barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher
) VALUES (
    'CS001',                                    -- Your barcode
    'Introduction to Algorithms',               -- Your title
    'Thomas H. Cormen',                        -- Your author
    '9780262033848',                           -- Your ISBN
    'Comprehensive guide to algorithms and data structures',  -- Your description
    3,                                          -- Your total copies
    3,                                          -- Your available copies
    2009,                                       -- Your publication year
    'MIT Press'                                 -- Your publisher
);

-- Example 2: Programming Book
INSERT IGNORE INTO books (
    barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher
) VALUES (
    'PROG001',                                  -- Your barcode
    'JavaScript: The Definitive Guide',         -- Your title
    'David Flanagan',                          -- Your author
    '9781491952023',                           -- Your ISBN
    'Master JavaScript programming with this comprehensive guide',  -- Your description
    4,                                          -- Your total copies
    4,                                          -- Your available copies
    2020,                                       -- Your publication year
    'O\'Reilly Media'                           -- Your publisher
);

-- Example 3: Web Development Book
INSERT IGNORE INTO books (
    barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher
) VALUES (
    'WEB001',                                   -- Your barcode
    'HTML and CSS: Design and Build Websites',  -- Your title
    'Jon Duckett',                             -- Your author
    '9781118008188',                           -- Your ISBN
    'Beautiful guide to web design and development',  -- Your description
    2,                                          -- Your total copies
    2,                                          -- Your available copies
    2011,                                       -- Your publication year
    'Wiley'                                     -- Your publisher
);

-- ==============================================
-- ADD MORE BOOKS: Copy the template above for each additional book
-- ==============================================

-- Book 4: Add your fourth book here
-- INSERT IGNORE INTO books (...) VALUES (...);

-- Book 5: Add your fifth book here
-- INSERT IGNORE INTO books (...) VALUES (...);

-- ==============================================
-- VERIFY: Check that your books were added successfully
-- ==============================================

-- Show all books with their barcodes
SELECT barcode, title, author, total_copies, available_copies 
FROM books 
ORDER BY barcode;

-- Show only the books you just added (if using the example barcodes)
SELECT barcode, title, author, total_copies, available_copies 
FROM books 
WHERE barcode IN ('CS001', 'PROG001', 'WEB001')
ORDER BY barcode;






