import network
import urequests as requests
import ujson
import time
import machine

WIFI_SSID = "Mahdiyeh"
WIFI_PASSWORD = "yhbb06481"
API_URL = "http://0.0.0.100:8080/api"
DEVICE_ID = "650e8400-e29b-41d4-a716-446655440001"

print("=" * 40)
print("API Test - Complete")
print("=" * 40)

print("\n1. Connecting to WiFi...")
wlan = network.WLAN(network.STA_IF)
wlan.active(True)

if not wlan.isconnected():
    print("Connecting to: {}".format(WIFI_SSID))
    wlan.connect(WIFI_SSID, WIFI_PASSWORD)

    timeout = 20
    while not wlan.isconnected() and timeout > 0:
        print(".", end="")
        time.sleep(0.5)
        timeout -= 0.5

    print()

if wlan.isconnected():
    print("WiFi Connected!")
    print("IP: {}".format(wlan.ifconfig()[0]))
else:
    print("WiFi Failed!")
    exit()

print("\n2. Preparing test data...")
test_data = {
    "temperature": 23.5,
    "humidity": 55.0,
    "luminosity": 150.0,
    "powerConsumption": 500.0,
    "voltage": 230.0,
    "current": 2.17,
    "deviceId": DEVICE_ID
}

print("Data to send:")
print(ujson.dumps(test_data, indent=2))

print("\n3. Sending to API...")
url = API_URL + "/sensor-data"
print("URL: {}".format(url))

headers = {'Content-Type': 'application/json'}

try:
    response = requests.post(url, data=ujson.dumps(test_data), headers=headers)

    print("\nResponse:")
    print("Status Code: {}".format(response.status_code))
    print("Response Text: {}".format(response.text))

    if response.status_code == 200 or response.status_code == 201:
        print("\nSUCCESS! API accepted the data")
    else:
        print("\nERROR! API rejected the data")

    response.close()

except Exception as e:
    print("\nERROR sending data:")
    print(str(e))

print("\n" + "=" * 40)
print("Test Complete!")
print("=" * 40)
