# 🩸 Blood Bank Finder API

> *Because in emergencies, every second counts.*

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

| Technology       | Purpose                  |
|------------------|--------------------------|
| Java 24          | Core language            |
| Spring Boot 4.1  | Backend framework        |
| Spring Data JPA  | Database operations      |
| MySQL 8.0        | Data storage             |
| Lombok           | Boilerplate-free code    |
| Validation       | Input validation         |
| Maven            | Dependency management    |

---

## ✨ Features

- 🏥 **Blood Bank Management** — Register and manage blood banks city-wise
- 🧑 **Donor Registry** — Register donors with smart eligibility checks
- 🩸 **Stock Tracking** — Real-time blood inventory per blood bank
- 🔍 **Smart Search** — Find available blood by group AND city instantly
- 🚨 **Critical Alerts** — Identify blood groups critically low (less than 2 units)
- 🛡️ **Input Validation** — All fields validated before saving
- 🚫 **Duplicate Prevention** — Smart checks on donors and blood banks

---

## 🔑 The Key Endpoint

This is the heart of the entire project:

```
GET /api/stock/search?bloodGroup=O%2B&city=Patna
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

## 📡 API Reference

### 🏥 Blood Banks — `/api/bloodbank`

| Method   | Endpoint              | Description                    |
|----------|-----------------------|--------------------------------|
| `GET`    | `/all`                | Get all blood banks            |
| `GET`    | `/{id}`               | Get blood bank by ID           |
| `GET`    | `/filter?city=Patna`  | Get blood banks in a city      |
| `POST`   | `/add`                | Register new blood bank        |
| `PUT`    | `/{id}`               | Update blood bank details      |
| `DELETE` | `/{id}`               | Remove blood bank              |

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

---

### 🧑 Donors — `/api/donors`

| Method   | Endpoint                      | Description                        |
|----------|-------------------------------|------------------------------------|
| `GET`    | `/all`                        | Get all donors                     |
| `GET`    | `/{id}`                       | Get donor by ID                    |
| `GET`    | `/bloodgroup?type=O%2B`       | Get donors by blood group          |
| `GET`    | `/bank/{bankId}`              | Get all donors of a blood bank     |
| `POST`   | `/add`                        | Register new donor                 |
| `PUT`    | `/{id}`                       | Update donor details               |
| `DELETE` | `/{id}`                       | Remove donor                       |

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
  "bloodBankId": 1
}
```

---

### 🩸 Blood Stock — `/api/stock`

| Method    | Endpoint                                        | Description                        |
|-----------|-------------------------------------------------|------------------------------------|
| `GET`     | `/all`                                          | Get all stock entries              |
| `GET`     | `/bank/{bankId}`                                | Get stock of a specific blood bank |
| `GET`     | `/bloodgroup?type=O%2B`                         | Get stock by blood group           |
| `GET`     | `/critical`                                     | 🚨 Get critically low stock        |
| `GET`     | `/search?bloodGroup=O%2B&city=Patna`            | 🔑 Find blood by group and city    |
| `POST`    | `/add`                                          | Add new stock entry                |
| `PATCH`   | `/{id}/update?units=2`                          | Update stock units                 |
| `DELETE`  | `/{id}`                                         | Remove stock entry                 |

**Sample Request Body — Add Stock:**
```json
{
  "bloodBankId": 1,
  "bloodGroup": "O+",
  "units": 20
}
```

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
├── BloodAvailabilityDTO.java         — Search response DTO
│
├── BloodBankRepository.java          — Blood Bank DB operations
├── DonerRepository.java              — Donor DB operations
├── BloodStockRepository.java         — Blood Stock DB operations
│
├── BloodBankService.java             — Blood Bank business logic
├── DonerService.java                 — Donor business logic
├── BloodStockService.java            — Blood Stock business logic
│
├── BloodBankController.java          — Blood Bank REST endpoints
├── DonerController.java              — Donor REST endpoints
└── BloodStockController.java         — Blood Stock REST endpoints
```

---

## 🛠️ Setup & Installation

### Prerequisites
- Java 17 or above
- MySQL 8.0 or above
- Maven

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

Open `src/main/resources/application.properties` and update:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bloodbankdb
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

**4. Run the application**
```bash
mvn spring-boot:run
```

---

## 👨‍💻 Author

**Aditya Raj**
B.Tech CSE — IIIT Kalyani (Batch 2028)
Passionate about building backends that solve real-world problems.

[![GitHub](https://img.shields.io/badge/GitHub-vsaaditya-black)](https://github.com/vsaaditya)

---

> *"The best code is the code that saves lives."* ❤️
