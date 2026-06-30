# CustomerEDP
## Customer Engagement & Delivery Platform

Polyglot Microservices project with:
- Java (Spring Boot) - Core Backend & Auth
- Python (FastAPI) - AI Intelligence
- C# (.NET) - Reporting
- React - Frontend Dashboard


## Configuration

### Database & Credentials

1. Copy `src/main/resources/application.properties.template` to `src/main/resources/application.properties`
2. Replace `YOUR_PASSWORD_HERE` with your PostgreSQL password (default: `password`)
3. Replace `YOUR_JWT_SECRET_HERE` with a secret key for JWT (e.g., `mySuperSecretKey1234567890`)


### Default Admin User

After starting the application, create an admin user in the database:

```sql
INSERT INTO members (username, email, password, role, created_at) 
VALUES ('admin', 'admin@example.com', '$2a$10$dXJ3SW6G7P50lGmMkkmwe.20cQQubK3.HZWzG3YB1tlRy.fqvM/BG', 'ADMIN', NOW());
```


### Environment Variables

1. Copy `.env.template` to `.env`
2. Replace `YOUR_PASSWORD_HERE` with your PostgreSQL password (default: `password`)


### Running the Application

1. Copy `.env.template` to `.env` and set `POSTGRES_PASSWORD`
2. Copy `src/main/resources/application.properties.template` to `src/main/resources/application.properties` and set your credentials
3. Run `docker-compose up -d`
4. Build and run the Java service:
   ```bash
   cd java-service
   mvn clean package -DskipTests
   java -jar target/java-service-0.0.1-SNAPSHOT.jar