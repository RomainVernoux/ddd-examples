package com.zenika.dddexample.rentabike;

import com.zenika.dddexample.rentabike.application.BikeScanner;
import com.zenika.dddexample.rentabike.application.BikeSearcher;
import com.zenika.dddexample.rentabike.application.JourneyCreator;
import com.zenika.dddexample.rentabike.application.JourneySearcher;
import com.zenika.dddexample.rentabike.domain.EventBus;
import com.zenika.dddexample.rentabike.domain.IdGenerator;
import com.zenika.dddexample.rentabike.domain.bike.BikeRepository;
import com.zenika.dddexample.rentabike.domain.journey.JourneyRepository;
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
