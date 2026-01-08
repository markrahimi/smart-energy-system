import machine
import time
import sh1107

LIGHT_PIN = 39
OLED_SCL = 22
OLED_SDA = 23

print("Light Sensor Test...")

i2c = machine.I2C(0, scl=machine.Pin(OLED_SCL), sda=machine.Pin(OLED_SDA), freq=400000)
oled = sh1107.SH1107_I2C(128, 64, i2c)

light = machine.ADC(machine.Pin(LIGHT_PIN))
light.atten(machine.ADC.ATTN_11DB)
light.width(machine.ADC.WIDTH_12BIT)

try:
    while True:
        raw = light.read()
        voltage = (raw / 4095.0) * 3.3
        lux = int(voltage * 500)

        print("{} | {:.2f}V | {} lux".format(raw, voltage, lux))

        oled.fill(0)
        oled.text('Light Sensor', 15, 0)
        oled.text('ADC: {}'.format(raw), 10, 20)
        oled.text('Volt: {:.2f}V'.format(voltage), 10, 35)
        oled.text('Lux: {}'.format(lux), 10, 50)
        oled.show()

        time.sleep(1)

except KeyboardInterrupt:
    oled.fill(0)
    oled.text('Stopped', 35, 28)
    oled.show()
    print("Stopped!")
