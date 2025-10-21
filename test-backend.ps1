# Backend API Test Script
Write-Host "Testing Library Management System Backend..." -ForegroundColor Green
Write-Host "=========================================" -ForegroundColor Green

# Test 1: Check if backend is running
Write-Host "`n1. Testing backend availability..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/books/available" -Method GET
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Backend is running on port 8080" -ForegroundColor Green
        $books = $response.Content | ConvertFrom-Json
        Write-Host "✅ Found $($books.data.Count) available books" -ForegroundColor Green
    }
} catch {
    Write-Host "❌ Backend is not responding" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 2: Initialize default user
Write-Host "`n2. Testing user initialization..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/init" -Method POST
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Default user initialized successfully" -ForegroundColor Green
    }
} catch {
    Write-Host "❌ Failed to initialize default user" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Test login
Write-Host "`n3. Testing user login..." -ForegroundColor Yellow
try {
    $loginData = @{
        username = "student1"
        password = "pass123"
    } | ConvertTo-Json
    
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/auth/login" -Method POST -Body $loginData -ContentType "application/json"
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Login successful" -ForegroundColor Green
        $loginResponse = $response.Content | ConvertFrom-Json
        Write-Host "✅ JWT Token received: $($loginResponse.data.token.Substring(0,20))..." -ForegroundColor Green
    }
} catch {
    Write-Host "❌ Login failed" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
    if ($_.Exception.Response) {
        $reader = New-Object System.IO.StreamReader($_.Exception.Response.GetResponseStream())
        $responseBody = $reader.ReadToEnd()
        Write-Host "Response: $responseBody" -ForegroundColor Red
    }
}

# Test 4: Test book search
Write-Host "`n4. Testing book search..." -ForegroundColor Yellow
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/books/search?query=Java" -Method GET
    if ($response.StatusCode -eq 200) {
        Write-Host "✅ Book search working" -ForegroundColor Green
        $searchResults = $response.Content | ConvertFrom-Json
        Write-Host "✅ Found $($searchResults.data.Count) books matching 'Java'" -ForegroundColor Green
    }
} catch {
    Write-Host "❌ Book search failed" -ForegroundColor Red
    Write-Host "Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=========================================" -ForegroundColor Green
Write-Host "Backend testing completed!" -ForegroundColor Green













