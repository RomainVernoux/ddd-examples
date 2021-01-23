# Java 15 (with preview features) + Spring Boot + MongoDB

Particular points of interest:

- The entire domain logic is encapsulated in the domain layer: `Bike.java`, `Journey.java`, `Position.java`
- The variable, methods and class names reveal the intention and use the ubiquitous language
- In aggregates, we use the ValueObjectId (VOID) pattern
- Java 15 records (preview feature) are used for all value objects and DTOs, avoiding a lot of boilerplate code! Not 
  that using records as DTO requires `Jackson 2.12+`.
- There is no (zero) setter in the code!
- Getters are used sparingly, and never in the domain or application layer!
- Equality on value objects is implemented with field-by-field comparison (using Java 15 records)
- Equality on entities is implemented by comparing ids (in the parent class `Entity.java`)
- We used inside-out TDD: starting with unit tests on aggregates, then moving to application service unit tests with
  doubles for infrastructure services.
- We chose not to unit test controllers or repository, since they mostly pass-through to framework code
- Integration tests cover the whole backend, from the controllers to a real database instance
- We used Java modules to enforce the constraints of the Hexagonal architecture, in particular the dependencies between
  layers. Note that due to a [current limitation](http://openjdk.java.net/projects/jigsaw/spec/issues/#MultiModuleJARs)
  of Java modules, there can only be one module per Jar. This implies that all layers of the architecture must have 
  their own `jar`, which in turns requires them to have their own Maven submodules. If you take into account the 
  duplication of logic between the `pom.xml` files and the `module-info.java` files, as well as the poor support and 
  developer experience of Java modules, I do not believe that using modules to isolate layers is worth the trouble.
  Actually, I do not believe that using Maven submodules for this use case is much better. See the other examples for
  simpler architectures.
