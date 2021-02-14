package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.*;
import fr.vernoux.rentabike.domain.standard.Zone;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class MongoBikeRepository implements BikeRepository {

    private final MongoBikeDAO dao;

    public MongoBikeRepository(MongoBikeDAO dao, ReactiveMongoOperations mongoOperations) {
        this.dao = dao;
        mongoOperations.indexOps(Bike.class).ensureIndex(new GeospatialIndex("position")).subscribe();
    }

    @Override
    public Mono<Void> save(Bike bike) {
        return dao.save(bike).then();
    }

    @Override
    public Mono<Bike> getById(BikeId id) {
        return dao.findById(id).switchIfEmpty(Mono.error(new UnknownBikeException()));
    }

    public Flux<Bike> findAllByStatusInZone(BikeStatus status, Zone zone) {
        Point point = new Point(zone.getLat(), zone.getLng());
        Distance distance = new Distance(zone.getRadius() / 1000d, Metrics.KILOMETERS);
        return dao.findAllByStatusAndPositionNear(status, point, distance);
    }

    @Override
    public Mono<Void> deleteAll() {
        return dao.deleteAll();
    }
}
