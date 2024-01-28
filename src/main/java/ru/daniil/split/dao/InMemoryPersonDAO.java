package ru.daniil.split.dao;

import ru.daniil.split.exceptions.DuplicateResourceException;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class InMemoryPersonDAO implements PersonDAO {

    BigDecimal allSpends = BigDecimal.ZERO;
    Set<String> allPerson = new HashSet<>();


    @Override
    public void insert(String person) throws DuplicateResourceException {
        if (allPerson.contains(person)) {
            throw new DuplicateResourceException("Such a record already exists");
        }
        allPerson.add(person);
    }

    @Override
    public void addSpend(int newSpend) {
        allSpends = allSpends.add(new BigDecimal(newSpend));
    }

    @Override
    public int getPersonCount() {
        return allPerson.size();
    }

    @Override
    public Set<String> getAllPersonNames() {
        return new HashSet<>(allPerson);
    }

    @Override
    public int getSpends() {
        return allSpends.intValue();
    }

    @Override
    public void reset() {
        allSpends = BigDecimal.ZERO;
        allPerson = new HashSet<>();
    }
}
