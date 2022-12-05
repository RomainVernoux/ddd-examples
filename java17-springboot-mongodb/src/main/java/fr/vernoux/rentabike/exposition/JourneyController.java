package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.JourneySearcher;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/journeys")
public class JourneyController {

    private final JourneySearcher journeySearcher;

    public JourneyController(JourneySearcher journeySearcher) {
        this.journeySearcher = journeySearcher;
    }

    @GetMapping
    public List<JourneyDTO> searchInProgressForBike(@RequestParam String bikeId) {
        return journeySearcher.searchJourneyInProgressForBike(new BikeId(UUID.fromString(bikeId))).stream()
                .map(this::toDTO)
                .toList();
    }

    private JourneyDTO toDTO(Journey journey) {
        return new JourneyDTO(journey.getId().value().toString(), journey.getBikeId().value().toString(),
                journey.getStartPosition(), journey.getEndPosition());
    }
}
