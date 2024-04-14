# BetterLife-Fitness

## Overview
This is a Health and Fitness Club Management System is designed to manage and facilitate the operations of a fitness club. This system handles everything from member registrations, scheduling personal training sessions and fitness classes, to managing room bookings and maintaining equipment.

Video Demonstration
- Part One: ER Model, Schema Mapping and Exploring DDL and DML https://youtu.be/hoIx-6PqL1c

- Part Two: Functionality Walk-through and Code Overview https://youtu.be/mQvc15fZ2b0

## Features
- **Member Management**: Allows new and existing members to manage their profiles, set fitness goals, and track their health metrics.
- **Trainer Management**: Enables trainers to manage their schedules and view details about their assigned members.
- **Class Management**: Admin staff can schedule, update, and cancel group fitness classes, linking members, trainers, and available rooms.
- **Equipment Management**: Includes tracking of equipment status and scheduling maintenance.
- **Billing System**: Manages billing processes, including membership fees and payments for personal training.

## Technology Stack
- **Backend**: PostgreSQL for database management
- **Frontend**: Java Application (CLI)

## Getting Started

### Prerequisites
- PostgreSQL 12 or above
- Java JDK 11 or above
- JDBC 4.2 for database connectivity

### Installation
1. **Database Setup**
   - Install PostgreSQL and create a database named `BetterLifeFitness`.
   - Execute the SQL scripts provided in DDL to create the necessary tables and relationships.
2. **Java Application Setup**
   - Clone the repository: `git clone https://github.com/zhuan0352/BetterLife-Fitness.git`
   - Navigate to the application directory and compile the Java files: `javac *.java`
   - Run the main application: `java Main`

### Configuration
- Ensure that the database URL, username, and password in the Java application are set to match your PostgreSQL setup.

## Usage
- Start the application as described above.
- Follow the on-screen prompts to interact with the system:
  - **For Members**: Enter 'M' to access member functionalities.
  - **For Trainers**: Enter 'T' to manage schedules and member profiles.
  - **For Administrative Staff**: Enter 'A' to manage rooms, equipment, and classes.

## Contact
Zhuoran Liu - zhuoranliu3@cmail.carleton.ca 
Project Link: https://github.com/zhuan0352/BetterLife-Fitness.git

## Acknowledgements
- [PostgreSQL](https://www.postgresql.org/)
- [Carleton University](#)
