# Project Structure

```
src/main/java/com/talentica/paymentservice/
├── config/        # App configuration (security, Swagger/OpenAPI, JWT)
├── constant/      # Constant values
├── controller/    # REST API controllers
├── entity/        # JPA entities (Order, Transaction)
├── enum/          # Enum classes (PaymentStatus, TransactionType)
├── exception/     # Custom exceptions
├── mapper/        # Mapper classes (DTO <-> Entity)
├── model/         # Request/Response models (DTOs)
├── repository/    # JPA repositories
├── service/       # Service interfaces
├── service/impl/  # Service implementations
├── util/          # Utilities (JWT, etc.)
PaymentServiceApplication.java # Main class
```

- `resources/application.properties`: Configuration
- `.env.example`: Environment variable sample
- `docker-compose.yml`: Multi-container setup
