@echo off
echo Starting Library Management System Backend...

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java is not installed. Please install Java 17 or higher.
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven is not installed. Please install Maven 3.6 or higher.
    pause
    exit /b 1
)

echo Building the project...
mvn clean install -DskipTests

if %errorlevel% equ 0 (
    echo Build successful. Starting the application...
    mvn spring-boot:run
) else (
    echo Build failed. Please check the errors above.
    pause
    exit /b 1
)











