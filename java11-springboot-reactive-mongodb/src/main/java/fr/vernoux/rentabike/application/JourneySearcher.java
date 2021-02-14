package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import reactor.core.publisher.Flux;

import static java.util.Objects.requireNonNull;

public class JourneySearcher {

    private final JourneyRepository journeyRepository;

    public JourneySearcher(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    public Flux<Journey> searchJourneyInProgressForBike(BikeId bikeId) {
        requireNonNull(bikeId);
        return journeyRepository.findAllByBike(bikeId);
    }
}
