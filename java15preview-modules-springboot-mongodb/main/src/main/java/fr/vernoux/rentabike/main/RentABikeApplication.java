package fr.vernoux.rentabike.main;

import fr.vernoux.rentabike.application.BikeScanner;
import fr.vernoux.rentabike.application.BikeSearcher;
import fr.vernoux.rentabike.application.JourneyCreator;
import fr.vernoux.rentabike.application.JourneySearcher;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.IdGenerator;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {"fr.vernoux.rentabike.exposition", "fr.vernoux.rentabike.infrastructure"})
@EnableMongoRepositories(basePackages = "fr.vernoux.rentabike.infrastructure")
public class RentABikeApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentABikeApplication.class, args);
    }

    @Bean
    public BikeSearcher bikeSearcher(BikeRepository bikeRepository) {
        return new BikeSearcher(bikeRepository);
    }

    @Bean
    public BikeScanner bikeScanner(BikeRepository bikeRepository, EventBus eventBus) {
        return new BikeScanner(bikeRepository, eventBus);
    }

    @Bean
    public JourneyCreator journeyCreator(JourneyRepository journeyRepository, IdGenerator idGenerator) {
        return new JourneyCreator(journeyRepository, idGenerator);
    }

    @Bean
    public JourneySearcher journeySearcher(JourneyRepository journeyRepository) {
        return new JourneySearcher(journeyRepository);
    }
}
