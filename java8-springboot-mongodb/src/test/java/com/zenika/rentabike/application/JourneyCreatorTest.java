package com.zenika.rentabike.application;

import com.zenika.rentabike.domain.bike.BikeId;
import com.zenika.rentabike.domain.journey.Journey;
import com.zenika.rentabike.domain.journey.JourneyId;
import com.zenika.rentabike.domain.journey.JourneyRepository;
import com.zenika.rentabike.domain.standard.Position;
import com.zenika.rentabike.doubles.IdGeneratorDouble;
import com.zenika.rentabike.doubles.JourneyRepositoryDouble;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JourneyCreatorTest {

    @Test
    void createsJourneys() {
        String uuid = "1";
        BikeId bikeId = new BikeId("2");
        Position position = new Position(48d, 2d);
        JourneyRepository journeyRepository = new JourneyRepositoryDouble();
        IdGeneratorDouble idGenerator = new IdGeneratorDouble();
        idGenerator.configure(uuid);
        JourneyCreator journeyCreator = new JourneyCreator(journeyRepository, idGenerator);

        journeyCreator.create(bikeId, position);

        List<Journey> allJourneys = journeyRepository.findAll();
        assertThat(allJourneys).usingFieldByFieldElementComparator()
                .containsExactly(new Journey(new JourneyId(uuid), bikeId, position));
    }
}
