INSERT INTO users (id, username, email, full_name, password, role, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'mahdiyeh', 'mahdieh.anjomshoae76@gmail.com', 'Mahdiyeh ANJOMSHOAE', 'pass123', 'ADMIN', CURRENT_TIMESTAMP);

INSERT INTO devices (id, name, type, location, status, active, user_id, created_at) VALUES ('650e8400-e29b-41d4-a716-446655440001', 'ESP32-Device-001', 'IOT_SENSOR', 'Living Room', 'ACTIVE', true, '550e8400-e29b-41d4-a716-446655440001', CURRENT_TIMESTAMP);

INSERT INTO thresholds (id, device_id, user_id, min_temperature, max_temperature, min_humidity, max_humidity, min_luminosity, max_luminosity, min_power, max_power, min_voltage, max_voltage, min_current, max_current, is_active, created_at) VALUES ('a50e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001', '550e8400-e29b-41d4-a716-446655440001', 15.0, 30.0, 30.0, 70.0, 200.0, 1000.0, 50.0, 300.0, 200.0, 250.0, 0.5, 2.5, true, CURRENT_TIMESTAMP);

INSERT INTO sensor_data (id, temperature, humidity, luminosity, power_consumption, voltage, current, device_id, timestamp) VALUES ('950e8400-e29b-41d4-a716-446655440001', 22.5, 45.0, 650.0, 120.0, 220.0, 0.55, '650e8400-e29b-41d4-a716-446655440001', CURRENT_TIMESTAMP);
