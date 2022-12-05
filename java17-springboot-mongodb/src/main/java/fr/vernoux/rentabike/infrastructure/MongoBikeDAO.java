package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeStatus;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MongoBikeDAO extends MongoRepository<Bike, BikeId> {

    List<Bike> findAllByStatusAndPositionNear(BikeStatus bikeStatus, Point point, Distance distance);
}
