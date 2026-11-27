# Hostel Management System

A console-based Java app for managing hostel rooms and students — add rooms, register students, allocate rooms, and track who lives where.

**Status: Week 3 project, in progress (Day 1 of 7 done).**

## Features (so far)
- Add a room with a room number and bed capacity
- Add a student with a name, roll number, and course
- Allocate a room to a student (only if the room exists and has a free bed)
- List all rooms with capacity, occupied beds, and free beds
- List all students with their allocated room (or "Not allocated")
- Duplicate room numbers and duplicate roll numbers are rejected

## Build plan for the week
| Day | Work |
| --- | --- |
| 1 | Project setup, Student and Room models, add / allocate / list ✅ |
| 2 | View allocations, search student or room, vacancy check |
| 3 | Save and load students and rooms from CSV files |
| 4 | Occupancy and fees report |
| 5 | Input validation and a cleaner menu |
| 6 | Edit, vacate, transfer and delete records |
| 7 | Final polish, sample data, complete README |

## Concepts used
Classes and objects, ArrayList, loops, switch, Scanner input, and basic validation (blank checks, duplicate checks, NumberFormatException handling).

## How to run
```bash
javac Student.java Room.java HostelManagement.java
java HostelManagement
```

## Example session
```text
===== Hostel Management System =====
Rooms: 0  |  Students: 0
1. Add a room
2. Add a student
3. Allocate a room to a student
4. List all rooms
5. List all students
6. Exit
Choose an option: 1
Enter room number: A-101
Enter room capacity (number of beds): 3
Room A-101 added with capacity 3.
```
