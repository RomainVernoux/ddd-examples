package fr.vernoux.rentabike.infrastructure;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.List;
import java.util.UUID;

@Entity
public class JpaJourney {

    @Id
    private UUID id;
    private UUID bikeId;
    @ElementCollection
    private List<JpaPosition> positions;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getBikeId() {
        return bikeId;
    }

    public void setBikeId(UUID bikeId) {
        this.bikeId = bikeId;
    }

    public List<JpaPosition> getPositions() {
        return positions;
    }

    public void setPositions(List<JpaPosition> positions) {
        this.positions = positions;
    }
}
