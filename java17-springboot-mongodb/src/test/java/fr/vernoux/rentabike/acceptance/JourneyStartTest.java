package fr.vernoux.rentabike.acceptance;

import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.BikeRepository;
import fr.vernoux.rentabike.domain.journey.JourneyRepository;
import fr.vernoux.rentabike.domain.spatial.Position;
import fr.vernoux.rentabike.infrastructure.MongoBikeDAO;
import fr.vernoux.rentabike.infrastructure.MongoJourneyDAO;
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
class JourneyStartTest extends MongoTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    JourneyRepository journeyRepository;

    @Autowired
    MongoBikeDAO bikeDAO;

    @Autowired
    MongoJourneyDAO journeyDAO;

    @BeforeEach
    void beforeEach() {
        bikeDAO.deleteAll();
        journeyDAO.deleteAll();
    }

    @Test
    void scanningABikeStartsAJourney() throws Exception {
        var bikeId = aRandomId();
        var bikeLat = 48.88d;
        var bikeLng = 2.33d;
        var bike = Bike.enroll(new BikeId(bikeId), new Position(bikeLat, bikeLng));
        bikeRepository.save(bike);

        mockMvc.perform(post("/bikes/" + bikeId + "/scan"));

        mockMvc.perform(get("/journeys?bikeId=" + bikeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].startPosition.lat").value(bikeLat))
                .andExpect(jsonPath("$[0].startPosition.lng").value(bikeLng));
    }
}
