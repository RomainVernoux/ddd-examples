package com.zenika.dddexample.rentabike.exposition;

import com.zenika.dddexample.rentabike.application.JourneySearcher;
import com.zenika.dddexample.rentabike.domain.bike.BikeId;
import com.zenika.dddexample.rentabike.domain.journey.Journey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/journeys")
public class JourneyController {

    private final JourneySearcher journeySearcher;

    public JourneyController(JourneySearcher journeySearcher) {
        this.journeySearcher = journeySearcher;
    }

    @GetMapping
    public List<JourneyDTO> searchInProgressForBike(@RequestParam String bikeId) {
        return journeySearcher.searchJourneyInProgressForBike(new BikeId(bikeId)).stream()
                .map(this::toDTO)
                .collect(toList());
    }

    private JourneyDTO toDTO(Journey journey) {
        return new JourneyDTO(journey.getId().getValue(), journey.getBikeId().getValue(),
                journey.getStartPosition(), journey.getEndPosition());
    }
}
