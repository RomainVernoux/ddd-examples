package fr.vernoux.rentabike.doubles;

import fr.vernoux.rentabike.domain.IdGenerator;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import static java.util.Arrays.asList;

public class IdGeneratorDouble implements IdGenerator {

    private Queue<String> values;

    @Override
    public String uuid() {
        try {
            return values.remove();
        } catch (NoSuchElementException e) {
            throw new IllegalStateException("No more values to return");
        }
    }

    public void configure(String... valuesToGenerate) {
        this.values = new LinkedList<>(asList(valuesToGenerate));
    }
}
