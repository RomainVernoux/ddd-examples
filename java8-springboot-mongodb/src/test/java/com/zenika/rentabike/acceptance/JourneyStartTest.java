package com.zenika.rentabike.acceptance;

import com.zenika.rentabike.domain.bike.Bike;
import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.bike.BikeRepository;
import com.zenika.rentabike.domain.journey.JourneyRepository;
import com.zenika.rentabike.domain.standard.Position;
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

@SpringBootTest
@AutoConfigureMockMvc
class JourneyStartTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    JourneyRepository journeyRepository;

    @BeforeEach
    void beforeEach() {
        bikeRepository.deleteAll();
        journeyRepository.deleteAll();
    }

    @Test
    void scanningABikeStartsAJourney() throws Exception {
        String bike1Id = "123";
        double bike1Lng = 48.88d;
        double bike1Lat = 2.33d;
        bikeRepository.save(new Bike(new BikeId(bike1Id), new Position(bike1Lat, bike1Lng)));

        mockMvc.perform(post("/bikes/" + bike1Id + "/scan"));

        mockMvc.perform(get("/journeys?bikeId=" + bike1Id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].startPosition.lat").value(bike1Lat))
                .andExpect(jsonPath("$[0].startPosition.lng").value(bike1Lng));
    }
}
