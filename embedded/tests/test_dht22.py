import machine
import dht
import time

PINS_TO_TEST = [21, 27, 14, 12, 4]

print("DHT22 Test - Multiple Pins")

for pin in PINS_TO_TEST:
    print("\nTesting GPIO {}...".format(pin))
    
    try:
        sensor = dht.DHT22(machine.Pin(pin))
        time.sleep(2)
        
        sensor.measure()
        temp = sensor.temperature()
        hum = sensor.humidity()
        
        print("  SUCCESS! {:.1f}C  {:.1f}%".format(temp, hum))
        print("  >>> Use GPIO {} <<<".format(pin))
        break
        
    except:
        print("  Failed")

print("\nDone!")
