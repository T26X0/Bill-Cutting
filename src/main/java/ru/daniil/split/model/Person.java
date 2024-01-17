package ru.daniil.split.model;

import java.util.ArrayList;
import java.util.List;

public class Person {

    public final String name;
    private final List<Integer> allSpends = new ArrayList<>();

    public Person(String name) {
        this.name = name;
    }

    public List<Integer> getAllSpends() {
        return new ArrayList<>(allSpends);
    }

    public void addSpend(Integer newSpend) {
        this.allSpends.add(newSpend);
    }
}
