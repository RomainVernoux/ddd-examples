package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.bike.*;
import fr.vernoux.rentabike.domain.standard.Zone;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BikeRepositoryDouble implements BikeRepository {

    private final Map<BikeId, Bike> database = new HashMap<>();

    @Override
    public void save(Bike bike) {
        database.put(bike.getId(), bike);
    }

    @Override
    public Bike getById(BikeId id) throws UnknownBikeException {
        Bike bike = database.get(id);
        if (bike == null) {
            throw new UnknownBikeException();
        }
        return bike;
    }

    @Override
    public List<Bike> findAllByStatusInZone(BikeStatus status, Zone zone) {
        return database.values().stream()
                .filter(bike -> status.equals(bike.getStatus()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        database.clear();
    }
}
