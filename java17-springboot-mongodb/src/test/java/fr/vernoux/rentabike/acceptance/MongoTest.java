package fr.vernoux.rentabike.acceptance;

import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ContextConfiguration(initializers = MongoTest.MongoTestContextInitializer.class)
abstract class MongoTest {

    private static final MongoDBContainer MONGO_DB_CONTAINER;

    static {
        MONGO_DB_CONTAINER = new MongoDBContainer(DockerImageName.parse("mongo:5.0"));
        MONGO_DB_CONTAINER.start();
    }

    public static class MongoTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.data.mongodb.uri=" + MONGO_DB_CONTAINER.getReplicaSetUrl("rentabike")
            );
        }
    }
}
