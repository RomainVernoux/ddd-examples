package fr.vernoux.rentabike.acceptance;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureWebTestClient
class AvailableBikesTest {

    @Autowired
    WebTestClient testClient;

    @Autowired
    BikeRepository bikeRepository;

    @BeforeEach
    void beforeEach() {
        bikeRepository.deleteAll().block();
    }

    @Test
    void aUserCanSeeAllAvailableBikesNearby() {
        Bike bikeNearby = aBikeNearby();
        Bike bikeFarAway = aBikeFarAway();
        bikeRepository.save(bikeNearby).block();
        bikeRepository.save(bikeFarAway).block();

        testClient.get()
                .uri("/bikes?" + aroundMeQueryParams())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").value(hasSize(1))
                .jsonPath("$[0].id").isEqualTo(bikeNearby.getId().getValue())
                .jsonPath("$[0].position.lat").isEqualTo(bikeNearby.getPosition().getLat())
                .jsonPath("$[0].position.lng").isEqualTo(bikeNearby.getPosition().getLng());
    }

    @Test
    void aRentedBikeDoesNotAppearInAvailableBikesNearby() {
        Bike bikeNearby = aBikeNearby();
        bikeRepository.save(bikeNearby).block();

        testClient.post()
                .uri("/bikes/" + bikeNearby.getId().getValue() + "/scan")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();

        testClient.get()
                .uri("/bikes?" + aroundMeQueryParams())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").value(hasSize(0));
    }

    @Test
    void aReturnedBikeAppearsInAvailableBikesNearby() {
        Bike bike = aBikeFarAway();
        bike.scan();
        bike.move(closeToMe());
        bikeRepository.save(bike).block();

        testClient.post()
                .uri("/bikes/" + bike.getId().getValue() + "/scan")
                .exchange()
                .expectStatus().isOk();

        testClient.get()
                .uri("/bikes?" + aroundMeQueryParams())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").value(hasSize(1))
                .jsonPath("$[0].position.lat").isEqualTo(bike.getPosition().getLat())
                .jsonPath("$[0].position.lng").isEqualTo(bike.getPosition().getLng());
    }

    private String aroundMeQueryParams() {
        return "lng=48.88000&lat=2.33000&radius=450";
    }

    private Position closeToMe() {
        return new Position(2.33001d, 48.88001d);
    }

    private Bike aBikeNearby() {
        return new Bike(new BikeId("1"), closeToMe());
    }

    private Bike aBikeFarAway() {
        return new Bike(new BikeId("2"), new Position(0d, 0d));
    }
}
