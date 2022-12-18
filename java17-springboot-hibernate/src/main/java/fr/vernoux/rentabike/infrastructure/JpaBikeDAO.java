package fr.vernoux.rentabike.infrastructure;

import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public interface JpaBikeDAO extends JpaRepository<JpaBike, UUID> {

    // One should probably look into support of geo index by the target database instead of computing the distance manually like below.
    @Query("SELECT bike FROM JpaBike bike WHERE bike.status = :status AND 3671 * function('acos', function('sin', function('radians', :#{#point.x})) * function('sin', function('radians', bike.position.lat)) + function('cos', function('radians', :#{#point.x})) * function('cos', function('radians', bike.position.lat)) * function('cos', function('radians', bike.position.lng)-function('radians', :#{#point.y}))) < :#{#distance.value}")
    List<JpaBike> findAllByStatusAndPositionNear(String status, Point point, Distance distance);
}
