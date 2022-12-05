# Java 17 + Spring Boot + MongoDB

Particular points of interest:

- The entire domain logic is encapsulated in the domain layer: `Bike.java`, `Journey.java`, `Position.java`
- The variable, methods and class names reveal the intention and use the ubiquitous language
- In aggregates, we use the Value Object ID (VOID) pattern
- There is no (zero) setter in the code!
- Getters are used sparingly, and never in the domain or application layer!
- Thanks to the cleverness of Spring Data MongoDB, we can avoid paying the price of a dedicated persistence model in the
  infrastructure layer, but this requires a small compromise (private no-args constructors) in the domain.
- Value objects, domain events and DTOs are implemented using records
- Equality on entities is implemented by comparing ids (in the parent class `Entity.java`)
- We used inside-out TDD: starting with unit tests on aggregates, then moving to application service unit tests with
  doubles for infrastructure services.
- We chose not to unit test controllers or repository, since they mostly pass through to framework code
- Integration tests cover the whole backend, from the controllers to a real database instance (
  using [Testcontainers](https://www.testcontainers.org/))
- We used the [ArchUnit](https://www.archunit.org/) testing framework in `HexagonalArchitectureTest.java` to enforce the
  constraints of the Hexagonal architecture
