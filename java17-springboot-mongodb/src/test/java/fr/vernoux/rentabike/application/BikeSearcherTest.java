package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.spatial.Position;
import fr.vernoux.rentabike.domain.spatial.Zone;
import fr.vernoux.rentabike.doubles.BikeRepositoryDouble;
import org.junit.jupiter.api.Test;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static org.assertj.core.api.Assertions.assertThat;

class BikeSearcherTest {

    @Test
    void findsAvailableBikesNearby() {
        var bikeRepository = new BikeRepositoryDouble();
        var bikeSearcher = new BikeSearcher(bikeRepository);
        var bikeNearby = Bike.enroll(new BikeId(aRandomId()), new Position(48.88d, 2.33d));
        bikeRepository.save(bikeNearby);

        var bikes = bikeSearcher.getAllAvailableInZone(new Zone(48.80d, 2.20d, 380));

        assertThat(bikes).containsExactly(bikeNearby);
    }
}
