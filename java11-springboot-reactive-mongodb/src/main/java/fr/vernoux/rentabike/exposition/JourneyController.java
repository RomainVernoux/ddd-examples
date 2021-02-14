package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.JourneySearcher;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.journey.Journey;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/journeys")
public class JourneyController {

    private final JourneySearcher journeySearcher;

    public JourneyController(JourneySearcher journeySearcher) {
        this.journeySearcher = journeySearcher;
    }

    @GetMapping
    public Flux<JourneyDTO> searchInProgressForBike(@RequestParam String bikeId) {
        return journeySearcher.searchJourneyInProgressForBike(new BikeId(bikeId))
                .map(this::toDTO);
    }

    private JourneyDTO toDTO(Journey journey) {
        return new JourneyDTO(journey.getId().getValue(), journey.getBikeId().getValue(),
                journey.getStartPosition(), journey.getEndPosition());
    }
}
