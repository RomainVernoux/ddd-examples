package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.*;
import fr.vernoux.rentabike.domain.spatial.Position;
import fr.vernoux.rentabike.domain.spatial.Zone;
import jakarta.persistence.EntityManager;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaBikeRepository implements BikeRepository {

    private final EntityManager entityManager;
    private final JpaBikeDAO dao;

    public JpaBikeRepository(EntityManager entityManager, JpaBikeDAO dao) {
        this.entityManager = entityManager;
        this.dao = dao;
    }

    @Override
    public void save(Bike bike) {
        var jpaBike = toJpaEntity(bike); // Map the domain aggregate to a JPA entity
        entityManager.merge(jpaBike); // Merge this JPA entity into the JPA entity with same ID managed by JPA.
        dao.save(jpaBike);
    }

    @Override
    public Bike getById(BikeId id) throws UnknownBikeException {
        return dao.findById(id.value())
                .map(JpaBikeRepository::fromJpaEntity)
                .orElseThrow(UnknownBikeException::new);
    }

    @Override
    public List<Bike> findAllByStatusInZone(BikeStatus status, Zone zone) {
        var point = new Point(zone.lat(), zone.lng());
        var distance = new Distance(zone.radius() / 1000d, Metrics.KILOMETERS);
        return dao.findAllByStatusAndPositionNear(status.name(), point, distance).stream()
                .map(JpaBikeRepository::fromJpaEntity)
                .toList();
    }

    private static JpaBike toJpaEntity(Bike bike) {
        var jpaBike = new JpaBike();
        jpaBike.setId(bike.getId().value());
        jpaBike.setStatus(bike.getStatus().name());
        jpaBike.setPosition(toJpaEntity(bike.getPosition()));
        return jpaBike;
    }

    private static JpaPosition toJpaEntity(Position position) {
        var jpaPosition = new JpaPosition();
        jpaPosition.setLat(position.lat());
        jpaPosition.setLng(position.lng());
        return jpaPosition;
    }

    private static Bike fromJpaEntity(JpaBike jpaBike) {
        var bikeId = new BikeId(jpaBike.getId());
        var position = fromJpaEntity(jpaBike.getPosition());
        var status = BikeStatus.valueOf(jpaBike.getStatus());
        return new Bike(bikeId, position, status);
    }

    private static Position fromJpaEntity(JpaPosition jpaPosition) {
        return new Position(jpaPosition.getLat(), jpaPosition.getLng());
    }
}
