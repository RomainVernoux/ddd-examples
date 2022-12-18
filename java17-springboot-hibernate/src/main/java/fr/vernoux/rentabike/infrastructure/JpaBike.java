package fr.vernoux.rentabike.infrastructure;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class JpaBike {

    @Id
    private UUID id;
    @Embedded
    private JpaPosition position;
    private String status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public JpaPosition getPosition() {
        return position;
    }

    public void setPosition(JpaPosition position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
