package fr.vernoux.rentabike;

import fr.vernoux.rentabike.application.BikeScanner;
import fr.vernoux.rentabike.application.BikeSearcher;
import fr.vernoux.rentabike.application.JourneyCreator;
import fr.vernoux.rentabike.application.JourneySearcher;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.IdGenerator;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RentABikeApplicationConfig {

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
