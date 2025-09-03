# Project Structure

```
src/main/java/com/example/payment/
├── config/        # App configuration (security filter chain)
├── controller/    # REST API controllers
├── dto/           # Request/Response DTOs
├── entity/        # JPA entities (Order, Transaction)
├── gateway/       # Authorize.Net client wrapper
├── repository/    # Spring Data JPA repositories
├── security/      # JWT authentication filter
├── service/       # Business services
PaymentGatewayApplication.java # Main class

src/main/resources/
├── application.properties
└── db/migration/  # Flyway migrations (V1__init.sql)
```

- `docker-compose.yml`: Multi-container setup (PostgreSQL + app)
