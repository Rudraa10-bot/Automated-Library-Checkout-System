# Library Management System - Backend

A Spring Boot REST API for managing library operations including book issuing, returning, and user management.

## Features

- **User Authentication**: JWT-based authentication with Spring Security
- **Book Management**: CRUD operations for books with barcode support
- **Transaction Management**: Issue and return books with transaction history
- **Book Availability**: Real-time book availability checking
- **Notification System**: Automatic notifications when requested books become available
- **CORS Support**: Configured for React frontend integration
- **Database Integration**: MySQL database with JPA/Hibernate

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## Setup Instructions

### 1. Database Setup

1. Install MySQL and start the service
2. Create a database user (optional):
   ```sql
   CREATE USER 'library_user'@'localhost' IDENTIFIED BY 'library_password';
   GRANT ALL PRIVILEGES ON library_system.* TO 'library_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. Run the database setup script:
   ```bash
   mysql -u root -p < database/setup.sql
   ```

### 2. Configuration

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. Build and Run

```bash
# Navigate to backend directory
cd backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login
- `POST /api/auth/register` - User registration
- `POST /api/auth/init` - Initialize default user

### Books
- `GET /api/books` - Get all books
- `GET /api/books/available` - Get available books
- `GET /api/books/search?query={term}` - Search books
- `GET /api/books/barcode/{barcode}` - Get book by barcode
- `GET /api/books/check-availability/{barcode}` - Check book availability
- `POST /api/books/issue` - Issue a book (requires authentication)
- `POST /api/books/return` - Return a book (requires authentication)
- `POST /api/books/request` - Request a book (requires authentication)

### Transactions
- `GET /api/transactions/history` - Get user transaction history
- `GET /api/transactions/active` - Get user's active issues
- `GET /api/transactions/count` - Get count of active issues
- `GET /api/transactions/all` - Get all transactions (admin)
- `GET /api/transactions/overdue` - Get overdue transactions

## Default Credentials

- **Username**: student1
- **Password**: pass123

## Sample Books

The system comes with 5 sample books:
- BOOK001: Introduction to Java Programming
- BOOK002: Clean Code
- BOOK003: Design Patterns
- BOOK004: Spring Boot in Action
- BOOK005: React: Up & Running

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security with JWT
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Java Version**: 17

## Project Structure

```
backend/
├── src/main/java/com/library/
│   ├── config/          # Configuration classes
│   ├── controller/      # REST controllers
│   ├── dto/            # Data Transfer Objects
│   ├── entity/         # JPA entities
│   ├── repository/     # Data repositories
│   ├── security/       # Security configuration
│   └── service/        # Business logic services
├── src/main/resources/
│   └── application.properties
├── database/
│   └── setup.sql
└── pom.xml
```

## Features in Detail

### Book Availability System
- Real-time availability checking
- Automatic book status updates when issued/returned
- Queue system for unavailable books

### Notification System
- Automatic notifications when requested books become available
- Scheduled job runs every 5 minutes to check availability
- Request tracking and status management

### Security Features
- JWT token-based authentication
- Password encryption with BCrypt
- Role-based access control
- CORS configuration for frontend integration

### Transaction Management
- Complete transaction history
- Due date tracking
- Overdue book detection
- Fine calculation (extensible)

## Development Notes

- The application uses Hibernate's `ddl-auto=update` for automatic table creation
- JWT tokens expire after 24 hours (configurable)
- All API responses follow a consistent format with success/error status
- CORS is configured to allow all origins (configure for production)

## Troubleshooting

### Common Issues

1. **Database Connection Error**
   - Check MySQL service is running
   - Verify database credentials in application.properties
   - Ensure database exists

2. **Port Already in Use**
   - Change server.port in application.properties
   - Or stop the process using port 8080

3. **JWT Token Issues**
   - Check JWT secret in application.properties
   - Ensure token is sent in Authorization header as "Bearer {token}"

## Next Steps

- Implement email notifications
- Add book reservation system
- Implement fine calculation and payment
- Add admin dashboard
- Implement book categories and search filters
- Add book rating and review system










