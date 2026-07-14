# 🩸 Blood Bank Finder API

> *Because in emergencies, every second counts.*

---

## 🌐 Live Demo

**API is live at:** https://blood-bank-finder-api.onrender.com

> ⚠️ Hosted on Render's free tier — the server sleeps after 15 minutes of inactivity. First request after sleep may take 30-60 seconds (cold start) while it wakes up.

### Quick Test:
```
GET https://blood-bank-finder-api.onrender.com/actuator/health
```

Expected response:
```json
{"status": "UP"}
```

---

## 📌 About The Project

Finding blood during a medical emergency in India means calling blood bank after blood bank — wasting precious time. This API solves that problem. One request instantly tells you which blood banks in your city have the required blood group available right now.

```
❌ Without this API:
   Patient needs O- blood at 3 AM
   → Call blood bank 1 — no answer
   → Call blood bank 2 — don't have O-
   → Call blood bank 3 — closed
   → 2 hours wasted 😢

✅ With this API:
   GET /api/stock/search?bloodGroup=O-&city=Patna
   → Instant list of blood banks with O- available
   → Call the nearest one
   → Life saved ❤️
```

---

## ⚙️ Tech Stack

| Technology          | Purpose                        |
|----------------------|---------------------------------|
| Java 17             | Core language                  |
| Spring Boot 4.1     | Backend framework              |
| Spring Data JPA     | Database operations            |
| Spring Security 7   | Authentication & Authorization |
| JWT                 | Stateless token authentication |
| BCrypt              | Password encryption            |
| **PostgreSQL 16**   | **Production database (cloud)**|
| MySQL 8.0           | Local development database     |
| Lombok              | Boilerplate-free code          |
| Validation          | Input validation               |
| Spring Actuator     | App monitoring & health checks |
| **Docker**          | **Containerization (multi-stage build)** |
| **Render**          | **Cloud deployment platform**  |
| Maven               | Dependency management          |

---

## ✨ Features

- 🌐 **Live & Deployed** — Publicly accessible REST API running on Render
- 🐳 **Dockerized** — Multi-stage Docker build for portable, consistent deployments
- 🔐 **JWT Authentication** — Secure login with token-based authentication
- 👥 **Role-Based Access Control** — ADMIN, DOCTOR, USER roles with different permissions
- 🔒 **BCrypt Encryption** — Passwords never stored in plain text
- 🛡️ **Privilege Escalation Prevention** — Public registration always defaults to USER role; elevated roles assigned only by an existing ADMIN
- 🏥 **Blood Bank Management** — Register and manage blood banks city-wise
- 🧑 **Donor Registry** — Register donors with smart eligibility checks
- 🩸 **Stock Tracking** — Real-time blood inventory per blood bank
- 🔍 **Smart Search** — Find available blood by group AND city instantly
- 🚨 **Critical Alerts** — Identify blood groups critically low (less than 2 units)
- 🔗 **JPA Relationships** — Proper foreign key constraints between entities (`@ManyToOne` / `@OneToMany`)
- 🧮 **Custom JPQL Queries** — Aggregations like `SUM` and `COUNT` for stock and donor analytics
- 📄 **Pagination & Sorting** — All list endpoints support page + size + sort
- ⚙️ **Environment Profiles** — Separate `dev` and `prod` configurations
- 📊 **Actuator Monitoring** — Health check, info, and metrics endpoints
- 🧪 **Unit & Integration Tests** — Mockito for service layer, MockMvc for controller layer
- 🛡️ **Input Validation** — All fields validated before saving
- 🚫 **Duplicate Prevention** — Smart checks on donors and blood banks

---

## 👥 Roles & Permissions

| Action | ADMIN | DOCTOR | USER |
|--------|-------|--------|------|
| View blood banks | ✅ | ✅ | ✅ |
| Add / Update / Delete blood bank | ✅ | ❌ | ❌ |
| View donors | ✅ | ✅ | ✅ |
| Add / Update donor | ✅ | ✅ | ❌ |
| Delete donor | ✅ | ❌ | ❌ |
| View stock | ✅ | ✅ | ✅ |
| Add / Update stock | ✅ | ✅ | ❌ |
| Delete stock | ✅ | ❌ | ❌ |
| Search blood by group + city | ✅ | ✅ | ✅ |
| Critical stock alert | ✅ | ✅ | ❌ |

