package fr.vernoux.rentabike.acceptance;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureWebTestClient
class JourneyStartTest {

    @Autowired
    WebTestClient testClient;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    JourneyRepository journeyRepository;

    @BeforeEach
    void beforeEach() {
        bikeRepository.deleteAll().block();
        journeyRepository.deleteAll().block();
    }

    @Test
    void scanningABikeStartsAJourney() {
        String bike1Id = "123";
        double bike1Lng = 48.88d;
        double bike1Lat = 2.33d;
        bikeRepository.save(new Bike(new BikeId(bike1Id), new Position(bike1Lat, bike1Lng))).block();

        testClient.post()
                .uri("/bikes/" + bike1Id + "/scan")
                .exchange()
                .expectStatus().isOk();

        testClient.get()
                .uri("/journeys?bikeId=" + bike1Id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").value(hasSize(1))
                .jsonPath("$[0].startPosition.lat").isEqualTo(bike1Lat)
                .jsonPath("$[0].startPosition.lng").isEqualTo(bike1Lng);
    }
}
