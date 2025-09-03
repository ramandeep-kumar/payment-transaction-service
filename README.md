# Payment Transaction Service

A robust backend application with Authorize.Net Sandbox payment integration.

## Features
- JWT Authentication
- Authorize.Net Sandbox API integration
- Core payment flows: purchase, refund, cancel, authorize/capture
- PostgreSQL database for orders & transaction history
- Clear error responses
- Swagger API documentation

## Setup

### Prerequisites
- Java 11+
- Docker

### Environment Variables
Configure `.env` (see `.env.example`).

### Run with Docker Compose

```bash
docker-compose up --build
```

### API Documentation

Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## Testing

```bash
./mvnw test
```

## Database

PostgreSQL is used for persistence. Use `docker-compose.yml` for local setup.

## Authorize.Net Sandbox

Sign up for sandbox credentials [here](https://developer.authorize.net/hello_world/sandbox.html).
