# 🌱 Volunet – Skill-Based Volunteering Platform

**Volunet** is a professional-grade backend application built with **Spring Boot** that connects **Non-Profit Organizations (NPOs)** with **volunteers** based on specific professional skills.
It features a robust project lifecycle, automated volunteer workload management, and dynamic impact reporting.

---

## 🚀 Key Features

* **Organization Management**
  NPO registration with strict **case-sensitive unique constraints** on name and email.

* **Project Lifecycle State Machine**
  Projects move through controlled states:
  `DRAFT → RECRUITING → IN_PROGRESS → COMPLETED`

* **Smart Volunteer Assignment**
  Prevents volunteer burnout by limiting active assignments to a maximum of **3 `IN_PROGRESS` projects** per volunteer.

* **Dynamic Impact Metrics**
  Real-time calculation of:

  * Total completed projects
  * Volunteer engagement
  * Organization impact metrics

* **Advanced Filtering & Pagination**
  Native SQL-based search for projects filtered by **SkillType** and **ProjectStatus** with optimized pagination.

* **Global Exception Handling**
  Centralized handling for:

  * Duplicate resources
  * Missing data
  * Business rule violations

---

## 🛠️ Technical Stack

| Layer     | Technology                  |
| --------- | --------------------------- |
| Framework | Spring Boot 3.5.7           |
| Language  | Java 17                     |
| Database  | MySQL                       |
| ORM       | Spring Data JPA             |
| API Docs  | SpringDoc OpenAPI (Swagger) |
| Utilities | Lombok, Jakarta Validation  |

---

## 🏛️ Architecture & Relationships

The system is designed around a **three-entity core** with clearly defined JPA relationships:

* **Organization**

  * One-to-Many relationship with Projects

* **Project**

  * Many-to-One with Organization
  * Many-to-Many with Volunteers via `project_volunteers`

* **Volunteer**

  * Many-to-Many with Projects through a join table

---

## 🔌 API Endpoints

### 🏢 Organizations

* `POST /api/organization`
  Register a new NPO

* `GET /api/organization`
  List all registered organizations

* `GET /api/organization/{id}/impact`
  Retrieve a detailed impact report and skill breakdown

---

### 📌 Projects

* `POST /api/project`
  Create a new volunteer project

* `GET /api/project/filter`
  Paginated search by skill and status

* `POST /api/project/{pId}/assign/{vId}`
  Assign a volunteer to a project

* `PATCH /api/project/{id}/status`
  Advance a project to its next lifecycle stage

---

### 🙋 Volunteers

* `POST /api/volunteer`
  Register as a volunteer

* `PATCH /api/volunteer/{id}/status`
  Manually toggle availability status to `BUSY`

---

## ⚙️ Configuration

### 📦 Database Setup

Create a MySQL schema named:

```sql
CREATE DATABASE volunet;
```

---

### 📝 Application Properties

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/volunet
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=update
```

---

## ▶️ Running the Application

Use the Maven wrapper to start the application:

```bash
./mvnw spring-boot:run
```

---

## 📄 API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:8092/swagger-ui.html
```

---

## 🧠 Business Rules Summary

* Only verified organizations can recruit volunteers
* Project state transitions must be **linear**
* Volunteers cannot exceed **3 active projects**
* All validation and errors are handled globally

