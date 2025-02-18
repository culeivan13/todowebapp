# Spring Boot MySQL CRUD Application
A simple CRUD application built with Spring Boot and MySQL.

## Tech Stack
- **Backend**: Spring Boot, Spring Web, Spring Security, JPA
- **Frontend**: Server side rendering using Thymeleaf
- **Database**: MySQL
- **Build Tool**: Maven

## Run locally
1. Clone the repository:
   ```bash
   git clone https://github.com/culeivan13/todowebapp.git
   cd todowebapp
   ```
2. Make `application-dev.properties` with your MySQL credentials.
3. Change the spring profile to dev.
4. Build and run the project:
    ``` bash
    mvn clean install
    mvn spring-boot:run
   ```
5. Open http://localhost:8080/.
