import network
import urequests as requests
import time
import ujson

WIFI_SSID = "Mahdiyeh"
WIFI_PASSWORD = "yhbb06481"
API_URL = "http://192.168.1.100:8080/api"
DEVICE_ID = "650e8400-e29b-41d4-a716-446655440001"

print("API Test...")

wlan = network.WLAN(network.STA_IF)
wlan.active(True)

print("Connecting WiFi...")
wlan.connect(WIFI_SSID, WIFI_PASSWORD)

timeout = 15
while not wlan.isconnected() and timeout > 0:
    print(".", end="")
    time.sleep(1)
    timeout -= 1

print()

if not wlan.isconnected():
    print("WiFi Failed!")
else:
    print("WiFi OK - IP: {}".format(wlan.ifconfig()[0]))
    
    print("\nTesting API...")
    
    try:
        data = {
            "deviceId": DEVICE_ID,
            "temperature": 22.5,
            "humidity": 55.0,
            "luminosity": 150.0,
            "powerConsumption": 100.0,
            "voltage": 220.0,
            "current": 0.45
        }
        
        url = API_URL + "/sensor-data"
        headers = {'Content-Type': 'application/json'}
        
        print("Sending...")
        response = requests.post(url, data=ujson.dumps(data), headers=headers)
        
        print("Status: {}".format(response.status_code))
        
        if response.status_code == 200 or response.status_code == 201:
            print("SUCCESS!")
        else:
            print("Error: {}".format(response.text))
        
    except Exception as e:
        print("Error: {}".format(e))

print("\nDone!")
