# Enterprise OAuth2 JWT Security System

## Project Overview

This project is developed using Spring Boot and Spring Security.

Features:

* JWT Authentication
* Refresh Token
* Logout Token Revocation
* Role-Based Access Control
* Permission-Based Access Control
* Swagger API Documentation
* User-Role Mapping
* Role-Permission Mapping

---

# Technologies Used

* Java 17
* Spring Boot
* Spring Security
* JWT
* MySQL
* Maven
* Swagger OpenAPI

---

# Database Tables

* users
* roles
* permissions
* user_roles
* role_permissions
* refresh_tokens
* revoked_tokens

---

# APIs

## Authentication APIs

* POST /auth/login
* POST /auth/refresh
* POST /auth/logout

## User APIs

* POST /users
* GET /users
* GET /users/{id}
* PUT /users/{id}
* DELETE /users/{id}

## Role APIs

* POST /roles
* GET /roles

## Permission APIs

* POST /permissions
* GET /permissions

---

# Swagger URL

http://localhost:8080/swagger-ui.html

---

# Run Steps

1. Create MySQL database

CREATE DATABASE auth_security_db;

2. Configure application.properties

3. Run Spring Boot application

4. Open Swagger URL

---

# Security Features

* JWT Validation
* Token Revocation
* Role-Based Authorization
* Permission-Based Authorization
* Stateless Authentication

---


