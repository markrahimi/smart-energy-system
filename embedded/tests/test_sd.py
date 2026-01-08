import machine
import os

SD_CS = 33
SD_SCK = 5
SD_MOSI = 18
SD_MISO = 19

print("SD Card Test...")

try:
    sd = machine.SDCard(slot=2, sck=machine.Pin(SD_SCK), mosi=machine.Pin(SD_MOSI),
                       miso=machine.Pin(SD_MISO), cs=machine.Pin(SD_CS))
    os.mount(sd, '/sd')
    print("SD Card Mounted!")

    print("\nTesting write...")
    with open('/sd/test.txt', 'w') as f:
        f.write('Hello from ESP32!\n')
    print("Write OK!")

    print("\nTesting read...")
    with open('/sd/test.txt', 'r') as f:
        data = f.read()
        print("Read: {}".format(data))

    print("\nListing files...")
    files = os.listdir('/sd')
    for f in files:
        print("  - {}".format(f))

    print("\nSUCCESS!")
    os.umount('/sd')

except Exception as e:
    print("Error: {}".format(e))

print("\nDone!")
