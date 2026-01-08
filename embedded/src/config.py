# wifi settings
WIFI_SSID = "Mahdiyeh"
WIFI_PASSWORD = "yhbb06481"

# backend api
API_URL = "https://smart-energy-system.onrender.com/api"
DEVICE_ID = "650e8400-e29b-41d4-a716-446655440001"  # ESP32-Device-001 from database

# sensor pins
DHT22_PIN = 21      # GPIO 21 - for DHT22 temperature/humidity sensor
TEMT6000_PIN = 39   # A3 (GPIO 39) - for TEMT6000 light sensor
POT_PIN = 34        # A2 (GPIO 34) - for potentiometer (ADC1 - works with WiFi)
LED_PIN = 13        # built-in red LED

# oled display pins
OLED_SCL = 22
OLED_SDA = 23

# timing in seconds
SENSOR_READ_INTERVAL = 2   # read sensors and refresh OLED every 2 seconds
API_SEND_INTERVAL = 20     # send to backend API every 20 seconds

# power range for simulation
MIN_POWER = 0
MAX_POWER = 3000

# voltage/current settings
NOMINAL_VOLTAGE = 230  # volts

# data source mode
USE_SD_CARD = True  # True: read from SD card CSV, False: read from sensors

# sd card pins (Adalogger FeatherWing)
SD_CS = 33
SD_SCK = 5
SD_MOSI = 18
SD_MISO = 19

# csv file path
CSV_FILE = "/sd/sensor_data.csv"
