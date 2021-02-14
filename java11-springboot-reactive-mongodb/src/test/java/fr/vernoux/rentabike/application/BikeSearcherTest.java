package fr.vernoux.rentabike.application;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import fr.vernoux.rentabike.domain.standard.Zone;
import fr.vernoux.rentabike.doubles.BikeRepositoryDouble;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class BikeSearcherTest {

    @Test
    void findsAvailableBikesNearby() {
        BikeRepository bikeRepository = new BikeRepositoryDouble();
        BikeSearcher bikeSearcher = new BikeSearcher(bikeRepository);
        Bike bikeNearby = new Bike(new BikeId("123"), new Position(2.33d, 48.88d));
        bikeRepository.save(bikeNearby).block();

        Flux<Bike> bikes = bikeSearcher.getAllAvailableInZone(new Zone(48.80d, 2.20d, 380));

        StepVerifier.create(bikes)
                .expectNext(bikeNearby)
                .verifyComplete();
    }
}
