package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.*;
import fr.vernoux.rentabike.domain.standard.Zone;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JpaBikeRepository implements BikeRepository {

    private final JpaBikeDAO dao;

    public JpaBikeRepository(JpaBikeDAO dao) {
        this.dao = dao;
    }

    @Override
    public void save(Bike bike) {
        dao.save(bike);
    }

    @Override
    public Bike getById(BikeId id) throws UnknownBikeException {
        return dao.findById(id).orElseThrow(UnknownBikeException::new);
    }

    public List<Bike> findAllByStatusInZone(BikeStatus status, Zone zone) {
        Point point = new Point(zone.getLat(), zone.getLng());
        Distance distance = new Distance(zone.getRadius() / 1000d, Metrics.KILOMETERS);
        return dao.findAllByStatusAndPositionNear(status, point, distance);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}
