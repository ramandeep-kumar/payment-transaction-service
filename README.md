# Payment Transaction Service

A robust backend application with Authorize.Net Sandbox payment integration.

## Features
- JWT Authentication (Bearer)
- Authorize.Net Sandbox API integration (official Java SDK)
- Core payment flows: purchase, authorize/capture, void (cancel), refund (full/partial)
- PostgreSQL + Flyway for orders & transaction history
- Clear error responses

## Setup

### Prerequisites
- Java 17+
- Docker

### Environment Variables
Set the following (via shell, `.env`, or docker-compose):

- `API_LOGIN_ID` (Authorize.Net sandbox)
- `TRANSACTION_KEY` (Authorize.Net sandbox)
- `JWT_SECRET` (HS256 secret; 32+ chars recommended)
- `DB_URL` (e.g. jdbc:postgresql://localhost:5432/paymentservice)
- `DB_USERNAME` (e.g. postgres)
- `DB_PASSWORD` (e.g. postgres)

### Run with Docker Compose

```bash
docker-compose up --build
```

### Run locally (without Docker)

```bash
./mvnw spring-boot:run
```

### JWT Authentication

1) Generate a token (for testing):

```bash
curl -X POST "http://localhost:8080/auth/token?sub=dev-user"
```

2) Call secured endpoints with Authorization header:

```bash
curl -H "Authorization: Bearer <JWT>" \
     -H "Content-Type: application/json" \
     -d '{
           "externalOrderId":"ext-1",
           "customerEmail":"a@b.com",
           "amountCents":1000,
           "currency":"USD",
           "description":"Test purchase",
           "cardNumber":"4111111111111111",
           "cardExpiry":"2030-12",
           "cardCvv":"123"
         }' \
     http://localhost:8080/payments/purchase
```

### API Specification

See `API-SPECIFICATION.yaml` (OpenAPI 3).

## Testing

```bash
./mvnw test
```

Coverage HTML report: `target/site/jacoco/index.html`

## Database

PostgreSQL is used for persistence. Use `docker-compose.yml` for local setup. Migrations via Flyway under `src/main/resources/db/migration`.

## Authorize.Net Sandbox

Sign up for sandbox credentials (login ID and transaction key) at `https://developer.authorize.net/hello_world/sandbox.html`.
