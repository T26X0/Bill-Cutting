package ru.daniil.split.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Model {

    private Set<String> personNames = new HashSet<>();
    private BigDecimal allSpends;

    public Set<String> getPersonNames() {
        return new HashSet<>(personNames);
    }

    public void addNewPerson(String personName) {
        this.personNames.add(personName);
    }

    public BigDecimal getAllSpends() {
        return allSpends;
    }

    public void addNewSpend(BigDecimal allSpends) {
        this.allSpends = allSpends;
    }
}
