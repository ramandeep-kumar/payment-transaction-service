# Unit Test Coverage Summary

## Methodology

- Used JUnit + Mockito for unit testing
- Coverage measured using JaCoCo

## Summary (from latest JaCoCo run)

- Overall project (lines): 38.0%
- Overall project (instructions): 44.2%
- Service layer `PaymentService` (lines): 64.9%
- Controller layer `PaymentController` (lines): 50.0%
- Security `JwtAuthenticationFilter` (lines): 5.6%
- DTOs (lines): 57.1%

Note:
- Unit tests focus on `PaymentService` flows (purchase, authorize, capture) and controller purchase path. Additional tests for void/refund and negative paths will raise coverage beyond 60% as required.

Artifacts:
- Detailed HTML report: target/site/jacoco/index.html