> **Note:** Public registration always assigns the `USER` role. `ADMIN` and `DOCTOR` roles are assigned manually by an existing administrator — preventing privilege escalation through the public API.

---

## 🔑 The Key Endpoint

This is the heart of the entire project:

```
GET /api/stock/search?bloodGroup=O%2B&city=Patna
Authorization: Bearer <your_token>
```

**Sample Response:**
```json
[
  {
    "bloodBankName": "Patna Red Cross Blood Bank",
    "city": "Patna",
    "phone": "0612-2234567",
    "bloodGroup": "O+",
    "units": 20
  },
  {
    "bloodBankName": "PMCH Blood Bank",
    "city": "Patna",
    "phone": "0612-2300000",
    "bloodGroup": "O+",
    "units": 10
  }
]
```

> **Note:** Use `%2B` instead of `+` in the URL for blood groups like O+, A+, B+, AB+.

---

## 🔐 Authentication

All endpoints except `/auth/register`, `/auth/login`, and `/actuator/**` require a valid JWT token.

**Step 1 — Register:**
```
POST /auth/register
```
```json
{
  "username": "hospital1",
  "password": "pass123"
}
```
> Role is always assigned as `USER` automatically — it cannot be set by the client.

**Step 2 — Login:**
```
POST /auth/login
```
```json
{
  "username": "hospital1",
  "password": "pass123"
}
```
→ Returns JWT token

**Step 3 — Use token in every request:**
```
Authorization: Bearer eyJhbGci...
```

---

## 📡 API Reference

### 🔓 Auth — `/auth` (Public)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| `POST` | `/register` | Register new user (always as USER) | ❌ |
| `POST` | `/login` | Login and get JWT token | ❌ |

---

### 🏥 Blood Banks — `/api/bloodbank`

| Method | Endpoint | Description | Role Required |
|--------|----------|-------------|---------------|
| `GET` | `/all` | Get all blood banks (paginated) | Any |
| `GET` | `/{id}` | Get blood bank by ID | Any |
| `GET` | `/filter?city=Patna` | Get blood banks in a city | Any |
| `GET` | `/active?city=Patna` | Get only active blood banks in a city | Any |
| `GET` | `/search?keyword=Red` | Search blood banks by name keyword | Any |
| `GET` | `/count?city=Patna` | Count blood banks in a city | Any |
| `POST` | `/add` | Register new blood bank | ADMIN |
| `PUT` | `/{id}` | Update blood bank details | ADMIN |
| `DELETE` | `/{id}` | Remove blood bank | ADMIN |

**Sample Request Body — Add Blood Bank:**
```json
{
  "name": "Patna Red Cross Blood Bank",
  "address": "Exhibition Road",
  "city": "Patna",
  "pincode": 800001,
  "phone": "0612-2234567",
  "email": "patna@redcross.org",
  "active": true
}
```

**Pagination support:**
```
GET /api/bloodbank/all?page=0&size=5&sort=name
```

---

### 🧑 Donors — `/api/donors`

| Method | Endpoint | Description | Role Required |
|--------|----------|-------------|---------------|
| `GET` | `/all` | Get all donors (paginated) | Any |
| `GET` | `/{id}` | Get donor by ID | Any |
| `GET` | `/bloodgroup?type=O%2B` | Get donors by blood group | Any |
| `GET` | `/bank/{bankId}` | Get all donors of a blood bank | Any |
| `GET` | `/gender?gender=male` | Get donors by gender | Any |
| `GET` | `/age-above?age=25` | Get donors older than given age | Any |
| `GET` | `/eligible` | Get donors eligible to donate (3+ months since last donation) | ADMIN / DOCTOR |
| `GET` | `/count?bloodGroup=O%2B` | Count donors of a blood group | Any |
| `POST` | `/add` | Register new donor | ADMIN / DOCTOR |
| `PUT` | `/{id}` | Update donor details | ADMIN / DOCTOR |
| `DELETE` | `/{id}` | Remove donor | ADMIN |

**Sample Request Body — Add Donor:**
```json
{
  "name": "Aditya Raj",
  "age": 20,
  "gender": "male",
  "bloodGroup": "O+",
  "phone": "9876543210",
  "email": "adi@gmail.com",
  "lastDonationDate": "2025-01-01",
  "bloodBank": {"id": 1}
}
```

---

### 🩸 Blood Stock — `/api/stock`

