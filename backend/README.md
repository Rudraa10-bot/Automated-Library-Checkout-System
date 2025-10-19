# Library System Backend

Spring Boot REST API for the Library Management System.

## Features
- JWT-based authentication
- Book management (issue/return)
- Transaction tracking
- User management
- CORS enabled for frontend integration

## Local Development

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

### Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE library_system;
```

2. Run the database scripts from the `database/` folder:
```bash
# Run setup.sql first
mysql -u root -p library_system < ../database/setup.sql

# Then add sample data
mysql -u root -p library_system < ../database/add_my_books.sql
```

### Configuration
Update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/library_system?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT Configuration
jwt.secret=your-super-secure-jwt-secret-key-at-least-256-bits
jwt.expiration=86400000

# CORS Configuration
cors.allowed.origins=http://localhost:3000
```

### Running the Application
```bash
# Using Maven
mvn spring-boot:run

# Or using the provided scripts
./run.sh  # Linux/Mac
run.bat   # Windows
```

The API will be available at http://localhost:8080

## Deployment

### Railway Deployment
1. Install Railway CLI: `npm install -g @railway/cli`
2. Login: `railway login`
3. Initialize: `railway init`
4. Set environment variables:
   - `DATABASE_URL`: Your MySQL connection string
   - `JWT_SECRET`: Your JWT secret
   - `CORS_ALLOWED_ORIGINS`: Your frontend URL
5. Deploy: `railway up`

### Render Deployment
1. Connect your GitHub repository to Render
2. Set environment variables in Render dashboard
3. Deploy

### Environment Variables for Production
```env
DATABASE_URL=mysql://username:password@host:port/database_name
JWT_SECRET=your-super-secure-jwt-secret-key
JWT_EXPIRATION=86400000
CORS_ALLOWED_ORIGINS=https://your-frontend-domain.vercel.app
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=production
```

## API Endpoints

### Authentication
- `POST /api/auth/login` - User login

### Books
- `POST /api/books/issue` - Issue a book
- `POST /api/books/return` - Return a book

### Transactions
- `GET /api/transactions/history` - Get transaction history

## Project Structure
```
backend/
├── src/main/java/com/library/
│   ├── config/      # Configuration classes
│   ├── controller/  # REST controllers
│   ├── dto/         # Data transfer objects
│   ├── entity/      # JPA entities
│   ├── repository/  # Data repositories
│   ├── security/    # Security configuration
│   └── service/     # Business logic
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```