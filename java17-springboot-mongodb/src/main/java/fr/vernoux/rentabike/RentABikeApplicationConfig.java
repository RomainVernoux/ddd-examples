package fr.vernoux.rentabike;

import fr.vernoux.rentabike.application.BikeScanner;
import fr.vernoux.rentabike.application.BikeSearcher;
import fr.vernoux.rentabike.application.JourneySearcher;
import fr.vernoux.rentabike.application.JourneyStarter;
import fr.vernoux.rentabike.domain.EventBus;
import fr.vernoux.rentabike.domain.IdGenerator;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;
import java.util.UUID;

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
    public JourneyStarter journeyStarter(JourneyRepository journeyRepository, IdGenerator idGenerator) {
        return new JourneyStarter(journeyRepository, idGenerator);
    }

    @Bean
    public JourneySearcher journeySearcher(JourneyRepository journeyRepository) {
        return new JourneySearcher(journeyRepository);
    }

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new BikeIdConverter(),
                new JourneyIdConverter()
        ));
    }

    private static class BikeIdConverter implements Converter<BikeId, UUID> {
        @Override
        public UUID convert(BikeId source) {
            return source.value();
        }
    }

    private static class JourneyIdConverter implements Converter<JourneyId, UUID> {
        @Override
        public UUID convert(JourneyId source) {
            return source.value();
        }
    }
}
