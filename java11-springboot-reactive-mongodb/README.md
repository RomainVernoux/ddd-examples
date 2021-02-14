# Java 11 + Spring Boot + Reactive + Hibernate

Particular points of interest:

- The entire domain logic is encapsulated in the domain layer: `Bike.java`, `Journey.java`, `Position.java`
- The variable, methods and class names reveal the intention and use the ubiquitous language
- In aggregates, we use the ValueObjectId (VOID) pattern
- There is no (zero) setter in the code!
- Getters are used sparingly, and never in the domain or application layer!
- The `get` prefix is reserved for getters, business methods don't use them (e.g., `Journey.length()`)
- Equality on value objects is implemented with field-by-field comparison (we use reflection in `ValueObject.java`)
- Equality on entities is implemented by comparing ids (in the parent class `Entity.java`)
- The reactive architecture requires the use of Reactor, a technical library, in the domain and application layers.
  This is not ideal but still better than introducing a custom facade to abstract these complex behaviors!
- Looking back at the technical complexity brought with the Reactive approach, one should really be sure to need the 
  Reactive benefits before using it everywhere. This is definitely not a silver bullet!
- We used inside-out TDD: starting with unit tests on aggregates, then moving to application service unit tests with
  doubles for infrastructure services.
- We chose not to unit test controllers or repository, since they mostly pass-through to framework code
- Integration tests cover the whole backend, from the controllers to a real database instance
- We used the [ArchUnit](https://www.archunit.org/) testing framework in `HexagonalArchitectureTest.java` to enforce the
  constraints of the Hexagonal architecture
