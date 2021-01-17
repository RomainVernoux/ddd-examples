package fr.vernoux.rentabike.main;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.standard.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RentABikeApplication.class)
@AutoConfigureMockMvc
class AvailableBikesTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BikeRepository bikeRepository;

    @BeforeEach
    void beforeEach() {
        bikeRepository.deleteAll();
    }

    @Test
    void aUserCanSeeAllAvailableBikesNearby() throws Exception {
        Bike bikeNearby = aBikeNearby();
        Bike bikeFarAway = aBikeFarAway();
        bikeRepository.save(bikeNearby);
        bikeRepository.save(bikeFarAway);

        mockMvc.perform(get("/bikes?" + aroundMeQueryParams()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(bikeNearby.getId().value()))
                .andExpect(jsonPath("$[0].position.lat").value(bikeNearby.getPosition().lat()))
                .andExpect(jsonPath("$[0].position.lng").value(bikeNearby.getPosition().lng()));
    }

    @Test
    void aRentedBikeDoesNotAppearInAvailableBikesNearby() throws Exception {
        Bike bikeNearby = aBikeNearby();
        bikeRepository.save(bikeNearby);

        mockMvc.perform(post("/bikes/" + bikeNearby.getId().value() + "/scan"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bikes?" + aroundMeQueryParams()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void aReturnedBikeAppearsInAvailableBikesNearby() throws Exception {
        Bike bike = aBikeFarAway();
        bike.scan();
        bike.move(closeToMe());
        bikeRepository.save(bike);

        mockMvc.perform(post("/bikes/" + bike.getId().value() + "/scan"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bikes?" + aroundMeQueryParams()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].position.lat").value(bike.getPosition().lat()))
                .andExpect(jsonPath("$[0].position.lng").value(bike.getPosition().lng()));
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
