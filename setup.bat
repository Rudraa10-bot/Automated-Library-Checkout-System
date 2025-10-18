@echo off
echo ========================================
echo Library Management System Setup
echo ========================================
echo.

echo Step 1: Checking Java installation...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed or not in PATH
    echo Please install Java 17 or higher from: https://adoptium.net/
    pause
    exit /b 1
) else (
    echo Java is installed ✓
)

echo.
echo Step 2: Checking Node.js installation...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Node.js is not installed or not in PATH
    echo Please install Node.js from: https://nodejs.org/
    pause
    exit /b 1
) else (
    echo Node.js is installed ✓
)

echo.
echo Step 3: Installing frontend dependencies...
call npm install
if %errorlevel% neq 0 (
    echo ERROR: Failed to install frontend dependencies
    pause
    exit /b 1
) else (
    echo Frontend dependencies installed ✓
)

echo.
echo Step 4: Setting up database...
echo Please make sure MySQL is running and you have created the database.
echo Run the following command to setup the database:
echo mysql -u root -p library_system ^< backend/database/setup.sql
echo.
echo Press any key after you have set up the database...
pause

echo.
echo Step 5: Building backend...
cd backend
call mvnw.cmd clean compile
if %errorlevel% neq 0 (
    echo ERROR: Failed to build backend
    pause
    exit /b 1
) else (
    echo Backend built successfully ✓
)

echo.
echo ========================================
echo Setup Complete!
echo ========================================
echo.
echo To start the application:
echo 1. Start backend: cd backend ^&^& mvnw.cmd spring-boot:run
echo 2. Start frontend: npm start
echo.
echo Default login credentials:
echo Username: student1
echo Password: pass123
echo.
pause










