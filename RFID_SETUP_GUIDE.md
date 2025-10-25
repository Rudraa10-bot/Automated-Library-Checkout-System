# RFID Library Entry System - Complete Setup Guide

## 📦 Hardware You Have:
- ✅ ESP32 (30-pin)
- ✅ LCD 16x2 with I2C
- ✅ RC522 RFID Reader
- ✅ 1 Tag + 3 Cards
- ✅ Red & Green LEDs
- ✅ Jumper Wires (Male-Female)
- ✅ Breadboard

---

## 🔌 Step 1: Hardware Wiring

### **Connect Everything to Breadboard:**

#### **LCD 16x2 (I2C) → ESP32:**
```
LCD Pin    →    ESP32 Pin
------------------------
VCC        →    3.3V
GND        →    GND
SDA        →    GPIO 21
SCL        →    GPIO 22
```

#### **RC522 RFID Reader → ESP32:**
```
RC522 Pin  →    ESP32 Pin
------------------------
SDA (SS)   →    GPIO 5
SCK        →    GPIO 18
MOSI       →    GPIO 23
MISO       →    GPIO 19
IRQ        →    (Not connected)
GND        →    GND
RST        →    GPIO 4
3.3V       →    3.3V
```

#### **LEDs → ESP32:**
```
Green LED:
- Long leg (Anode)  →  GPIO 25
- Short leg (Cathode) → GND

Red LED:
- Long leg (Anode)  →  GPIO 26
- Short leg (Cathode) → GND
```

**IMPORTANT:** You don't need resistors if LEDs are low power. If they're bright LEDs, add 220Ω resistors between ESP32 pins and LED anodes.

---

## 💻 Step 2: Install Arduino IDE & Libraries

### **A. Install Arduino IDE:**
1. Download from: https://www.arduino.cc/en/software
2. Install it on your computer

### **B. Add ESP32 Board Support:**
1. Open Arduino IDE
2. Go to **File → Preferences**
3. In "Additional Board Manager URLs", paste:
   ```
   https://raw.githubusercontent.com/espressif/arduino-esp32/gh-pages/package_esp32_index.json
   ```
4. Click OK
5. Go to **Tools → Board → Boards Manager**
6. Search for "esp32"
7. Install "esp32 by Espressif Systems"
8. Select **Tools → Board → ESP32 Arduino → ESP32 Dev Module**

### **C. Install Required Libraries:**

Go to **Tools → Manage Libraries** and install these:

1. **MFRC522** by GithubCommunity (for RFID reader)
2. **LiquidCrystal I2C** by Frank de Brabander (for LCD)
3. **ArduinoJson** by Benoit Blanchon (for JSON parsing)

---

## 📝 Step 3: Configure & Upload Code

### **A. Open the Code:**
1. Open `esp32_rfid_system.ino` in Arduino IDE

### **B. Configure WiFi:**
Change these lines (around line 9-10):
```cpp
const char* ssid = "YOUR_WIFI_NAME";           // Your WiFi name
const char* password = "YOUR_WIFI_PASSWORD";   // Your WiFi password
```

### **C. Connect ESP32:**
1. Connect ESP32 to computer via USB cable
2. In Arduino IDE:
   - **Tools → Board:** ESP32 Dev Module
   - **Tools → Port:** Select the COM port (e.g., COM3, COM5)
   - **Tools → Upload Speed:** 115200

### **D. Upload:**
1. Click the **Upload** button (→ arrow icon)
2. Wait for "Done uploading" message
3. Open **Tools → Serial Monitor** (set baud rate to 115200)
4. Watch the ESP32 boot messages

---

## 🗄️ Step 4: Deploy Backend Changes

### **A. Commit & Push:**
```bash
git add .
git commit -m "Add RFID functionality to library system"
git push
```

### **B. Wait for Deployment:**
- Render will automatically redeploy (2-3 minutes)
- Check Render logs to confirm success

---

## 🎴 Step 5: Register RFID Cards in Database

### **A. Scan Your Cards First:**
1. Power on ESP32
2. Open Serial Monitor (115200 baud)
3. Tap each RFID card on the reader
4. Note down the RFID tag IDs (e.g., "A1B2C3D4")

**Example output:**
```
====== RFID Card Detected ======
RFID Tag: A1B2C3D4
```

### **B. Add Tags to Database:**

**Method 1: Via SQL (Railway Dashboard):**
```sql
-- Update existing users with RFID tags
UPDATE users SET rfid_tag = 'A1B2C3D4' WHERE username = 'student1';
UPDATE users SET rfid_tag = 'E5F6G7H8' WHERE username = 'student2';
UPDATE users SET rfid_tag = 'I9J0K1L2' WHERE username = 'librarian1';
```

