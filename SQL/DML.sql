INSERT INTO members (member_id, name, email, password, fitness_goals, health_metrics)
VALUES
(1, 'Barney Stinson', 'barneystinson@himym.com', 'pword000', 
'{"muscle_gain": {"target": "5 kg", "duration": "6 months"}}',
'{"heart_rate": 70, "weight": 82.5, "blood_pressure": "120/80"}'),
(3, 'Robin Scherbatsky', 'robin@himym.com', 'pword001', 
'{"muscle_gain": {"target": "3 kg", "duration": "4 months"}}',
'{"heart_rate": 72, "weight": 62.0, "blood_pressure": "118/79"}'),

(4, 'Ted Mosby', 'ted@himym.com', 'pword002', 
'{"weight_loss": {"target": "4 kg", "duration": "5 months"}}',
'{"heart_rate": 68, "weight": 76.5, "blood_pressure": "121/80"}'),

(5, 'Lily Aldrin', 'lily@himym.com', 'pword003', 
'{"muscle_gain": {"target": "2 kg", "duration": "3 months"}}',
'{"heart_rate": 65, "weight": 58.0, "blood_pressure": "115/75"}'),

(6, 'Marshall Eriksen', 'marshall@himym.com', 'pword004', 
'{"weight_loss": {"target": "5 kg", "duration": "6 months"}}',
'{"heart_rate": 70, "weight": 90.0, "blood_pressure": "122/81"}');


INSERT INTO dashboard (member_id, exercise_routines, fitness_achievements)
VALUES
(1, '{"Bench press", "Deadlift"}', '{"Bench pressed 225lbs", "Lifted 100 kg deadlift"}'),
(3, '{"Bench press", "Squats"}', '{"Ran 5K under 20 minutes", "Squatted 150 kg"}'),
(4, '{"Treadmill running", "Cycling"}', '{"Ran 10 km", "Cycled 50 km in 3 hours"}'),
(5, '{"Yoga", "Pilates"}', '{"Mastered advanced yoga", "Completed Pilates level 2"}'),
(6, '{"Rowing", "Elliptical trainer"}', '{"Rowed 2000 meters in 8 minutes", "Completed 30 minutes on elliptical"}');

INSERT INTO trainers (trainer_id, available_times)
VALUES
(1, ARRAY[
    '2024-10-01 10:00:00+00'::TIMESTAMPTZ, 
    '2024-10-01 12:00:00+00'::TIMESTAMPTZ, 
    '2024-10-02 12:00:00+00'::TIMESTAMPTZ, 
    '2024-10-03 12:00:00+00'::TIMESTAMPTZ
]),
(2, ARRAY[
    '2024-10-04 10:00:00+00'::TIMESTAMPTZ, 
    '2024-10-04 14:00:00+00'::TIMESTAMPTZ, 
    '2024-10-05 09:00:00+00'::TIMESTAMPTZ, 
    '2024-10-06 13:00:00+00'::TIMESTAMPTZ
]),
(3, ARRAY[
    '2024-10-07 11:00:00+00'::TIMESTAMPTZ, 
    '2024-10-07 15:00:00+00'::TIMESTAMPTZ, 
    '2024-10-08 10:00:00+00'::TIMESTAMPTZ, 
    '2024-10-09 14:00:00+00'::TIMESTAMPTZ
]),
(4, ARRAY[
    '2024-10-10 08:00:00+00'::TIMESTAMPTZ, 
    '2024-10-11 12:00:00+00'::TIMESTAMPTZ, 
    '2024-10-12 11:00:00+00'::TIMESTAMPTZ, 
    '2024-10-13 15:00:00+00'::TIMESTAMPTZ
]),
(5, ARRAY[
    '2024-10-14 09:00:00+00'::TIMESTAMPTZ, 
    '2024-10-15 13:00:00+00'::TIMESTAMPTZ, 
    '2024-10-16 10:00:00+00'::TIMESTAMPTZ, 
    '2024-10-17 16:00:00+00'::TIMESTAMPTZ
]);

INSERT INTO administrative_staff (staff_id)
VALUES
(DEFAULT),
(DEFAULT),
(DEFAULT),
(DEFAULT),
(DEFAULT);


INSERT INTO rooms (room_id, booking_schedule)
VALUES
(1, ARRAY['2024-10-01 09:00:00+00'::TIMESTAMPTZ, '2024-10-01 19:00:00+00'::TIMESTAMPTZ]),
(2, ARRAY['2024-10-02 10:00:00+00'::TIMESTAMPTZ, '2024-10-02 20:00:00+00'::TIMESTAMPTZ]),
(3, ARRAY['2024-10-03 11:00:00+00'::TIMESTAMPTZ, '2024-10-03 21:00:00+00'::TIMESTAMPTZ]),
(4, ARRAY['2024-10-04 12:00:00+00'::TIMESTAMPTZ, '2024-10-04 22:00:00+00'::TIMESTAMPTZ]),
(5, ARRAY['2024-10-05 13:00:00+00'::TIMESTAMPTZ, '2024-10-05 23:00:00+00'::TIMESTAMPTZ]);

INSERT INTO fitness_classes (schedule, room_id, member_id, trainer_id)
VALUES
('2024-10-01 09:00:00+00', 1, 1, 1),
('2024-10-01 09:00:00+00', 1, 1, 1),
('2024-10-02 09:00:00+00', 2, 2, 1),
('2024-10-03 09:00:00+00', 3, 3, 2),
('2024-10-04 09:00:00+00', 4, 4, 3);

INSERT INTO training_sessions (member_id, trainer_id, date_time)
VALUES
(1, 1, '2024-10-01 10:00:00+00'),
(1, 1, '2024-10-01 10:00:00+00'),
(2, 1, '2024-10-02 10:00:00+00'),
(3, 2, '2024-10-03 10:00:00+00'),
(4, 3, '2024-10-04 10:00:00+00');

INSERT INTO equipment (name, status, maintenance_schedule)
VALUES
('Cable machines', 'Operational', '2023-12-01'),
('Barbells ', 'Operational', '2023-12-01'),
('Treadmill', 'Operational', '2023-12-15'),
('Elliptical', 'Needs repair', '2023-11-20'),
('Smith machines', 'Operational', '2024-01-10');

INSERT INTO billing (member_id, amount, payment_status)
VALUES
(1, 99.99, TRUE),
(2, 99.99, TRUE),
(2, 49.99, FALSE),
(3, 29.99, TRUE),
(4, 199.99, FALSE);




