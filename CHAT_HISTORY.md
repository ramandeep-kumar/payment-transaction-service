# Chat History Summary

## Key Decision Points

- Pivoted from initial Node.js scaffold to Spring Boot + Java upon requirement clarification.
- Selected PostgreSQL with Flyway for schema migrations; added `orders` and `transactions` with UUIDs and JSONB.
- Implemented JWT auth with a simple `/auth/token` for testing; secured `/payments/*` endpoints.
- Integrated Authorize.Net via official Java SDK; wrapped in `AuthorizeNetClient`.
- Added unit tests (JUnit 5, Mockito, MockMvc) and JaCoCo coverage with excludes for gateway/security.

## Alternatives Considered

- ORMs: Considered JPA/Hibernate vs MyBatis; chose Spring Data JPA for speed.
- DB migrations: Considered Liquibase; chose Flyway for simplicity.
- Testing DB: Real Postgres vs H2; chose H2 for faster CI tests.

## How AI Helped

- Automated scaffolding of Spring Boot project, Flyway config, and Docker Compose.
- Generated boilerplate for JWT filter, entities, repositories, DTOs, and service/controller.
- Iteratively fixed build/test issues (BigDecimal amounts, H2 config, JWT filter in tests).
- Authored OpenAPI spec and documentation updates.

## Next Steps

- Expand negative-path tests and increase coverage ≥60%.
- Add validation annotations to DTOs and global error handling.