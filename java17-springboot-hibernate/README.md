# Java 17 + Spring Boot + Hibernate

Particular points of interest:

- The entire domain logic is encapsulated in the domain layer: `Bike.java`, `Journey.java`, `Position.java`
- The variable, methods and class names reveal the intention and use the ubiquitous language
- In aggregates, we use the Value Object ID (VOID) pattern
- There is no setter in the code, except for JPA entities in the infrastructure layer
- Getters are used sparingly, and never in the domain or application layer!
- Aggregates are created using factory methods named with the ubiquitous language. This gives us an opportunity to
  expose the business meaning of the object creation.
- Because JPA is not very DDD-friendly, we have to maintain a dedicated persistence model in the
  infrastructure layer and manually synchronize state between entities from the domain and manage entities in each
  repository. Mapping also requires public all-args constructors in the domain aggregates.
- Value objects, domain events and DTOs are implemented using records
- We used inside-out TDD: starting with unit tests on aggregates, then moving to application service unit tests with
  doubles for infrastructure services.
- We chose not to unit test controllers or repository, since they mostly pass through to framework code
- Integration tests cover the whole backend, from the controllers to a real database instance (
  using [Testcontainers](https://www.testcontainers.org/))
- We used the [ArchUnit](https://www.archunit.org/) testing framework in `HexagonalArchitectureTest.java` to enforce the
  constraints of the Hexagonal architecture
