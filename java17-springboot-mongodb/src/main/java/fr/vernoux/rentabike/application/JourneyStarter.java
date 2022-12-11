package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.IdGenerator;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.spatial.Position;

import static java.util.Objects.requireNonNull;

public class JourneyStarter {

    private final JourneyRepository journeyRepository;
    private final IdGenerator idGenerator;

    public JourneyStarter(JourneyRepository journeyRepository, IdGenerator idGenerator) {
        this.journeyRepository = journeyRepository;
        this.idGenerator = idGenerator;
    }

    public void create(BikeId bikeId, Position startPosition) {
        requireNonNull(bikeId);
        requireNonNull(startPosition);
        var journeyId = new JourneyId(idGenerator.next());
        var journey = Journey.start(journeyId, bikeId, startPosition);
        journeyRepository.save(journey);
    }
}
