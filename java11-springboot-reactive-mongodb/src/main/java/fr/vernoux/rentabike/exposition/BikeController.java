package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.BikeScanner;
import fr.vernoux.rentabike.application.BikeSearcher;
import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.UnknownBikeException;
import fr.vernoux.rentabike.domain.standard.Zone;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/bikes")
public class BikeController {

    private final BikeSearcher bikeSearcher;
    private final BikeScanner bikeScanner;

    public BikeController(BikeSearcher bikeSearcher, BikeScanner bikeScanner) {
        this.bikeSearcher = bikeSearcher;
        this.bikeScanner = bikeScanner;
    }

    @GetMapping
    public Flux<BikeDTO> getAllAvailableNear(@RequestParam double lat, @RequestParam double lng, @RequestParam long radius) {
        return bikeSearcher.getAllAvailableInZone(new Zone(lat, lng, radius))
                .map(this::toDTO);
    }

    @PostMapping("/{id}/scan")
    public Mono<Void> scan(@PathVariable String id) {
        return bikeScanner.scan(new BikeId(id)).onErrorResume(e -> {
            if (e instanceof UnknownBikeException) {
                return Mono.error(new ResponseStatusException(NOT_FOUND, "No bike found with id: " + id));
            }
            return Mono.error(e);
        });
    }

    private BikeDTO toDTO(Bike bike) {
        return new BikeDTO(bike.getId().getValue(), bike.getPosition());
    }
}
