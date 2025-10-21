# Library System Database

MySQL database schema and scripts for the Library Management System.

## Database Schema

### Tables
- **users**: Library users (students, staff, etc.)
- **books**: Book catalog with barcodes
- **transactions**: Book issue/return records
- **book_requests**: Book request system

## Setup Instructions

### 1. Create Database
```sql
CREATE DATABASE library_system;
USE library_system;
```

### 2. Run Setup Script
```bash
mysql -u root -p library_system < setup.sql
```

### 3. Add Sample Data
```bash
# Add sample books
mysql -u root -p library_system < add_my_books.sql

# Add additional books (optional)
mysql -u root -p library_system < add_cs001_book.sql
mysql -u root -p library_system < add_your_books.sql
```

### 4. Generate Barcodes (Optional)
```bash
mysql -u root -p library_system < scripts/generate_barcodes.sql
```

## Database Files

- `setup.sql` - Main database schema and initial data
- `add_my_books.sql` - Sample book data
- `add_cs001_book.sql` - Additional CS books
- `add_your_books.sql` - Additional books
- `scripts/generate_barcodes.sql` - Barcode generation script

## Production Database Options

### 1. PlanetScale (Recommended)
- MySQL-compatible
- Serverless scaling
- Free tier available
- Easy connection string format

### 2. Railway
- MySQL hosting
- Simple setup
- Good for small to medium projects

### 3. Supabase
- PostgreSQL (requires schema migration)
- Built-in authentication
- Real-time features

### 4. Neon
- PostgreSQL serverless
- Good performance
- Free tier available

## Connection String Format

### MySQL
```
mysql://username:password@host:port/database_name
```

### Example for PlanetScale
```
mysql://username:password@aws.connect.psdb.cloud/library_system?sslaccept=strict
```

## Environment Variables

For production deployment, set these environment variables:

```env
DATABASE_URL=mysql://username:password@host:port/database_name
DB_HOST=your-mysql-host
DB_PORT=3306
DB_NAME=library_system
DB_USERNAME=your-username
DB_PASSWORD=your-password
```

## Migration Notes

If migrating from MySQL to PostgreSQL:
1. Update data types (AUTO_INCREMENT → SERIAL)
2. Update syntax differences
3. Test all queries thoroughly

## Backup and Restore

### Backup
```bash
mysqldump -u username -p library_system > backup.sql
```

### Restore
```bash
mysql -u username -p library_system < backup.sql
```