| Method | Endpoint | Description | Role Required |
|--------|----------|-------------|---------------|
| `GET` | `/all` | Get all stock entries (paginated) | Any |
| `GET` | `/bank/{bankId}` | Get stock of a specific blood bank | Any |
| `GET` | `/bloodgroup?type=O%2B` | Get stock by blood group | Any |
| `GET` | `/critical` | 🚨 Get critically low stock | ADMIN / DOCTOR |
| `GET` | `/critical-under?units=3` | Get stock below a custom threshold | ADMIN / DOCTOR |
| `GET` | `/range?min=0&max=5` | Get stock entries within a unit range | Any |
| `GET` | `/total?bloodGroup=O%2B` | Total units of a blood group across all banks (SUM) | Any |
| `GET` | `/search?bloodGroup=O%2B&city=Patna` | 🔑 Find blood by group and city | Any |
| `POST` | `/add` | Add new stock entry | ADMIN / DOCTOR |
| `PATCH` | `/{id}/update?units=2` | Update stock units | ADMIN / DOCTOR |
| `DELETE` | `/{id}` | Remove stock entry | ADMIN |

**Sample Request Body — Add Stock:**
```json
{
  "bloodGroup": "O+",
  "units": 20,
  "bloodBank": {"id": 1}
}
```

---

### 📊 Monitoring — `/actuator` (Public)

| Endpoint | Description |
|----------|-------------|
| `/actuator/health` | App + DB health status |
| `/actuator/info` | App information |
| `/actuator/metrics` | Performance metrics |

> In production, only `health` and `info` are exposed for security. All endpoints are exposed in the `dev` profile for local debugging.

---

## 🧠 Business Rules

### Donor Eligibility
```
✅ Must be 18 years or older
✅ Must not have donated in the last 3 months
✅ No duplicate registration (same name + phone + blood group)
```

### Stock Rules
```
✅ Units cannot go below 0
✅ Critical alert when units are less than 2
✅ Last updated timestamp recorded automatically
```

### Blood Bank Rules
```
✅ No duplicate blood bank allowed (same name + city)
✅ All required fields validated before saving
```

### Security Rules
```
✅ Passwords encrypted with BCrypt — never stored in plain text
✅ JWT token expires after 24 hours
✅ Role-based access enforced on every endpoint via @PreAuthorize
✅ Public registration always assigns USER role — no privilege escalation
✅ 401 Unauthorized for missing/invalid token
✅ 403 Forbidden for insufficient role
```

---

## 🔗 Database Relationships

```
BloodBank (ONE)
    ├── has many → Donors (MANY)         @OneToMany (CascadeType.ALL, FetchType.LAZY)
    └── has many → BloodStock (MANY)     @OneToMany (CascadeType.ALL, FetchType.LAZY)

Donor (MANY) → belongs to → BloodBank (ONE)      @ManyToOne
BloodStock (MANY) → belongs to → BloodBank (ONE) @ManyToOne
```

Foreign key constraints enforced by the database — deleting a blood bank automatically removes all its donors and stock! `@JsonIgnore` prevents infinite serialization loops between related entities.

---

## 🧮 Custom Queries (JPQL)

Beyond simple derived query methods (`findByCity`, `findByBloodGroup`), the following use custom JPQL for operations derived methods can't express:

```java
@Query("SELECT SUM(s.units) FROM BloodStock s WHERE s.bloodGroup = :bloodGroup")
Long totalUnitsByBloodGroup(@Param("bloodGroup") String bloodGroup);

@Query("SELECT COUNT(b) FROM BloodBank b WHERE b.city = :city")
Long countBanksByCity(@Param("city") String city);

@Query("SELECT d FROM Doner d WHERE d.lastDonationDate < :date")
List<Doner> findDonorsEligibleToDonate(@Param("date") LocalDate date);
```

JPQL queries reference **entity/field names**, not table/column names — making them database-independent (proven by migrating this project from MySQL to PostgreSQL with zero query changes).

---

## 🧪 Testing

- **Unit Tests** — Service layer logic tested in isolation using Mockito (`@Mock`, `@InjectMocks`)
- **Integration Tests** — Full HTTP request/response flow tested using `MockMvc` and `@SpringBootTest`

```bash
./mvnw test
```

---

## ⚙️ Environment Profiles

