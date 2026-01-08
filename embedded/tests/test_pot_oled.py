import machine
import time
import sh1107

POT_PIN = 34
OLED_SCL = 22
OLED_SDA = 23

print("Potentiometer Test...")

i2c = machine.I2C(0, scl=machine.Pin(OLED_SCL), sda=machine.Pin(OLED_SDA), freq=400000)
oled = sh1107.SH1107_I2C(128, 64, i2c)

pot = machine.ADC(machine.Pin(POT_PIN))
pot.atten(machine.ADC.ATTN_11DB)
pot.width(machine.ADC.WIDTH_12BIT)

try:
    while True:
        raw = pot.read()
        power = (raw / 4095.0) * 3000
        percent = int((raw / 4095.0) * 100)

        print("{} | {}% | {:.1f}W".format(raw, percent, power))

        oled.fill(0)
        oled.text('Potentiometer', 10, 0)
        oled.text('ADC: {}'.format(raw), 10, 20)
        oled.text('Percent: {}%'.format(percent), 10, 35)
        oled.text('Power: {:.1f}W'.format(power), 10, 50)
        oled.show()

        time.sleep(0.5)

except KeyboardInterrupt:
    oled.fill(0)
    oled.text('Stopped', 35, 28)
    oled.show()
    print("Stopped!")
