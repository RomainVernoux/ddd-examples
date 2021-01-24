package fr.vernoux.rentabike.infrastructure;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeStatus;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface JpaBikeDAO extends JpaRepository<Bike, BikeId> {

    // One should probably look into support of geo index by the target database instead of computing the distance manually like below.
    @Query("SELECT bike FROM Bike bike WHERE bike.status = :bikeStatus AND 3671 * function('acos', function('sin', function('radians', :#{#point.x})) * function('sin', function('radians', bike.position.lat)) + function('cos', function('radians', :#{#point.x})) * function('cos', function('radians', bike.position.lat)) * function('cos', function('radians', bike.position.lng)-function('radians', :#{#point.y}))) < :#{#distance.value}")
    List<Bike> findAllByStatusAndPositionNear(BikeStatus bikeStatus, Point point, Distance distance);
}