| Profile | Purpose |
|---------|---------|
| `dev` | Local development — verbose logging, all actuator endpoints exposed |
| `prod` | Production (Render) — minimal logging, only `health`/`info` exposed, DB credentials via environment variables |

Profile is controlled via the `SPRING_PROFILES_ACTIVE` environment variable — never hardcoded.

---

## 🐳 Docker

The application is fully containerized using a **multi-stage Docker build**:

```dockerfile
# Stage 1: Build the jar from source
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Lightweight runtime image
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

**Run locally with Docker:**
```bash
docker build -t blood-bank-api .
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:postgresql://host.docker.internal:5432/bloodbankdb \
  -e SPRING_DATASOURCE_USERNAME=postgres \
  -e SPRING_DATASOURCE_PASSWORD=yourpassword \
  blood-bank-api
```

Multi-stage build keeps the final image small by discarding Maven and build tools — only the JRE and compiled jar ship in the production image.

---

## ☁️ Deployment

Deployed on **Render** using the Dockerfile above, connected to a managed **PostgreSQL** database.

```
GitHub push
    ↓
Render detects commit
    ↓
Builds Docker image (multi-stage)
    ↓
Runs container with environment variables
    ↓
Live at https://blood-bank-finder-api.onrender.com
```

Database credentials, JWT secret, and active profile are all injected via environment variables — never committed to source control.

---

## 📁 Project Structure

```
src/main/java/com/adi/blood_bank/
│
├── BloodBankApplication.java         — Application entry point
│
├── BloodBank.java                    — Blood Bank entity
├── Doner.java                        — Donor entity
├── BloodStock.java                   — Blood Stock entity
├── User.java                         — User entity (auth)
├── BloodAvailabilityDTO.java         — Search response DTO
│
├── BloodBankRepository.java          — Blood Bank DB operations + custom queries
├── DonerRepository.java              — Donor DB operations + custom queries
├── BloodStockRepository.java         — Blood Stock DB operations + custom queries
├── UserRepository.java               — User DB operations
│
├── BloodBankService.java             — Blood Bank business logic
├── DonerService.java                 — Donor business logic
├── BloodStockService.java            — Blood Stock business logic
├── AuthService.java                  — Register + Login logic
│
├── BloodBankController.java          — Blood Bank REST endpoints
├── DonerController.java              — Donor REST endpoints
├── BloodStockController.java         — Blood Stock REST endpoints
├── AuthController.java               — Auth REST endpoints
│
├── JwtUtil.java                      — JWT token generation + validation
├── JwtFilter.java                    — JWT request interceptor
├── SecurityConfig.java               — Spring Security configuration (@EnableMethodSecurity)
├── CustomUserDetailsService.java     — User loading for Spring Security
└── AppConfig.java                    — App configuration properties

src/test/java/com/adi/blood_bank/
├── BloodBankServiceTest.java         — Unit tests (Mockito)
└── BloodBankControllerTest.java      — Integration tests (MockMvc)

Dockerfile                            — Multi-stage container build
```

---

## 🛠️ Local Setup & Installation

### Prerequisites
- Java 17 or above
- PostgreSQL 16 (or MySQL 8.0, if using the `dev` MySQL config)
- Maven
- Docker (optional, for containerized run)

### Steps

**1. Clone the repository**
```bash
git clone https://github.com/vsaaditya/blood-bank-finder-api.git
cd blood-bank-finder-api
```

**2. Create the database**
```sql
CREATE DATABASE bloodbankdb;
```

**3. Configure your credentials**

Open `src/main/resources/application-dev.properties` and update:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bloodbankdb
spring.datasource.username=your_username
spring.datasource.password=your_password
```

**4. Run the application**
```bash
./mvnw spring-boot:run
```

**5. API is live at**
```
http://localhost:8080
```

**6. Register your first user, then promote to ADMIN via SQL**
```
POST http://localhost:8080/auth/register
{
  "username": "admin1",
  "password": "password"
}
```
```sql
UPDATE users SET role = 'ADMIN' WHERE username = 'admin1';
```

---

## 👨‍💻 Author

**Aditya Raj**
B.Tech CSE — IIIT Kalyani (Batch 2028)
Passionate about building backends that solve real-world problems.

[![GitHub](https://img.shields.io/badge/GitHub-vsaaditya-black)](https://github.com/vsaaditya)

---

> *"The best code is the code that saves lives."* ❤️
