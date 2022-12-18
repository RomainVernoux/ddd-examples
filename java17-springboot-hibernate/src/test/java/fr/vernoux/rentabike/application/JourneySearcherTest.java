package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static fr.vernoux.rentabike.utils.TestUtils.aRandomPosition;
import static org.assertj.core.api.Assertions.assertThat;

class JourneySearcherTest {

    @Test
    void findsJourneysInProgressByBikeId() {
        var bikeId = new BikeId(aRandomId());
        var journey = Journey.start(new JourneyId(aRandomId()), bikeId, aRandomPosition());
        var journeyRepository = new JourneyRepositoryDouble();
        journeyRepository.save(journey);
        var journeySearcher = new JourneySearcher(journeyRepository);

        var journeys = journeySearcher.searchJourneyInProgressForBike(journey.getBikeId());

        assertThat(journeys).containsExactly(journey);
    }
}
