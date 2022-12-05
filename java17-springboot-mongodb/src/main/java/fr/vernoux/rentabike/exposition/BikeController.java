package fr.vernoux.rentabike.exposition;

import fr.vernoux.rentabike.application.BikeScanner;
import fr.vernoux.rentabike.application.BikeSearcher;
import fr.vernoux.rentabike.domain.bike.Bike;
import fr.vernoux.rentabike.domain.bike.BikeId;
import fr.vernoux.rentabike.domain.bike.UnknownBikeException;
import fr.vernoux.rentabike.domain.spatial.Zone;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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
    public List<BikeDTO> getAllAvailableNear(@RequestParam double lat, @RequestParam double lng, @RequestParam long radius) {
        return bikeSearcher.getAllAvailableInZone(new Zone(lat, lng, radius)).stream()
                .map(this::toDTO)
                .toList();
    }

    @PostMapping("/{id}/scan")
    public void scan(@PathVariable String id) {
        try {
            bikeScanner.scan(new BikeId(UUID.fromString(id)));
        } catch (UnknownBikeException e) {
            throw new ResponseStatusException(NOT_FOUND, "No bike found with id: " + id);
        }
    }

    private BikeDTO toDTO(Bike bike) {
        return new BikeDTO(bike.getId().value().toString(), bike.getPosition());
    }
}
