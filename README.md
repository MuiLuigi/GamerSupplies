# GamerSupplies

> **Project by Star Mode Studios**

GamerSupplies is a comprehensive E-Commerce Platform dedicated to gaming retail. It is designed to manage inventory, track stock across multiple distribution centers, and provide a seamless customer ordering experience.

## Technology Stack

*   **Backend:** Java 17, Spring Boot 3.4.2
*   **Database:** H2 (In-Memory Development) / PostgreSQL (Production ready)
*   **Frontend:** Thymeleaf (with custom Glassmorphism UI)
*   **Security:** Spring Security
*   **Build Tool:** Maven

## Team Members

| Name | Role |
| :--- | :--- |
| **Nicholas Carreiro** | Developer |
| **Musa Jama** | Developer |
| **Abhishek Masur Jayatheertha** | Developer |
| **Fahad Arif** | Developer |

## Prerequisites

Before running the application, ensure you have the following installed:

*   [Java 17 JDK](https://adoptium.net/) (or a compatible OpenJDK distribution)
*   Maven 3.8+ (optional, the project includes `./mvnw`)
*   PostgreSQL

## How to Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/abhishake2323/GamerSupplies.git
    cd GamerSupplies
    ```
2.  **Configure the Database:**
    Create a PostgreSQL database named gamersupplies.
    Update src/main/resources/application.properties with your PostgreSQL username and password.
    The products table and sample data will be created automatically on first run.

3.  **Run the application (Development Mode):**
    The application defaults to an **H2 in-memory database**. This means you do not need to install or configure an external database server to test the application locally. Data will reset upon restart.
    
    If `spring-boot:run` fails in your environment, package and run the fat JAR directly:
    ```bash
    mvn clean package -DskipTests
    java -jar target\gamersupplies-0.0.1-SNAPSHOT.jar
    ```

4.  **Access the application:**
    *   Open your browser and navigate to `http://localhost:8080`.
    *   You can access the H2 DB console at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:gamersuppliesdb`, un/pw: `sa` / `password`).

## Running Tests

The application includes a comprehensive JUnit 5 test suite validating models, MVC controllers, service logic, and database repositories. Run the suite using:
```bash
mvn test
```
