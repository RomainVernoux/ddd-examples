package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.IdGenerator;

import java.util.*;

public class IdGeneratorDouble implements IdGenerator {

    private Queue<UUID> values;

    @Override
    public UUID next() {
        try {
            return values.remove();
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("No more values to return");
        }
    }

    public void configure(UUID... valuesToGenerate) {
        this.values = new LinkedList<>(List.of(valuesToGenerate));
    }
}
