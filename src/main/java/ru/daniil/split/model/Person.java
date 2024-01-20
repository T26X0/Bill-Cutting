package ru.daniil.split.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Person {

    public final String name;
    private final List<BigDecimal> allSpends = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public List<BigDecimal> getAllSpends() {
        return new ArrayList<>(allSpends);
    }

    public void addSpend(BigDecimal newSpend) {
        this.allSpends.add(newSpend);
    }
}
