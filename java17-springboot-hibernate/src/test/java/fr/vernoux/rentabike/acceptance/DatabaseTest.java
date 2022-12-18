package fr.vernoux.rentabike.acceptance;

import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.TestPropertySourceUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ContextConfiguration(initializers = DatabaseTest.DatabaseTestContextInitializer.class)
@Transactional
abstract class DatabaseTest {

    private static final PostgreSQLContainer<?> PG_DB_CONTAINER;

    static {
        PG_DB_CONTAINER = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"))
                .withDatabaseName("rentabike")
                .withUsername("rentabike")
                .withPassword("pwd");
        PG_DB_CONTAINER.start();
    }

    public static class DatabaseTestContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext applicationContext) {
            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                    applicationContext,
                    "spring.datasource.url=" + PG_DB_CONTAINER.getJdbcUrl(),
                    "spring.datasource.username=" + PG_DB_CONTAINER.getUsername(),
                    "spring.datasource.password=" + PG_DB_CONTAINER.getPassword()
            );
        }
    }
}
