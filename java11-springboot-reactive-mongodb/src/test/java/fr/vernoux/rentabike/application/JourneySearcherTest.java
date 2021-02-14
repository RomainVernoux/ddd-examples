package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import fr.vernoux.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class JourneySearcherTest {

    @Test
    void findsJourneysInProgressByBikeId() {
        BikeId bikeId = new BikeId("2");
        Position position = new Position(48d, 2d);
        Journey journey = new Journey(new JourneyId("456"), bikeId, position);
        JourneyRepository journeyRepository = new JourneyRepositoryDouble();
        journeyRepository.save(journey).block();
        JourneySearcher journeySearcher = new JourneySearcher(journeyRepository);

        StepVerifier.create(journeySearcher.searchJourneyInProgressForBike(journey.getBikeId()))
                .expectNext(journey)
                .verifyComplete();
    }
}
