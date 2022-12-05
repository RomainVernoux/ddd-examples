package fr.vernoux.rentabike.acceptance;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.spatial.Position;
import fr.vernoux.rentabike.infrastructure.MongoBikeDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static fr.vernoux.rentabike.utils.TestUtils.aRandomId;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
class AvailableBikesTest extends MongoTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    MongoBikeDAO bikeDAO;

    @BeforeEach
    void beforeEach() {
        bikeDAO.deleteAll();
    }

    @Test
    void aUserCanSeeAllAvailableBikesNearby() throws Exception {
        var bikeNearby = aBikeNearby();
        var bikeFarAway = aBikeFarAway();
        bikeRepository.save(bikeNearby);
        bikeRepository.save(bikeFarAway);

        mockMvc.perform(get("/bikes?" + aroundMeQueryParams()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(bikeNearby.getId().value().toString()))
                .andExpect(jsonPath("$[0].position.lat").value(bikeNearby.getPosition().lat()))
                .andExpect(jsonPath("$[0].position.lng").value(bikeNearby.getPosition().lng()));
    }

    @Test
    void aRentedBikeDoesNotAppearInAvailableBikesNearby() throws Exception {
        var bikeNearby = aBikeNearby();
        bikeRepository.save(bikeNearby);

        mockMvc.perform(post("/bikes/" + bikeNearby.getId().value() + "/scan"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/bikes?" + aroundMeQueryParams()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void aReturnedBikeAppearsInAvailableBikesNearby() throws Exception {
        var bike = aBikeFarAway();
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
        return "lat=48.88000&lng=2.33000&radius=450";
    }

    private Bike aBikeNearby() {
        return new Bike(new BikeId(aRandomId()), closeToMe());
    }

    private Bike aBikeFarAway() {
        return new Bike(new BikeId(aRandomId()), farAway());
    }

    private Position closeToMe() {
        return new Position(48.88001d, 2.33001d);
    }

    private Position farAway() {
        return new Position(40d, 0d);
    }
}
