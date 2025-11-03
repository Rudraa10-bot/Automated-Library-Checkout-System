# Library Management System

A complete library management system built with React frontend and Spring Boot backend.

## Project Structure

```
library-system/
├── frontend/          # React frontend application
├── backend/           # Spring Boot REST API
├── database/          # MySQL database scripts
└── README.md         # This file
```

## Features

- **User Authentication**: JWT-based secure login
- **Book Management**: Issue and return books with barcode scanning
- **Transaction Tracking**: Complete history of all book transactions
- **Responsive Design**: Modern UI with Bootstrap
- **RESTful API**: Clean backend architecture

## Quick Start

### 1. Database Setup
```bash
cd database
mysql -u root -p library_system < setup.sql
mysql -u root -p library_system < add_my_books.sql
```

### 2. Backend Setup
```bash
cd backend
# Update application.properties with your database credentials
mvn spring-boot:run
```

### 3. Frontend Setup
```bash
cd frontend
npm install
npm start
```

## Deployment Guide

### Frontend (Vercel)
1. Push `frontend/` folder to GitHub repository
2. Connect to Vercel
3. Set environment variable: `REACT_APP_API_BASE=https://your-backend-url.com/api`
4. Deploy

### Backend (Railway/Render)
1. Push `backend/` folder to GitHub repository
2. Connect to Railway/Render
3. Set environment variables:
   - `DATABASE_URL`: Your MySQL connection string
   - `JWT_SECRET`: Secure JWT secret
   - `CORS_ALLOWED_ORIGINS`: Your frontend URL
4. Deploy

### Database (PlanetScale/Railway)
1. Create MySQL database
2. Run database scripts from `database/` folder
3. Get connection string for backend


## Environment Variables

### Frontend (.env)
```env
REACT_APP_API_BASE=http://localhost:8080/api
```

### Backend (application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/library_system
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your-jwt-secret
cors.allowed.origins=http://localhost:3000
```

## API Endpoints

- `POST /api/auth/login` - User authentication
- `POST /api/books/issue` - Issue a book
- `POST /api/books/return` - Return a book
- `GET /api/transactions/history` - Transaction history

## Technology Stack

- **Frontend**: React, Bootstrap, Axios
- **Backend**: Spring Boot, Spring Security, JWT
- **Database**: MySQL
- **Deployment**: Vercel (Frontend), Railway/Render (Backend)

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## License

This project is licensed under the MIT License.