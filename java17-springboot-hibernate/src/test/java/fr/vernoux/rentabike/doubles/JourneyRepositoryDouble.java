package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class JourneyRepositoryDouble implements JourneyRepository {

    private final Map<JourneyId, Journey> database = new HashMap<>();

    @Override
    public void save(Journey bike) {
        database.put(bike.getId(), bike);
    }

    @Override
    public List<Journey> findAllByBike(BikeId bikeId) {
        return database.values().stream()
                .filter(journey -> journey.getBikeId().equals(bikeId))
                .collect(toList());
    }
}
