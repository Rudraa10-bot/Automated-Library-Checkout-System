#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>
#include <SPI.h>
#include <MFRC522.h>
#include <LiquidCrystal_I2C.h>

// ============ CONFIGURATION - CHANGE THESE ============
const char* ssid = "YOUR_WIFI_NAME";           // Change to your WiFi name
const char* password = "YOUR_WIFI_PASSWORD";   // Change to your WiFi password
const char* apiUrl = "https://library-backend-kdev.onrender.com/api/rfid/verify";
// =====================================================

// Pin Definitions
#define SS_PIN 5
#define RST_PIN 4
#define GREEN_LED 25
#define RED_LED 26

// Initialize RFID reader
MFRC522 mfrc522(SS_PIN, RST_PIN);

// Initialize LCD (I2C address 0x27, 16 columns, 2 rows)
LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  Serial.begin(115200);
  
  // Initialize LEDs
  pinMode(GREEN_LED, OUTPUT);
  pinMode(RED_LED, OUTPUT);
  digitalWrite(GREEN_LED, LOW);
  digitalWrite(RED_LED, LOW);
  
  // Initialize LCD
  lcd.init();
  lcd.backlight();
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Library System");
  lcd.setCursor(0, 1);
  lcd.print("Initializing...");
  
  // Initialize SPI bus
  SPI.begin();
  
  // Initialize MFRC522
  mfrc522.PCD_Init();
  delay(1000);
  
  // Connect to WiFi
  Serial.println("\nConnecting to WiFi...");
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Connecting WiFi");
  
  WiFi.begin(ssid, password);
  
  int attempts = 0;
  while (WiFi.status() != WL_CONNECTED && attempts < 20) {
    delay(500);
    Serial.print(".");
    lcd.setCursor(attempts % 16, 1);
    lcd.print(".");
    attempts++;
  }
  
  if (WiFi.status() == WL_CONNECTED) {
    Serial.println("\nWiFi Connected!");
    Serial.print("IP Address: ");
    Serial.println(WiFi.localIP());
    
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("WiFi Connected!");
    lcd.setCursor(0, 1);
    lcd.print(WiFi.localIP());
    delay(2000);
  } else {
    Serial.println("\nWiFi Connection Failed!");
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("WiFi Failed!");
    lcd.setCursor(0, 1);
    lcd.print("Check Settings");
    digitalWrite(RED_LED, HIGH);
    while(1); // Stop here
  }
  
  // Ready to scan
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("LIBRARY ENTRY");
  lcd.setCursor(0, 1);
  lcd.print("Scan Your Card");
  
  Serial.println("System Ready!");
  Serial.println("Waiting for RFID card...");
}

void loop() {
  // Look for new cards
  if (!mfrc522.PICC_IsNewCardPresent()) {
    return;
  }
  
  // Select one of the cards
  if (!mfrc522.PICC_ReadCardSerial()) {
    return;
  }
  
  // Read RFID tag
  String rfidTag = "";
  for (byte i = 0; i < mfrc522.uid.size; i++) {
    rfidTag += String(mfrc522.uid.uidByte[i], HEX);
  }
  rfidTag.toUpperCase();
  
  Serial.println("\n====== RFID Card Detected ======");
  Serial.print("RFID Tag: ");
  Serial.println(rfidTag);
  
  // Show scanning message
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("Verifying...");
  lcd.setCursor(0, 1);
  lcd.print(rfidTag.substring(0, 16));
  
  // Verify with backend API
  verifyRFID(rfidTag);
  
  // Halt PICC
  mfrc522.PICC_HaltA();
  
  // Delay before next scan
  delay(3000);
  
  // Reset display
  lcd.clear();
  lcd.setCursor(0, 0);
  lcd.print("LIBRARY ENTRY");
  lcd.setCursor(0, 1);
  lcd.print("Scan Your Card");
  digitalWrite(GREEN_LED, LOW);
  digitalWrite(RED_LED, LOW);
}

void verifyRFID(String rfidTag) {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    
    http.begin(apiUrl);
    http.addHeader("Content-Type", "application/json");
    
    // Create JSON payload
    String jsonPayload = "{\"rfidTag\":\"" + rfidTag + "\"}";
    Serial.println("Sending: " + jsonPayload);
    
    // Send POST request
    int httpResponseCode = http.POST(jsonPayload);
    
    if (httpResponseCode > 0) {
      String response = http.getString();
      Serial.println("Response Code: " + String(httpResponseCode));
      Serial.println("Response: " + response);
      
      // Parse JSON response
      DynamicJsonDocument doc(1024);
      deserializeJson(doc, response);
      
      bool success = doc["success"];
      if (success && doc["data"]["access"]) {
        // Access Granted
        String username = doc["data"]["username"] | "User";
        String fullName = doc["data"]["fullName"] | "Unknown";
        
        Serial.println("✓ ACCESS GRANTED");
        Serial.println("User: " + fullName);
        
        lcd.clear();
        lcd.setCursor(0, 0);
        lcd.print("WELCOME!");
        lcd.setCursor(0, 1);
        lcd.print(fullName.substring(0, 16));
        
        // Green LED on
        digitalWrite(GREEN_LED, HIGH);
        digitalWrite(RED_LED, LOW);
        
        // Beep pattern (optional - if you add buzzer later)
        tone(13, 1000, 200);
        delay(200);
        tone(13, 1500, 200);
        
      } else {
        // Access Denied
        Serial.println("✗ ACCESS DENIED");
        
        lcd.clear();
        lcd.setCursor(0, 0);
        lcd.print("ACCESS DENIED!");
        lcd.setCursor(0, 1);
        lcd.print("Unknown Card");
        
        // Red LED on
        digitalWrite(GREEN_LED, LOW);
        digitalWrite(RED_LED, HIGH);
        
        // Error beep (optional)
        tone(13, 500, 500);
      }
    } else {
      Serial.println("Error: HTTP " + String(httpResponseCode));
      
      lcd.clear();
      lcd.setCursor(0, 0);
      lcd.print("Connection");
      lcd.setCursor(0, 1);
      lcd.print("Error!");
      
      digitalWrite(RED_LED, HIGH);
    }
    
    http.end();
  } else {
    Serial.println("WiFi Disconnected");
    
    lcd.clear();
    lcd.setCursor(0, 0);
    lcd.print("WiFi Error!");
    lcd.setCursor(0, 1);
    lcd.print("Reconnecting...");
    
    WiFi.reconnect();
  }
}
