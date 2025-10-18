# How to Add Your Books to the Library Database

## Step-by-Step Guide

### Step 1: Open the SQL File
Open the file: `add_your_books.sql` in any text editor (Notepad, VS Code, etc.)

### Step 2: Customize Each Book
For each book you want to add, replace these values:

```sql
INSERT IGNORE INTO books (
    barcode,           -- ← YOUR barcode (e.g., 'CS001', 'PROG001')
    title,             -- ← YOUR book title
    author,            -- ← YOUR author name
    isbn,              -- ← YOUR ISBN number
    description,       -- ← YOUR book description
    total_copies,      -- ← YOUR total number of copies
    available_copies,  -- ← YOUR available copies (usually same as total)
    publication_year,  -- ← YOUR publication year
    publisher          -- ← YOUR publisher name
) VALUES (
    'YOUR001',                                    -- ← Change this
    'Your Book Title Here',                       -- ← Change this
    'Your Author Name Here',                      -- ← Change this
    '1234567890',                                 -- ← Change this
    'Your book description here',                 -- ← Change this
    5,                                            -- ← Change this
    5,                                            -- ← Change this
    2024,                                         -- ← Change this
    'Your Publisher Name'                         -- ← Change this
);
```

### Step 3: Examples of What to Change

**Example 1: Computer Science Book**
```sql
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) 
VALUES (
    'CS001',                                    -- Barcode
    'Introduction to Algorithms',               -- Title
    'Thomas H. Cormen',                        -- Author
    '9780262033848',                           -- ISBN
    'Comprehensive guide to algorithms',       -- Description
    3,                                          -- Total copies
    3,                                          -- Available copies
    2009,                                       -- Year
    'MIT Press'                                 -- Publisher
);
```

**Example 2: Programming Book**
```sql
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) 
VALUES (
    'PROG001',                                  -- Barcode
    'JavaScript: The Definitive Guide',         -- Title
    'David Flanagan',                          -- Author
    '9781491952023',                           -- ISBN
    'Master JavaScript programming',           -- Description
    4,                                          -- Total copies
    4,                                          -- Available copies
    2020,                                       -- Year
    'O\'Reilly Media'                           -- Publisher
);
```

### Step 4: Run the SQL Script

**Option A: Command Line**
```bash
mysql -u root -p library_system < add_your_books.sql
```

**Option B: MySQL Workbench**
1. Open MySQL Workbench
2. File → Open SQL Script
3. Select `add_your_books.sql`
4. Click Execute (⚡)

### Step 5: Verify Your Books Were Added
The script will automatically show you all books in the database after adding them.

## Quick Reference

| Field | What to Put | Example |
|-------|-------------|---------|
| barcode | Unique identifier | 'CS001', 'PROG001', 'WEB001' |
| title | Book title | 'Introduction to Algorithms' |
| author | Author name | 'Thomas H. Cormen' |
| isbn | ISBN number | '9780262033848' |
| description | Brief description | 'Comprehensive guide to algorithms' |
| total_copies | Total number of copies | 3 |
| available_copies | Available copies (usually same as total) | 3 |
| publication_year | Year published | 2009 |
| publisher | Publisher name | 'MIT Press' |

## Important Notes

1. **Barcodes must be unique** - don't use the same barcode twice
2. **Use quotes around text** - 'Title', 'Author', 'Publisher'
3. **Numbers don't need quotes** - 3, 2009, 9780262033848
4. **Available copies** should usually equal total copies when adding new books
5. **ISBN** can be any 10-13 digit number if you don't have the real one




