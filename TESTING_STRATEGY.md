# Testing Strategy

## Frameworks

- JUnit 5 for unit tests
- Mockito for mocking dependencies
- Spring MockMvc for controller slice tests
- H2 in-memory DB for tests (application-test)
- JaCoCo for coverage

## Coverage Goals

- ≥60% overall line coverage
- Service layer flows (purchase, authorize, capture, void, refund)
- Controller endpoints (200 responses)
- Negative-path tests where feasible

## Approach

- Unit tests for each service method with Authorize.Net mocked
- Controller slice tests with filters disabled
- H2 configuration for context loads; Flyway disabled in tests
- Measure coverage with JaCoCo; exclude gateway/security/config
