package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import fr.vernoux.rentabike.doubles.IdGeneratorDouble;
import fr.vernoux.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JourneyCreatorTest {

    @Test
    void createsJourneys() {
        String uuid = "1";
        BikeId bikeId = new BikeId("2");
        Position position = new Position(48d, 2d);
        JourneyRepository journeyRepository = new JourneyRepositoryDouble();
        IdGeneratorDouble idGenerator = new IdGeneratorDouble();
        idGenerator.configure(uuid);
        JourneyCreator journeyCreator = new JourneyCreator(journeyRepository, idGenerator);

        journeyCreator.create(bikeId, position);

        List<Journey> allJourneys = journeyRepository.findAll();
        assertThat(allJourneys).usingFieldByFieldElementComparator()
                .containsExactly(new Journey(new JourneyId(uuid), bikeId, position));
    }
}
