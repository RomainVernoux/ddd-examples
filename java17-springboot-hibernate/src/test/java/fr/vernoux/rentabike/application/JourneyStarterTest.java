package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.doubles.IdGeneratorDouble;
import fr.vernoux.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static fr.vernoux.rentabike.utils.TestUtils.aRandomPosition;
import static org.assertj.core.api.Assertions.assertThat;

class JourneyStarterTest {

    @Test
    void createsJourneys() {
        var journeyId = aRandomId();
        var bikeId = new BikeId(aRandomId());
        var position = aRandomPosition();
        var journeyRepository = new JourneyRepositoryDouble();
        var idGenerator = new IdGeneratorDouble();
        idGenerator.configure(journeyId);
        var journeyStarter = new JourneyStarter(journeyRepository, idGenerator);

        journeyStarter.create(bikeId, position);

        var allJourneys = journeyRepository.findAllByBike(bikeId);
        assertThat(allJourneys).hasSize(1);
        assertThat(allJourneys.get(0).getBikeId()).isEqualTo(bikeId);
        assertThat(allJourneys.get(0).getStartPosition()).isEqualTo(position);
    }
}