**Method 2: Via API (Postman/curl):**
```bash
# Test endpoint first
curl https://library-backend-kdev.onrender.com/api/rfid/test

# You'll need to create an endpoint to update RFID tags
# Or update manually via Railway database interface
```

---

## 🧪 Step 6: Testing

### **Test Sequence:**

#### **1. RFID Entry System:**
```
✅ Tap registered card
   → LCD shows: "WELCOME! Student Name"
   → Green LED lights up
   → Serial Monitor: "✓ ACCESS GRANTED"

❌ Tap unregistered card
   → LCD shows: "ACCESS DENIED! Unknown Card"
   → Red LED lights up
   → Serial Monitor: "✗ ACCESS DENIED"
```

#### **2. Website Login & Book Issue:**
```
1. Go to: https://automated-library-checkout-system.vercel.app/
2. Login: student1 / pass123
3. Navigate to "Issue Book"
4. Enter barcode: CS001
5. Click "Issue Book"
6. ✅ Book should be issued successfully
7. Check transaction history
```

#### **3. Complete Flow:**
```
Student arrives at library
  ↓
Taps RFID card → "Welcome!"
  ↓
Goes to computer/kiosk
  ↓
Logs into website
  ↓
Issues book by typing barcode
  ↓
Book issued ✅
```

---

## 🐛 Troubleshooting

### **LCD shows nothing:**
- Check I2C address (try 0x3F if 0x27 doesn't work)
- Check wiring: SDA→21, SCL→22
- Test with I2C scanner sketch

### **WiFi not connecting:**
- Check SSID and password spelling
- Make sure WiFi is 2.4GHz (ESP32 doesn't support 5GHz)
- Try moving ESP32 closer to router

### **RFID not reading:**
- Check all 8 wire connections
- Make sure 3.3V is connected (not 5V!)
- Try different cards/tags
- Check Serial Monitor for errors

### **API not responding:**
- Check internet connection
- Verify backend is deployed on Render
- Test endpoint: `https://library-backend-kdev.onrender.com/api/rfid/test`

### **"Access Denied" for registered card:**
- Make sure RFID tag is correctly added to database
- Check exact tag format (uppercase, no spaces)
- Verify user exists in database

---

## 📊 Demo Script for Teacher

### **Phase 1: Show RFID Entry (2 minutes)**
```
"This is the library entrance system"
→ Tap Card 1: "Welcome, Student1!" (Green LED)
→ Tap Card 2: "Welcome, Student2!" (Green LED)
→ Tap Card 3: "Access Denied!" (Red LED)
→ Explain: "System verifies users from cloud database"
```

### **Phase 2: Show Website (3 minutes)**
```
"Now student enters library and uses the kiosk"
→ Login with student1 credentials
→ Show dashboard
→ Issue book CS001
→ Show transaction history
→ Return book CS001
```

### **Phase 3: Explain Architecture (2 minutes)**
```
Show diagram:
RFID System (ESP32) → WiFi → Backend API (Render)
                               ↓
                         Database (Railway)
                               ↑
Website (Vercel) ──────────────┘
```

---

## 🎯 Card Label Suggestions

Label your cards with permanent marker:
- **Card 1:** "STUDENT 1" (registered)
- **Card 2:** "STUDENT 2" (registered)
- **Card 3:** "UNREGISTERED" (not in database)
- **Tag:** "LIBRARIAN" (registered)

---

## 📝 Next Steps (Optional Improvements)

1. **Add Buzzer** (GPIO 13) - Audio feedback
2. **Add More Users** - Register more students
3. **Add Admin Panel** - Manage RFID cards via website
4. **Add Logs** - Track entry/exit times
5. **Add Display of Stats** - Show entries count on LCD

---

## 🆘 Need Help?

If you face issues:
1. Check Serial Monitor output
2. Verify all wire connections
3. Test each component individually
4. Check Render deployment logs
5. Verify database connection

**Common Issues Already Fixed:**
✅ CORS configuration
✅ Security endpoints
✅ Database schema
✅ API endpoints

---

## ✅ Checklist Before Demo

- [ ] ESP32 connects to WiFi
- [ ] LCD displays messages clearly
- [ ] RFID reader detects cards
- [ ] Green/Red LEDs work
- [ ] Backend API responds
- [ ] At least 2 cards registered in database
- [ ] 1 unregistered card for demo
- [ ] Website login works
- [ ] Book issue/return works
- [ ] All wires secured on breadboard

---

**You're all set! Good luck with your demo! 🚀**
