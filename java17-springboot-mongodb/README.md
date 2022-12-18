# Java 17 + Spring Boot + MongoDB

Particular points of interest:

- The entire domain logic is encapsulated in the domain layer: `Bike.java`, `Journey.java`, `Position.java`
- The variable, methods and class names reveal the intention and use the ubiquitous language
- In aggregates, we use the Value Object ID (VOID) pattern. This provides additional type-safety for our code, but
  requires a manual unwrapping when converting to DTOs and storing in Mongo (cf. the `MongoCustomConversions` bean
  declared in `RentABikeApplicationConfig.java`).
- There is no (zero) setter in the code!
- Getters are used sparingly, and never in the domain or application layer!
- Aggregates are created using factory methods named with the ubiquitous language. This gives us an opportunity to
  expose the business meaning of the object creation, and gives us a no-args constructor to use for reflection in the
  infrastructure layer.
- Value objects, domain events and DTOs are implemented using records
- We used inside-out TDD: starting with unit tests on aggregates, then moving to application service unit tests with
  doubles for infrastructure services.
- We chose not to unit test controllers or repository, since they mostly pass through to framework code
- Integration tests cover the whole backend, from the controllers to a real database instance (
  using [Testcontainers](https://www.testcontainers.org/))
- We used the [ArchUnit](https://www.archunit.org/) testing framework in `HexagonalArchitectureTest.java` to enforce the
  constraints of the Hexagonal architecture
