# Customer Rewards API

Spring Boot REST API that calculates reward points per customer based on purchase transactions over a three-month period.

## Tech Stack

| Technology | Version / Purpose |
|---|---|
| Java | 21 |
| Spring Boot | 3.5.13 |
| Spring Data JPA | Database operations |
| H2 | Embedded database |
| Testing | JUnit 5 + Mockito |
| Utilities | Lombok |

## Getting Started

Clone the repository:

```bash
git clone https://github.com/reshmamanchineni24-tech/customer-rewards-api.git
cd customer-rewards-api
```

**On Windows:**

```bash
mvnw spring-boot:run
```

**On Mac/Linux:**

```bash
./mvnw spring-boot:run
```

Base URL: `http://localhost:8080`

The H2 database is automatically populated with sample transactions on startup using `data.sql`.

**H2 Database Console:**

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:rewardsdb`
- Username: `sa`
- Password: *(leave blank)*

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/rewards` | Retrieve reward summaries for all customers |
| GET | `/api/rewards/{customerId}` | Retrieve reward summary for a specific customer |
| GET | `/api/transactions` | Retrieve all transactions |
| POST | `/api/transactions` | Create a new transaction |
| DELETE | `/api/transactions/{transactionId}` | Delete an existing transaction |

### GET /api/rewards

Returns monthly reward points and total reward points for all customers.

**Sample Response:**

```json
[
  {
    "customerId": "C001",
    "customerName": "Priya Sharma",
    "monthlyPoints": [
      {
        "yearMonth": "2026-01",
        "points": 115
      },
      {
        "yearMonth": "2026-02",
        "points": 250
      },
      {
        "yearMonth": "2026-03",
        "points": 0
      }
    ],
    "totalPoints": 365
  }
]
```

### GET /api/rewards/{customerId}

Returns the reward summary for a specific customer.

**Example Request:**

```http
GET /api/rewards/C001
```

**Sample Response:**

```json
{
  "customerId": "C001",
  "customerName": "Priya Sharma",
  "monthlyPoints": [
    {
      "yearMonth": "2026-01",
      "points": 115
    },
    {
      "yearMonth": "2026-02",
      "points": 250
    },
    {
      "yearMonth": "2026-03",
      "points": 0
    }
  ],
  "totalPoints": 365
}
```

### GET /api/transactions

Retrieves all customer purchase transactions.

**Sample Response:**

```json
[
  {
    "transactionId": "TXN001",
    "customerId": "C001",
    "customerName": "Priya Sharma",
    "amount": 120,
    "transactionDate": "2026-01-05"
  }
]
```

### POST /api/transactions

Creates a new transaction for a customer.

**Sample Request:**

```json
{
  "transactionId": "TXN011",
  "customerId": "C001",
  "customerName": "Priya Sharma",
  "amount": 85.00,
  "transactionDate": "2026-04-01"
}
```

**Successful Response:**

- HTTP Status: `201 Created`
- Returns the saved transaction
- Includes a `Location` header

### DELETE /api/transactions/{transactionId}

Deletes a transaction using its transaction ID.

**Example Request:**

```http
DELETE /api/transactions/TXN001
```

## Error Responses

All API errors follow a consistent response structure.

**Example:**

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with ID: C999",
  "timestamp": "2026-04-07T10:30:00"
}
```

| HTTP Status | Description |
|---|---|
| 400 | Invalid request or missing required fields |
| 404 | Customer or transaction not found |
| 500 | Unexpected internal server error |

## Reward Points Calculation

Reward points are calculated based on each purchase amount.

| Transaction Amount | Reward Points |
|---|---|
| $50 or less | 0 points |
| Above $50 up to $100 | 1 point for every dollar above $50 |
| Above $100 | 50 points + 2 points for every dollar above $100 |

**Examples:**

- $75 → 25 points
- $120 → 90 points
- $200 → 250 points

Points are grouped by `yearMonth` (format: `YYYY-MM`), and the total reward points are calculated for each customer.

## Running Tests

**On Windows:**

```bash
mvnw test
```

**On Mac/Linux:**

```bash
./mvnw test
```

Unit tests use JUnit 5 and Mockito to validate reward calculations and application behavior.

## Project Structure

```text
src/main/java/com/rewards/
├── controller/
│   └── RewardsController.java
├── service/
│   └── RewardsService.java
├── repository/
│   └── TransactionRepository.java
├── model/
│   └── Transaction.java
├── dto/
│   ├── RewardSummary.java
│   ├── MonthlyPoints.java
│   ├── TransactionRequest.java
│   └── ErrorResponse.java
└── exception/
    ├── RewardsException.java
    ├── CustomerNotFoundException.java
    ├── TransactionNotFoundException.java
    └── GlobalExceptionHandler.java
```

## Sample Customer Data

| Customer ID | Customer Name | Transactions |
|---|---|---|
| C001 | Priya Sharma | 4 |
| C002 | Rahul Reddy | 3 |
| C003 | Ananya Rao | 3 |

The application uses an H2 in-memory database with sample customer transactions loaded through `data.sql` during startup.