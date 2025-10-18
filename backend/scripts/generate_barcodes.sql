-- Script to generate barcodes for books that don't have them
-- Run this script in your MySQL database

USE library_system;

-- Check current status
SELECT 
    'Books with barcodes' as status,
    COUNT(*) as count
FROM books 
WHERE barcode IS NOT NULL AND barcode != ''

UNION ALL

SELECT 
    'Books without barcodes' as status,
    COUNT(*) as count
FROM books 
WHERE barcode IS NULL OR barcode = '';

-- Show books without barcodes
SELECT id, title, author, barcode 
FROM books 
WHERE barcode IS NULL OR barcode = ''
ORDER BY id;

-- Generate barcodes for books without them
-- Note: This is a simple approach. For production, use the Java service above.

-- Example: Update books without barcodes with sequential numbers
-- Uncomment and modify as needed:

/*
UPDATE books 
SET barcode = CONCAT('BOOK', LPAD(id, 6, '0'))
WHERE barcode IS NULL OR barcode = '';
*/

-- Verify uniqueness
SELECT barcode, COUNT(*) as count 
FROM books 
GROUP BY barcode 
HAVING COUNT(*) > 1;

-- Show all books with their barcodes
SELECT id, barcode, title, author, available_copies 
FROM books 
ORDER BY barcode;




