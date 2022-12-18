package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import fr.vernoux.rentabike.domain.journey.JourneyId;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.spatial.Position;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaJourneyRepository implements JourneyRepository {

    private final EntityManager entityManager;
    private final JpaJourneyDAO dao;

    public JpaJourneyRepository(EntityManager entityManager, JpaJourneyDAO dao) {
        this.entityManager = entityManager;
        this.dao = dao;
    }

    @Override
    public void save(Journey journey) {
        var jpaJourney = toJpaEntity(journey); // Map the domain aggregate to a JPA entity
        entityManager.merge(jpaJourney); // Merge this JPA entity into the JPA entity with same ID managed by JPA.
        dao.save(jpaJourney);
    }

    @Override
    public List<Journey> findAllByBike(BikeId bikeId) {
        return dao.findAllByBikeId(bikeId.value()).stream()
                .map(JpaJourneyRepository::fromJpaEntity)
                .toList();
    }

    private static JpaJourney toJpaEntity(Journey journey) {
        var jpaJourney = new JpaJourney();
        jpaJourney.setId(journey.getId().value());
        jpaJourney.setBikeId(journey.getBikeId().value());
        jpaJourney.setPositions(journey.getPositions().stream().map(JpaJourneyRepository::toJpaEntity).toList());
        return jpaJourney;
    }

    private static JpaPosition toJpaEntity(Position position) {
        var jpaPosition = new JpaPosition();
        jpaPosition.setLat(position.lat());
        jpaPosition.setLng(position.lng());
        return jpaPosition;
    }

    private static Journey fromJpaEntity(JpaJourney jpaJourney) {
        var journeyId = new JourneyId(jpaJourney.getId());
        var bikeId = new BikeId(jpaJourney.getBikeId());
        var positions = jpaJourney.getPositions().stream()
                .map(JpaJourneyRepository::fromJpaEntity)
                .toList();
        return new Journey(journeyId, bikeId, positions);
    }

    private static Position fromJpaEntity(JpaPosition jpaPosition) {
        return new Position(jpaPosition.getLat(), jpaPosition.getLng());
    }
}
