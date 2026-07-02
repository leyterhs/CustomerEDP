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
## Android App

Το Android app βρίσκεται σε ξεχωριστό repository:  
[CustomerEDP-Android](https://github.com/leyterhs/CustomerEDP-Android)

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
   ```

## API Endpoints

### Authentication
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Admin (ADMIN only)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/users` | Get all users |
| POST | `/api/admin/users` | Create a new user |
| DELETE | `/api/admin/users/{id}` | Delete a user |

### Clients
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/clients` | Get all clients |
| POST | `/api/clients` | Create a new client |
| GET | `/api/clients/{id}` | Get a client by ID |
| PUT | `/api/clients/{id}` | Update a client |
| DELETE | `/api/clients/{id}` | Delete a client |

### Engagements
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/engagements` | Get all engagements |
| POST | `/api/engagements` | Create a new engagement |
| GET | `/api/engagements/{id}` | Get an engagement by ID |
| PUT | `/api/engagements/{id}` | Update an engagement |
| DELETE | `/api/engagements/{id}` | Delete an engagement |

### Deliveries
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/deliveries` | Get all deliveries |
| POST | `/api/deliveries` | Create a new delivery |
| GET | `/api/deliveries/{id}` | Get a delivery by ID |
| PUT | `/api/deliveries/{id}` | Update a delivery |
| DELETE | `/api/deliveries/{id}` | Delete a delivery |

### AI Service (Python)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/ai/suggest/{delivery_id}` | Get AI priority suggestion |

### Report Service (C#)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/report/engagement/{id}` | Download PDF report for an engagement |

> **Note:** All endpoints except `/api/auth/register` and `/api/auth/login` require a JWT token in the `Authorization: Bearer <token>` header.




## Android App

Το Android app βρίσκεται στον υποφάκελο `android-app/` (ως Git submodule):  
[CustomerEDP-Android](https://github.com/leyterhs/CustomerEDP-Android)

Για να το κατεβάσετε μαζί με το κύριο project, χρησιμοποιήστε:
```bash
git clone --recursive https://github.com/leyterhs/CustomerEDP.git
```

### Ρυθμίσεις

1. Άνοιξε το `Config.java` και όρισε το `BASE_URL`:
   - Για emulator: `http://10.0.2.2:8080/`
   - Για πραγματικό κινητό: `http://<YOUR_IP>:8080/`
   - Για ngrok: `https://<YOUR_NGROK_URL>/`

2. Τρέξε το app από το Android Studio.

### Λειτουργίες
- **Login** με `admin` / `password`
- **Admin Panel** – Διαχείριση χρηστών (CRUD)

![CustomerEDP Logo](logo.png)