# Rehana Groups Hotel

A hotel booking web application built with Spring Boot, MySQL, Thymeleaf, Spring Security, and JavaScript.

## Overview

Rehana Groups Hotel is a full-stack hotel booking system that allows users to browse available rooms, search for rooms based on booking dates and guest capacity, create and manage bookings, and manage their profiles.

The system also provides an admin dashboard for managing rooms and bookings.

## Features

### User Features

- User registration and login
- Secure password encryption using BCrypt
- Role-based authorization
- Browse hotel rooms
- View room details
- Search for available rooms
- Book rooms
- View personal bookings
- Cancel bookings
- View and edit user profile

### Admin Features

- Admin dashboard
- View room statistics
- Add new rooms
- Edit room information
- Activate or deactivate rooms
- View all bookings
- Confirm bookings
- Cancel bookings
- Complete bookings

### Booking System

The booking system includes:

- Check-in and check-out date validation
- Guest capacity validation
- Room availability checking
- Overlapping booking prevention
- Automatic total price calculation
- Booking status management

Booking statuses:

- `PENDING`
- `CONFIRMED`
- `CANCELLED`
- `COMPLETED`

## Technologies

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Spring Security
- Thymeleaf
- Thymeleaf Security Extras
- MySQL
- JavaScript
- Tailwind CSS
- Maven
- Lombok

## Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com/RehanaGroups/web/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repo/
│   │       ├── entity/
│   │       ├── DTOs/
│   │       ├── security/
│   │       └── exception/
│   │
│   └── resources/
│       ├── static/
│       │   ├── css/
│       │   └── script.js
│       │
│       └── templates/
│
├── test/
│
├── pom.xml
├── package.json
└── package-lock.json
