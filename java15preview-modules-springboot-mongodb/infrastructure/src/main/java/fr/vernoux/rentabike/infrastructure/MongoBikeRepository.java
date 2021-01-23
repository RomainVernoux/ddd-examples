package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.*;
import fr.vernoux.rentabike.domain.standard.Zone;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoBikeRepository implements BikeRepository {

    private final MongoBikeDAO dao;

    public MongoBikeRepository(MongoBikeDAO dao, MongoOperations mongoOperations) {
        this.dao = dao;
        mongoOperations.indexOps(Bike.class).ensureIndex(new GeospatialIndex("position"));
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
        Point point = new Point(zone.lat(), zone.lng());
        Distance distance = new Distance(zone.radius() / 1000d, Metrics.KILOMETERS);
        return dao.findAllByStatusAndPositionNear(status, point, distance);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}
