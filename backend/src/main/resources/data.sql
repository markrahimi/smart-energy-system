INSERT INTO users (id, username, email, full_name, password, role, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440001', 'mahdiyeh', 'mahdieh.anjomshoae76@gmail.com', 'Mahdiyeh ANJOMSHOAE', 'pass123', 'ADMIN', CURRENT_TIMESTAMP);
INSERT INTO users (id, username, email, full_name, password, role, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440002', 'mohammadali', 'markrahimi@gmail.com', 'Mohammad Ali RAHIMI KOUHBANANI', 'pass123', 'USER', CURRENT_TIMESTAMP);
INSERT INTO users (id, username, email, full_name, password, role, created_at) VALUES ('550e8400-e29b-41d4-a716-446655440003', 'mark', 'imarkrahimi@gmail.com', 'Mohammadali RAHIMI', 'mark', 'USER', CURRENT_TIMESTAMP);

INSERT INTO devices (id, name, type, location, status, active, user_id, created_at) VALUES ('650e8400-e29b-41d4-a716-446655440001', 'Panel A', 'SOLAR_PANEL', 'Building Roof', 'ACTIVE', true, '550e8400-e29b-41d4-a716-446655440001', CURRENT_TIMESTAMP);
INSERT INTO devices (id, name, type, location, status, active, user_id, created_at) VALUES ('650e8400-e29b-41d4-a716-446655440002', 'Panel B', 'WIND_TURBINE', 'Open Field', 'ACTIVE', true, '550e8400-e29b-41d4-a716-446655440002', CURRENT_TIMESTAMP);

INSERT INTO notifications (id, title, message, type, priority, is_read, user_id, created_at) VALUES ('750e8400-e29b-41d4-a716-446655440001', 'Test Notification 1', 'Test Notification 1 detail', 'INFO', 'LOW', false, '550e8400-e29b-41d4-a716-446655440001', CURRENT_TIMESTAMP);
INSERT INTO notifications (id, title, message, type, priority, is_read, user_id, created_at) VALUES ('750e8400-e29b-41d4-a716-446655440002', 'Test Notification 2', 'Test Notification 2 detail', 'WARNING', 'MEDIUM', false, '550e8400-e29b-41d4-a716-446655440002', CURRENT_TIMESTAMP);

INSERT INTO energy_readings (id, power, voltage, current, energy_consumed, device_id, timestamp) VALUES ('850e8400-e29b-41d4-a716-446655440001', 245.5, 220.0, 1.12, 122.5, '650e8400-e29b-41d4-a716-446655440001', CURRENT_TIMESTAMP);
INSERT INTO energy_readings (id, power, voltage, current, energy_consumed, device_id, timestamp) VALUES ('850e8400-e29b-41d4-a716-446655440002', 498.0, 230.0, 2.16, 249.0, '650e8400-e29b-41d4-a716-446655440002', CURRENT_TIMESTAMP);

INSERT INTO sensor_data (id, temperature, humidity, luminosity, distance, device_id, timestamp) VALUES ('950e8400-e29b-41d4-a716-446655440001', 24.8, 58.5, 780.0, null, '650e8400-e29b-41d4-a716-446655440001', CURRENT_TIMESTAMP);
INSERT INTO sensor_data (id, temperature, humidity, luminosity, distance, device_id, timestamp) VALUES ('950e8400-e29b-41d4-a716-446655440002', 21.3, 54.2, null, null, '650e8400-e29b-41d4-a716-446655440002', CURRENT_TIMESTAMP);
