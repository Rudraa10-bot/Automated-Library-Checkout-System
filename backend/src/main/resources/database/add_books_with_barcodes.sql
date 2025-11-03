-- Add more books with barcodes to the library system
-- Run this script to add additional books



-- Insert additional books with unique barcodes
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) VALUES

-- Programming Books
('PROG001', 'JavaScript: The Definitive Guide', 'David Flanagan', '9781491952023', 'Master JavaScript programming with this comprehensive guide', 4, 4, 2020, 'O\'Reilly Media'),
('PROG002', 'Python Crash Course', 'Eric Matthes', '9781593279288', 'Learn Python programming from basics to advanced topics', 6, 6, 2019, 'No Starch Press'),
('PROG003', 'Effective Java', 'Joshua Bloch', '9780134685991', 'Best practices for Java programming', 3, 3, 2018, 'Addison-Wesley'),
('PROG004', 'C++ Primer', 'Stanley Lippman', '9780321714114', 'Complete guide to C++ programming', 2, 2, 2013, 'Addison-Wesley'),
('PROG005', 'Head First Design Patterns', 'Eric Freeman', '9780596007126', 'Learn design patterns in an engaging way', 4, 4, 2004, 'O\'Reilly Media'),

-- Computer Science Books
('CS001', 'Introduction to Algorithms', 'Thomas H. Cormen', '9780262033848', 'Comprehensive guide to algorithms and data structures', 3, 3, 2009, 'MIT Press'),
('CS002', 'Computer Networks', 'Andrew Tanenbaum', '9780132126953', 'Modern approach to computer networking', 2, 2, 2011, 'Prentice Hall'),
('CS003', 'Operating System Concepts', 'Abraham Silberschatz', '9781118063330', 'Fundamentals of operating systems', 4, 4, 2018, 'Wiley'),
('CS004', 'Database System Concepts', 'Abraham Silberschatz', '9780078022159', 'Complete guide to database systems', 3, 3, 2019, 'McGraw-Hill'),
('CS005', 'Artificial Intelligence', 'Stuart Russell', '9780136042594', 'Modern approach to artificial intelligence', 2, 2, 2020, 'Prentice Hall'),

-- Web Development Books
('WEB001', 'HTML and CSS: Design and Build Websites', 'Jon Duckett', '9781118008188', 'Beautiful guide to web design', 5, 5, 2011, 'Wiley'),
('WEB002', 'Learning React', 'Alex Banks', '9781492051725', 'Modern web development with React', 4, 4, 2020, 'O\'Reilly Media'),
('WEB003', 'Node.js in Action', 'Mike Cantelon', '9781617290572', 'Server-side JavaScript with Node.js', 3, 3, 2017, 'Manning Publications'),
('WEB004', 'Vue.js 2 Cookbook', 'Andrea Passaglia', '9781786468093', 'Practical recipes for Vue.js development', 2, 2, 2017, 'Packt Publishing'),
('WEB005', 'Angular: Up & Running', 'Shyam Seshadri', '9781491999881', 'Building web applications with Angular', 3, 3, 2018, 'O\'Reilly Media'),

-- Data Science Books
('DATA001', 'Python for Data Analysis', 'Wes McKinney', '9781491957660', 'Data manipulation and analysis with pandas', 4, 4, 2017, 'O\'Reilly Media'),
('DATA002', 'Hands-On Machine Learning', 'Aurélien Géron', '9781492032649', 'Practical machine learning with Scikit-Learn', 3, 3, 2019, 'O\'Reilly Media'),
('DATA003', 'The Art of Data Science', 'Roger Peng', '9781491921537', 'Guide to data analysis and visualization', 2, 2, 2016, 'O\'Reilly Media'),
('DATA004', 'R for Data Science', 'Hadley Wickham', '9781491910399', 'Data science with R programming', 3, 3, 2017, 'O\'Reilly Media'),
('DATA005', 'Deep Learning', 'Ian Goodfellow', '9780262035613', 'Comprehensive guide to deep learning', 2, 2, 2016, 'MIT Press'),

-- Software Engineering Books
('SE001', 'The Pragmatic Programmer', 'David Thomas', '9780135957059', 'Your journey to mastery', 4, 4, 2019, 'Addison-Wesley'),
('SE002', 'Clean Architecture', 'Robert C. Martin', '9780134494272', 'A craftsman\'s guide to software structure', 3, 3, 2017, 'Prentice Hall'),
('SE003', 'Refactoring', 'Martin Fowler', '9780134757599', 'Improving the design of existing code', 2, 2, 2018, 'Addison-Wesley'),
('SE004', 'Continuous Delivery', 'Jez Humble', '9780321601919', 'Reliable software releases through build, test, and deployment automation', 3, 3, 2010, 'Addison-Wesley'),
('SE005', 'Domain-Driven Design', 'Eric Evans', '9780321125217', 'Tackling complexity in the heart of software', 2, 2, 2003, 'Addison-Wesley');

-- Verify the barcodes are unique
SELECT barcode, COUNT(*) as count 
FROM books 
GROUP BY barcode 
HAVING COUNT(*) > 1;

-- Show all books with their barcodes
SELECT barcode, title, author, available_copies 
FROM books 
ORDER BY barcode;

-- New Arrivals (5 copies each)
INSERT IGNORE INTO books (barcode, title, author, isbn, description, total_copies, available_copies, publication_year, publisher) VALUES
('NEWWEB001', 'Web Performance in Action', 'Jeremy L. Wagner', '9781617293771', 'Fast websites and performance patterns', 5, 5, 2021, 'Manning Publications'),
('NEWWEB002', 'CSS Secrets', 'Lea Verou', '9781449372637', 'Better solutions to everyday web design problems', 5, 5, 2015, 'O\'Reilly Media'),
('NEWWEB003', 'You Don\'t Know JS Yet: Scope & Closures', 'Kyle Simpson', '9781098124045', 'Deep-dive into JS fundamentals', 5, 5, 2020, 'O\'Reilly Media'),
('NEWWEB004', 'HTTP/2 in Action', 'Barry Pollard', '9781617295164', 'Modern web protocols for speed', 5, 5, 2019, 'Manning Publications'),
('NEWWEB005', 'Progressive Web Apps', 'Tal Ater', '9781491961650', 'Building PWAs for reliable, fast experiences', 5, 5, 2017, 'O\'Reilly Media');

COMMIT;






