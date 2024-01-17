package ru.daniil.split.dao;

import ru.daniil.split.model.Person;

import java.util.*;

public class InMemoryPersonDAO implements PersonDAO {
    private final Map<String, Person> allPerson;

    public InMemoryPersonDAO(Map<String, Person> allPerson) {
        this.allPerson = allPerson;
    }

    @Override
    public void upsert(Person person) {
        allPerson.put(
                person.name,
                person
        );
    }

    @Override
    public Set<Person> getAll() {
        return new HashSet<>(allPerson.values());
    }

    @Override
    public Person get(String name) {
        return allPerson.get(name);
    }

    @Override
    public void remove(String name) {
        allPerson.remove(name);
    }
}
