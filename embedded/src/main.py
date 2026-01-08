# Smart Energy Monitor
# Mahdiyeh ANJOMSHOAE & Mohammadali RAHIMI

import machine
import network
import urequests as requests
import dht
import sh1107
import time
import ujson
import os
from config import *

csv_data = []
csv_index = 0

def init_hardware():
    dht_sensor = None
    light_sensor = None
    pot = None

    if not USE_SD_CARD:
        dht_sensor = dht.DHT22(machine.Pin(DHT22_PIN))
        light_sensor = machine.ADC(machine.Pin(TEMT6000_PIN))
        light_sensor.atten(machine.ADC.ATTN_11DB)
        light_sensor.width(machine.ADC.WIDTH_12BIT)
        pot = machine.ADC(machine.Pin(POT_PIN))
        pot.atten(machine.ADC.ATTN_11DB)
        pot.width(machine.ADC.WIDTH_12BIT)

    led = machine.Pin(LED_PIN, machine.Pin.OUT)
    i2c = machine.I2C(0, scl=machine.Pin(OLED_SCL), sda=machine.Pin(OLED_SDA), freq=400000)
    oled = sh1107.SH1107_I2C(128, 64, i2c)

    return dht_sensor, light_sensor, pot, led, oled

def init_sd_card():
    try:
        sd = machine.SDCard(slot=2, sck=machine.Pin(SD_SCK), mosi=machine.Pin(SD_MOSI),
                           miso=machine.Pin(SD_MISO), cs=machine.Pin(SD_CS))
        os.mount(sd, '/sd')
        print("SD Card OK")
        return True
    except Exception as e:
        print("SD Card Error:", e)
        return False

def load_csv_data():
    global csv_data
    try:
        with open(CSV_FILE, 'r') as f:
            lines = f.readlines()
            csv_data = []
            for i, line in enumerate(lines):
                if i == 0:
                    continue
                parts = line.strip().split(',')
                if len(parts) == 4:
                    csv_data.append({
                        'temperature': float(parts[0]),
                        'humidity': float(parts[1]),
                        'luminosity': int(parts[2]),
                        'power': float(parts[3])
                    })
        print("Loaded {} records".format(len(csv_data)))
        return True
    except Exception as e:
        print("CSV Error:", e)
        return False

def connect_wifi(oled, led):
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)

    if not wlan.isconnected():
        oled.fill(0)
        oled.text('Connecting...', 0, 0)
        oled.text(WIFI_SSID[:16], 0, 20)
        oled.show()

        wlan.connect(WIFI_SSID, WIFI_PASSWORD)

        # wait for connection
        timeout = 20
        while not wlan.isconnected() and timeout > 0:
            led.value(not led.value())
            time.sleep(0.5)
            timeout -= 0.5

        if not wlan.isconnected():
            oled.fill(0)
            oled.text('WiFi Failed!', 0, 20)
            oled.show()
            return False

    # success
    led.value(1)
    oled.fill(0)
    oled.text('Connected!', 0, 0)
    oled.text(wlan.ifconfig()[0], 0, 20)
    oled.show()
    time.sleep(2)

    return True

def read_from_csv():
    global csv_index
    if len(csv_data) == 0:
        return None

    record = csv_data[csv_index]
    csv_index = (csv_index + 1) % len(csv_data)

    data = {
        'temperature': record['temperature'],
        'humidity': record['humidity'],
        'luminosity': record['luminosity'],
        'power': record['power'],
        'voltage': NOMINAL_VOLTAGE,
        'current': record['power'] / NOMINAL_VOLTAGE if record['power'] > 0 else 0.0
    }
    return data

def read_sensors(dht_sensor, light_sensor, pot):
    if USE_SD_CARD:
        return read_from_csv()

    data = {}

    try:
        dht_sensor.measure()
        data['temperature'] = dht_sensor.temperature()
        data['humidity'] = dht_sensor.humidity()
    except Exception as e:
        print("dht error:", e)
        data['temperature'] = None
        data['humidity'] = None

    try:
        adc_value = light_sensor.read()
        voltage_adc = (adc_value / 4095.0) * 3.3
        data['luminosity'] = int(voltage_adc * 500)
    except Exception as e:
        print("Light sensor error")
        data['luminosity'] = None

    try:
        adc_value = pot.read()
        data['power'] = (adc_value / 4095.0) * (MAX_POWER - MIN_POWER) + MIN_POWER
        data['voltage'] = NOMINAL_VOLTAGE
        data['current'] = data['power'] / NOMINAL_VOLTAGE if data['power'] > 0 else 0.0
    except:
        data['power'] = None
        data['voltage'] = None
        data['current'] = None

    return data

