# Library Management System - Complete Setup Guide

This guide will help you set up the complete Library Management System with React frontend and Spring Boot backend.

## System Overview

The Library Management System consists of:
- **Frontend**: React application with modern UI and animations
- **Backend**: Spring Boot REST API with MySQL database
- **Features**: User authentication, book issuing/returning, availability checking, notifications

## Prerequisites

### Required Software
1. **Node.js** (v16 or higher) - [Download](https://nodejs.org/)
2. **Java** (v17 or higher) - [Download](https://adoptium.net/)
3. **Maven** (v3.6 or higher) - [Download](https://maven.apache.org/download.cgi)
4. **MySQL** (v8.0 or higher) - [Download](https://dev.mysql.com/downloads/mysql/)
5. **Git** - [Download](https://git-scm.com/)

### Development Tools (Optional)
- **IntelliJ IDEA** or **Eclipse** for Java development
- **VS Code** for React development
- **MySQL Workbench** for database management

## Setup Instructions

### Step 1: Database Setup

1. **Install and Start MySQL**
   ```bash
   # Windows (if installed as service)
   net start mysql
   
   # macOS (using Homebrew)
   brew services start mysql
   
   # Linux
   sudo systemctl start mysql
   ```

2. **Create Database and User**
   ```sql
   -- Connect to MySQL as root
   mysql -u root -p
   
   -- Run the setup script
   source backend/database/setup.sql
   ```

3. **Verify Database Creation**
   ```sql
   USE library_system;
   SHOW TABLES;
   ```

### Step 2: Backend Setup

1. **Navigate to Backend Directory**
   ```bash
   cd backend
   ```

2. **Update Database Configuration**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=root
   spring.datasource.password=your_mysql_password
   ```

3. **Build and Run Backend**
   ```bash
   # Using Maven
   mvn clean install
   mvn spring-boot:run
   
   # Or use the provided script
   # Windows
   run.bat
   
   # Linux/macOS
   ./run.sh
   ```

4. **Verify Backend is Running**
   - Open browser and go to: `http://localhost:8080`
   - You should see a Whitelabel Error Page (this is normal for Spring Boot)

### Step 3: Frontend Setup

1. **Navigate to Frontend Directory**
   ```bash
   cd ..  # Go back to project root
   ```

2. **Install Dependencies**
   ```bash
   npm install
   ```

3. **Start Frontend Development Server**
   ```bash
   npm start
   ```

4. **Verify Frontend is Running**
   - Open browser and go to: `http://localhost:3000`
   - You should see the Library Portal login page

### Step 4: Initialize Default Data

1. **Initialize Default User**
   ```bash
   # Make a POST request to initialize default user
   curl -X POST http://localhost:8080/api/auth/init
   ```

2. **Login with Default Credentials**
   - Username: `student1`
   - Password: `pass123`

## Testing the System

### 1. Login Test
- Go to `http://localhost:3000`
- Use credentials: `student1` / `pass123`
- You should be redirected to the dashboard

### 2. Book Operations Test
- **Issue a Book**: Use barcode `BOOK001` to issue "Introduction to Java Programming"
- **Return a Book**: Use the same barcode to return the book
- **Check History**: View transaction history in the dashboard

### 3. Book Availability Test
- **Request Unavailable Book**: Try to issue a book that's already issued
- **Request Book**: Use the request feature for unavailable books
- **Check Notifications**: The system will notify when books become available

## API Testing with Postman

### Authentication
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
    "username": "student1",
    "password": "pass123"
}
```

### Issue Book
```http
POST http://localhost:8080/api/books/issue
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "barcode": "BOOK001"
}
```

### Return Book
```http
POST http://localhost:8080/api/books/return
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
    "barcode": "BOOK001"
}
```

## Project Structure

```
library-system/
├── src/                          # React Frontend
│   ├── components/              # React components
│   ├── pages/                   # Page components
│   ├── App.js                   # Main app component
│   └── index.css                # Global styles
├── backend/                     # Spring Boot Backend
│   ├── src/main/java/com/library/
│   │   ├── config/             # Configuration classes
│   │   ├── controller/         # REST controllers
│   │   ├── dto/               # Data Transfer Objects
│   │   ├── entity/            # JPA entities
│   │   ├── repository/        # Data repositories
│   │   ├── security/          # Security configuration
│   │   └── service/           # Business logic services
│   ├── database/              # Database setup scripts
│   └── pom.xml               # Maven configuration
├── package.json              # Node.js dependencies
└── README.md                # Project documentation
```

## Features Implemented

### Frontend Features
- ✅ Modern, animated UI with glassmorphism design
- ✅ Responsive design for all screen sizes
- ✅ Login page with form validation
- ✅ Dashboard with statistics and action cards
- ✅ Book issuing and returning functionality
- ✅ Transaction history display
- ✅ Loading animations and success notifications
- ✅ Floating particles and dynamic backgrounds

### Backend Features
- ✅ JWT-based authentication
- ✅ User management with roles
- ✅ Book CRUD operations
- ✅ Transaction management
- ✅ Book availability checking
- ✅ Notification system for book requests
- ✅ CORS configuration for frontend
- ✅ Database integration with MySQL
- ✅ RESTful API endpoints
- ✅ Input validation and error handling

## Troubleshooting

### Common Issues

1. **Port 8080 Already in Use**
   ```bash
   # Find process using port 8080
   netstat -ano | findstr :8080
   
   # Kill the process (Windows)
   taskkill /PID <PID_NUMBER> /F
   ```

2. **MySQL Connection Error**
   - Check if MySQL service is running
   - Verify database credentials
   - Ensure database exists

3. **Frontend Not Connecting to Backend**
   - Check if backend is running on port 8080
   - Verify CORS configuration
   - Check browser console for errors

4. **Build Errors**
   - Ensure Java 17+ is installed
   - Check Maven version (3.6+)
   - Clear Maven cache: `mvn clean`

### Logs and Debugging

1. **Backend Logs**
   - Check console output for Spring Boot logs
   - Logs are configured in `application.properties`

2. **Frontend Logs**
   - Check browser developer console
   - Network tab for API calls

3. **Database Logs**
   - Check MySQL error logs
   - Use MySQL Workbench for query debugging

## Production Deployment

### Backend Deployment
1. Build JAR file: `mvn clean package`
2. Deploy to server with Java 17+
3. Configure production database
4. Set environment variables for sensitive data

### Frontend Deployment
1. Build production bundle: `npm run build`
2. Deploy to web server (Apache, Nginx)
3. Configure API endpoint URLs
4. Set up HTTPS for security

## Next Steps

### Potential Enhancements
- [ ] Email notifications for book availability
- [ ] Book reservation system
- [ ] Fine calculation and payment
- [ ] Admin dashboard for librarians
- [ ] Book categories and advanced search
- [ ] Mobile app development
- [ ] Integration with external library systems
- [ ] Analytics and reporting features

## Support

If you encounter any issues:
1. Check the troubleshooting section above
2. Review the logs for error messages
3. Ensure all prerequisites are installed correctly
4. Verify database connection and configuration

## License

This project is for educational purposes. Feel free to modify and extend as needed.











