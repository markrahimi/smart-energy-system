import machine
import time
import sh1107

OLED_SCL = 22
OLED_SDA = 23

print("OLED Test...")

i2c = machine.I2C(0, scl=machine.Pin(OLED_SCL), sda=machine.Pin(OLED_SDA), freq=400000)
oled = sh1107.SH1107_I2C(128, 64, i2c)

oled.fill(0)
oled.text('OLED Test', 25, 10)
oled.text('Working!', 30, 30)
oled.show()
time.sleep(2)

oled.fill(0)
oled.text('Complete!', 30, 28)
oled.show()

print("Done!")