def display_data(oled, data):
    print("T: {:.1f}C | H: {:.1f}% | L: {} lux | P: {:.1f}W".format(
        data.get('temperature') or 0,
        data.get('humidity') or 0,
        data.get('luminosity') or 0,
        data.get('power') or 0
    ))

    oled.fill(0)
    oled.text('Energy Monitor', 0, 0)

    if data['temperature'] is not None:
        oled.text('Temp: {:.1f}C'.format(data['temperature']), 0, 12)
    else:
        oled.text('Temp: ---', 0, 12)

    if data['humidity'] is not None:
        oled.text('Humid: {:.1f}%'.format(data['humidity']), 0, 24)
    else:
        oled.text('Humid: ---', 0, 24)

    if data['luminosity'] is not None:
        oled.text('Light: {} lux'.format(data['luminosity']), 0, 36)
    else:
        oled.text('Light: ---', 0, 36)

    if data['power'] is not None:
        oled.text('Power: {:.1f}W'.format(data['power']), 0, 48)
    else:
        oled.text('Power: ---', 0, 48)

    if data['current'] is not None:
        oled.text('Curr: {:.2f}A'.format(data['current']), 0, 56)
    else:
        oled.text('Curr: ---', 0, 56)

    oled.show()

def send_to_api(data):
    try:
        payload = {
            "temperature": data.get('temperature'),
            "humidity": data.get('humidity'),
            "luminosity": float(data.get('luminosity')) if data.get('luminosity') is not None else None,
            "powerConsumption": float(data.get('power')) if data.get('power') is not None else None,
            "voltage": float(data.get('voltage')) if data.get('voltage') is not None else None,
            "current": float(data.get('current')) if data.get('current') is not None else None,
            "deviceId": DEVICE_ID
        }

        url = API_URL + "/sensor-data"
        headers = {'Content-Type': 'application/json'}

        response = requests.post(url, data=ujson.dumps(payload), headers=headers)

        if response.status_code == 200 or response.status_code == 201:
            print("API OK")
            return True
        else:
            print("API Error:", response.status_code)
            return False
    except Exception as e:
        print("Send Error:", e)
        return False

def main():
    print("Starting...")

    dht_sensor, light_sensor, pot, led, oled = init_hardware()

    oled.fill(0)
    oled.text('Smart Energy', 10, 20)
    oled.text('monitor', 10, 35)
    oled.show()
    time.sleep(2)

    if USE_SD_CARD:
        oled.fill(0)
        oled.text('Init SD Card...', 5, 28)
        oled.show()

        if not init_sd_card():
            oled.fill(0)
            oled.text('SD Card Failed!', 5, 28)
            oled.show()
            return

        if not load_csv_data():
            oled.fill(0)
            oled.text('CSV Load Failed!', 0, 28)
            oled.show()
            return

        oled.fill(0)
        oled.text('SD Card OK', 20, 20)
        oled.text('{} records'.format(len(csv_data)), 20, 35)
        oled.show()
        time.sleep(2)

    if not connect_wifi(oled, led):
        print("WiFi Failed!")
        return

    print("Ready!")

    last_api_send = 0
    while True:
        try:
            data = read_sensors(dht_sensor, light_sensor, pot)
            display_data(oled, data)

            led.value(0)
            time.sleep(0.1)
            led.value(1)

            current_time = time.time()
            if current_time - last_api_send >= API_SEND_INTERVAL:
                if send_to_api(data):
                    last_api_send = current_time

            time.sleep(SENSOR_READ_INTERVAL)

        except KeyboardInterrupt:
            print("Stopped")
            break
        except Exception as e:
            print("Error:", e)
            time.sleep(5)

    led.value(0)
    oled.fill(0)
    oled.text('Stopped', 40, 28)
    oled.show()

if __name__ == "__main__":
    main()


