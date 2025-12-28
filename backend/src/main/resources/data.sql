INSERT INTO users (username, email, full_name, password, role, created_at) VALUES ('mahdiyeh', 'mahdieh.anjomshoae76@gmail.com', 'Mahdiyeh ANJOMSHOAE', 'pass123', 'ADMIN', CURRENT_TIMESTAMP);
INSERT INTO users (username, email, full_name, password, role, created_at) VALUES ('mohammadali', 'markrahimi@gmail.com', 'Mohammad Ali RAHIMI KOUHBANANI', 'pass123', 'USER', CURRENT_TIMESTAMP);

INSERT INTO devices (name, type, location, status, active, user_id, created_at) VALUES ('Panel A', 'SOLAR_PANEL', 'Building Roof', 'ACTIVE', true, 1, CURRENT_TIMESTAMP);
INSERT INTO devices (name, type, location, status, active, user_id, created_at) VALUES ('Panel B', 'WIND_TURBINE', 'Open Field', 'ACTIVE', true, 2, CURRENT_TIMESTAMP);

INSERT INTO notifications (title, message, type, priority, is_read, user_id, created_at) VALUES ('Test Notification 1', 'Test Notification 1 detail', 'INFO', 'LOW', false, 1, CURRENT_TIMESTAMP);
INSERT INTO notifications (title, message, type, priority, is_read, user_id, created_at) VALUES ('Test Notification 2', 'Test Notification 2 detail', 'WARNING', 'MEDIUM', false, 2, CURRENT_TIMESTAMP);

INSERT INTO energy_readings (power, voltage, current, energy_consumed, device_id, timestamp) VALUES (245.5, 220.0, 1.12, 122.5, 1, CURRENT_TIMESTAMP);
INSERT INTO energy_readings (power, voltage, current, energy_consumed, device_id, timestamp) VALUES (498.0, 230.0, 2.16, 249.0, 2, CURRENT_TIMESTAMP);

INSERT INTO sensor_data (temperature, humidity, luminosity, distance, device_id, timestamp) VALUES (24.8, 58.5, 780.0, null, 1, CURRENT_TIMESTAMP);
INSERT INTO sensor_data (temperature, humidity, luminosity, distance, device_id, timestamp) VALUES (21.3, 54.2, null, null, 2, CURRENT_TIMESTAMP);
