package ru.daniil.split.dao;

import ru.daniil.split.model.Person;

import java.util.Set;

public interface PersonDAO {

    void upsert(Person person);

    Set<Person> getAll();

    Person get(String name);

    void remove(String name);
}
