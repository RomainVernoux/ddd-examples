package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.standard.Zone;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static fr.vernoux.rentabike.domain.bike.BikeStatus.LOCKED;
import static java.util.Objects.requireNonNull;

public class BikeSearcher {

    private final BikeRepository bikeRepository;

    public BikeSearcher(BikeRepository bikeRepository) {
        this.bikeRepository = bikeRepository;
    }

    @Transactional(readOnly = true)
    public List<Bike> getAllAvailableInZone(Zone zone) {
        requireNonNull(zone);
        return bikeRepository.findAllByStatusInZone(LOCKED, zone);
    }
}
