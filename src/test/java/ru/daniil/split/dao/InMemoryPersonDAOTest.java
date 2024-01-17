package ru.daniil.split.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.daniil.split.model.Person;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryPersonDAOTest {

    private PersonDAO personDAO;
    private Map<String, Person> personMap;
    private Person person;
    private PersonDAO personDAO_WithOnePerson;
    private Map<String, Person> personMap_WithOnePerson;
    private Set<Person> personSet;

    @BeforeEach
    void setUp() {
        personMap = new HashMap<>();
        personDAO = new InMemoryPersonDAO(personMap);

        person = new Person("Maxim");
        personMap_WithOnePerson = new HashMap<>();
        personDAO_WithOnePerson = new InMemoryPersonDAO(personMap_WithOnePerson);

        personMap_WithOnePerson.put(person.name, person);
        personSet = new HashSet<>();
    }

    @Test
    void upsert() {
        Person person = new Person("Nikita");
        personDAO.upsert(person);
        assertTrue(personMap.containsKey("Nikita"));
    }

    @Test
    void getAll() {
        assertEquals(personSet, personDAO.getAll());
    }

    @Test
    void get() {
        assertEquals(person.name, personDAO_WithOnePerson.get("Maxim").name);
    }

    @Test
    void remove() {
        personDAO_WithOnePerson.remove("Maxim");
        assertTrue(personMap_WithOnePerson.isEmpty());
    }
}