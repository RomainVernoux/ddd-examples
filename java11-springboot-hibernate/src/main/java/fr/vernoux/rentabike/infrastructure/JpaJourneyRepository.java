package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaJourneyRepository implements JourneyRepository {

    private final JpaJourneyDAO dao;

    public JpaJourneyRepository(JpaJourneyDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(Journey journey) {
        dao.save(journey);
    }

    @Override
    public List<Journey> findAll() {
        return dao.findAll();
    }

    @Override
    public List<Journey> findAllByBike(BikeId bikeId) {
        return dao.findAllByBikeId(bikeId);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}
