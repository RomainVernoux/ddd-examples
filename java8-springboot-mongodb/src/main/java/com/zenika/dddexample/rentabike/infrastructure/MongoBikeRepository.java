package com.zenika.dddexample.rentabike.infrastructure;

import com.zenika.dddexample.rentabike.domain.bike.*;
import com.zenika.dddexample.rentabike.domain.standard.Zone;
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
        Point point = new Point(zone.getLat(), zone.getLng());
        Distance distance = new Distance(zone.getRadius() / 1000d, Metrics.KILOMETERS);
        return dao.findAllByStatusAndPositionNear(status, point, distance);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }
}
