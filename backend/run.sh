#!/bin/bash

# Library Management System Backend Startup Script

echo "Starting Library Management System Backend..."

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "Maven is not installed. Please install Maven 3.6 or higher."
    exit 1
fi

# Check if MySQL is running
if ! pgrep -x "mysqld" > /dev/null; then
    echo "MySQL is not running. Please start MySQL service."
    exit 1
fi

echo "Building the project..."
mvn clean install -DskipTests

if [ $? -eq 0 ]; then
    echo "Build successful. Starting the application..."
    mvn spring-boot:run
else
    echo "Build failed. Please check the errors above."
    exit 1
fi










