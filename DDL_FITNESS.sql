-- Members Table
CREATE TABLE members (
    member_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    fitness_goals JSONB,
    health_metrics JSONB
);

-- Dashboard Table
CREATE TABLE dashboard (
    dashboard_id SERIAL PRIMARY KEY,
    member_id INTEGER REFERENCES members(member_id), 
    exercise_routines TEXT[],
    fitness_achievements TEXT[]
);

-- Trainers Table
CREATE TABLE trainers (
    trainer_id SERIAL PRIMARY KEY,
    available_times TIMESTAMPTZ[]
);

-- Administrative Staff Table
CREATE TABLE administrative_staff (
    staff_id SERIAL PRIMARY KEY
);

-- Rooms Table
CREATE TABLE rooms (
    room_id SERIAL PRIMARY KEY,
    booking_schedule TIMESTAMPTZ[] 
);

-- Fitness Classes Table
CREATE TABLE fitness_classes (
    class_id SERIAL PRIMARY KEY,
    schedule TIMESTAMPTZ,
    room_id INTEGER REFERENCES rooms(room_id),
    member_id INTEGER REFERENCES members(member_id),
    trainer_id INTEGER REFERENCES trainers(trainer_id)
);

-- Training Sessions Table
CREATE TABLE training_sessions (
    session_id SERIAL PRIMARY KEY,
    member_id INTEGER REFERENCES members(member_id),
    trainer_id INTEGER REFERENCES trainers(trainer_id),
    date_time TIMESTAMPTZ
);

-- Equipment Table
CREATE TABLE equipment (
    equipment_id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    status VARCHAR(255),
    maintenance_schedule DATE
);


-- Billing Table
CREATE TABLE billing (
    bill_id SERIAL PRIMARY KEY,
    member_id INTEGER REFERENCES members(member_id),
    amount DECIMAL(10, 2),
    payment_status BOOLEAN
);
