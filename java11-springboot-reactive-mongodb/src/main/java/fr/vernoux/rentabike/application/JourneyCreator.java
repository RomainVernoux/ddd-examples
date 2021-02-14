package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.IdGenerator;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import reactor.core.publisher.Mono;

import static java.util.Objects.requireNonNull;

public class JourneyCreator {

    private final JourneyRepository journeyRepository;
    private final IdGenerator idGenerator;

    public JourneyCreator(JourneyRepository journeyRepository, IdGenerator idGenerator) {
        this.journeyRepository = journeyRepository;
        this.idGenerator = idGenerator;
    }

    public Mono<Void> create(BikeId bikeId, Position startPosition) {
        requireNonNull(bikeId);
        requireNonNull(startPosition);
        return idGenerator.uuid()
                .map(id -> new Journey(new JourneyId(id), bikeId, startPosition))
                .flatMap(journeyRepository::save);
    }
}
