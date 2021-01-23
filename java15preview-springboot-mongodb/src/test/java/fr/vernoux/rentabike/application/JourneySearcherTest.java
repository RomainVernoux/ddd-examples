package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import fr.vernoux.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JourneySearcherTest {

    @Test
    void findsJourneysInProgressByBikeId() {
        BikeId bikeId = new BikeId("2");
        Position position = new Position(48d, 2d);
        Journey journey = new Journey(new JourneyId("456"), bikeId, position);
        JourneyRepository journeyRepository = new JourneyRepositoryDouble();
        journeyRepository.save(journey);
        JourneySearcher journeySearcher = new JourneySearcher(journeyRepository);

        List<Journey> journeys = journeySearcher.searchJourneyInProgressForBike(journey.getBikeId());

        assertThat(journeys).containsExactly(journey);
    }
}
