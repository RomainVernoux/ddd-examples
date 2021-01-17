package fr.vernoux.rentabike.domain.bike;

import fr.vernoux.rentabike.domain.standard.Zone;

import java.util.List;

public interface BikeRepository {

    void save(Bike bike);

    Bike getById(BikeId id) throws UnknownBikeException;

    List<Bike> findAllByStatusInZone(BikeStatus status, Zone zone);

    void deleteAll();
}
