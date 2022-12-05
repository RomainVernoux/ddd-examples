package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.doubles.IdGeneratorDouble;
import fr.vernoux.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static fr.vernoux.rentabike.utils.TestUtils.aRandomPosition;
import static org.assertj.core.api.Assertions.assertThat;

class JourneyCreatorTest {

    @Test
    void createsJourneys() {
        var journeyId = aRandomId();
        var bikeId = new BikeId(aRandomId());
        var position = aRandomPosition();
        var journeyRepository = new JourneyRepositoryDouble();
        var idGenerator = new IdGeneratorDouble();
        idGenerator.configure(journeyId);
        var journeyCreator = new JourneyCreator(journeyRepository, idGenerator);

        journeyCreator.create(bikeId, position);

        var allJourneys = journeyRepository.findAllByBike(bikeId);
        assertThat(allJourneys).usingRecursiveFieldByFieldElementComparator()
                .containsExactly(new Journey(new JourneyId(journeyId), bikeId, position));
    }
}
