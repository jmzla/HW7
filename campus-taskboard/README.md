# Campus Task Board API

## 📌 Project Description

This project is a Spring Boot REST API for managing tasks.
Homework 7 extends the previous version by adding advanced features such as exception handling, DTOs, soft delete, request logging, and health monitoring.

The API allows users to create, update, delete, and retrieve tasks while ensuring clean architecture and robust error handling.

---

## 🛠 Technologies Used

* Java 21
* Spring Boot
* Spring Data JPA
* H2 Database
* Maven
* Lombok
* Spring Validation
* Spring Boot Actuator
* Postman (for testing)

---

## 🚀 Features

### ✅ Exception Handling

* Custom exceptions:

    * `TaskNotFoundException`
    * `InvalidTaskDataException`
* Global exception handler using `@RestControllerAdvice`
* Handles:

    * 404 Not Found
    * 400 Validation errors
    * 500 Internal Server Error
* Returns structured JSON error responses

---

### ✅ DTOs (Data Transfer Objects)

* `TaskRequest` – handles incoming requests with validation
* `TaskResponse` – formats outgoing responses
* Separates API layer from database entity
* Prevents exposing internal fields like `deleted`

---

### ✅ Soft Delete

* Tasks are not permanently removed from the database
* A `deleted` field is added to the Task entity
* Deleting a task sets `deleted = true`
* Only non-deleted tasks are returned in queries
* Restore endpoint allows recovery of deleted tasks

---

### ✅ Request Logging

* Logs every API request:

    * HTTP method
    * Request URI
    * Response status
    * Execution time
* Helps with debugging and monitoring

---

### ✅ Actuator (Health Monitoring)

* Provides production-ready monitoring endpoints:

    * `/actuator/health`
    * `/actuator/info`
    * `/actuator/metrics`
* Used to check application status and performance

---

## ▶️ How to Run the Application

1. Clone the repository:

```bash
git clone https://github.com/jmzla/HW7.git
```

2. Open the project in IntelliJ IDEA or any Java IDE

3. Make sure Java 21 is installed

4. Run the application:

* Right-click `CampusTaskboardApplication.java`
* Click **Run**

5. The application will start at:

```
http://localhost:8080
```

---

## 📡 API Endpoints

### 🔹 Get All Tasks

```
GET /api/tasks
```

---

### 🔹 Get Task by ID

```
GET /api/tasks/{id}
```

---

### 🔹 Create Task

```
POST /api/tasks
```

Example request:

```json
{
  "title": "Complete Homework 7",
  "description": "Finish API",
  "priority": "HIGH"
}
```

---

### 🔹 Update Task

```
PUT /api/tasks/{id}
```

---

### 🔹 Delete Task (Soft Delete)

```
DELETE /api/tasks/{id}
```

---

### 🔹 Restore Task

```
PUT /api/tasks/{id}/restore
```

---

## 📊 Actuator Endpoints

```
/actuator/health
/actuator/info
/actuator/metrics
```

Example response:

```json
{
  "status": "UP"
}
```

---

## ⚠️ Validation Rules

* Title must not be blank
* Title must be between 3 and 100 characters
* Description must not exceed 500 characters

---

## ❌ Example Error Responses

### 404 Not Found

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Task with ID 10 not found",
  "path": "/api/tasks/10"
}
```

---

### 400 Validation Error

```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Input validation failed",
  "errors": {
    "title": "Title must be between 3 and 100 characters"
  }
}
```

---

### 500 Internal Server Error

```json
{
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/tasks"
}
```

---

## 📸 Screenshots Included

* 404 error response
* 400 validation error
* 500 internal error
* Soft delete working
* Health endpoint (`/actuator/health`)
* Request logging in console

---

## 🎥 Video Explanation

👉 Video Link:
https://www.youtube.com/watch?v=Uiub3J9hIHA

