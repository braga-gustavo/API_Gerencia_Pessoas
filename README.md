# People Management API

## 📌 Overview
This project is a **RESTful API** developed as part of my **Bachelor's Degree in Information Systems**. It provides an efficient way to manage people records, allowing users to **create, update, delete, and retrieve** information stored in a structured database.

## 🚀 Features
- **User Registration:** Add new people with structured data.
- **Data Update & Deletion:** Modify or remove registered records.
- **User Search:** Retrieve detailed information using unique identifiers.
- **RESTful API:** Designed following best practices for scalability and maintainability.
- **Secure Authentication:** JWT-based authentication to protect user data.

## 🛠️ Technologies Used
- **Java** (Backend development)
- **Spring Boot** (Framework for building the API)
- **Maven** (Dependency management)
- **MySQL/PostgreSQL** (Database)
- **JWT** (Authentication for secure access)

## ⚙️ Requirements
Before running the project, ensure you have the following installed:
- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/)
- [MySQL/PostgreSQL](https://www.mysql.com/ or https://www.postgresql.org/)

## 🚀 How to Run the Project
### 1️⃣ Clone the Repository
```bash
git clone https://github.com/braga-gustavo/API_Gerencia_Pessoas.git
cd API_Gerencia_Pessoas
```

### 2️⃣ Configure the Database
Edit the `application.properties` file inside `src/main/resources/`, setting up your database credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### 3️⃣ Build and Run the Project
```bash
mvn clean install
mvn spring-boot:run
```
The server will be running at `http://localhost:8080`.

## 📌 API Endpoints
- **POST /people** → Register a new person.
- **GET /people/{id}** → Retrieve a person's details by ID.
- **PUT /people/{id}** → Update a person's information.
- **DELETE /people/{id}** → Delete a person's record.

