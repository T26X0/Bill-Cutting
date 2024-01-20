package ru.daniil.split.service;

import ru.daniil.split.dao.InMemoryPersonDAO;
import ru.daniil.split.dao.PersonDAO;
import ru.daniil.split.exceptions.NonValidArgumentException;
import ru.daniil.split.exceptions.DuplicateResourceException;
import ru.daniil.split.model.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PersonService {

    private final int MAX_NAME_LENGTH = 15;
    private final int MIN_NAME_LENGTH = 4;
    private final InMemoryPersonDAO inMemoryPersonDAO = new InMemoryPersonDAO   ();

    public void addNewPerson(String personName) throws NonValidArgumentException, DuplicateResourceException {
        isValidPersonName(personName);
        personIsExist(personName);

        inMemoryPersonDAO.upsert(new Person(personName));
    }

    public void addSpend(String personName, int newSpend) throws NonValidArgumentException, IllegalArgumentException {
        isValidSpend(newSpend);

        Person person = inMemoryPersonDAO.get(personName);
        person.addSpend(new BigDecimal(newSpend));
        inMemoryPersonDAO.upsert(person);
    }

    public void addSpend(int newSpend) throws NonValidArgumentException {
        isValidSpend(newSpend);

        Set<Person> allPerson = inMemoryPersonDAO.getAll();
        BigDecimal spend = new BigDecimal(newSpend);
        BigDecimal personQuantity = new BigDecimal(allPerson.size());
        BigDecimal everyoneShare = spend.divide(personQuantity, RoundingMode.CEILING);

        for (Person person : allPerson) {
            person.addSpend(everyoneShare);
            inMemoryPersonDAO.upsert(person);
        }
    }

    public BigDecimal getAllSpends() {
        BigDecimal allSpends = BigDecimal.ZERO;

        for (Person person : inMemoryPersonDAO.getAll()) {
            for (BigDecimal spend : person.getAllSpends()) {
                allSpends = allSpends.add(spend);
            }
        }
        return allSpends;
    }

    public Set<String> getAllPerson() {
        Set<Person> allPerson = inMemoryPersonDAO.getAll();
        Set<String> allPersonNames = new HashSet<>();

        for (Person person : allPerson) {
            allPersonNames.add(person.name);
        }
        return allPersonNames;
    }

    public BigDecimal divideAmongEveryone() {
        if (inMemoryPersonDAO.getAll().isEmpty()) {
            return new BigDecimal(0);
        }

        BigDecimal allExpenses = getAllSpends();
        BigDecimal personCount = new BigDecimal(inMemoryPersonDAO.getAll().size());
        return allExpenses.divide(personCount, RoundingMode.CEILING);
    }

    public boolean nonContains(String personName) {
        Set<Person> allPerson = inMemoryPersonDAO.getAll();

        for (Person person : allPerson) {
            if (person.name.equals(personName)) return false;
        }
        return true;
    }

    private void isValidSpend(int newSpend) throws NonValidArgumentException {
        if (newSpend <= 0) {
            throw new NonValidArgumentException("Spending can't be negative");
        }
    }

    private void isValidPersonName(String personName) throws NonValidArgumentException {
        if (personName.length() < MIN_NAME_LENGTH || personName.length() > MAX_NAME_LENGTH) {
            throw new NonValidArgumentException(
                    "The name cannot be shorter than " + MIN_NAME_LENGTH +
                            " characters and longer than " + MAX_NAME_LENGTH);
        }
        if (personName.contains(" ")) {
            throw new NonValidArgumentException("The person name can only consist of one word");
        }
    }

    private void personIsExist(String personName) throws DuplicateResourceException {
        Set<Person> allPerson = inMemoryPersonDAO.getAll();
        for (Person person : allPerson) {
            if (person.name.equals(personName)) {
                throw new DuplicateResourceException("Person {" + personName + "} already exist");
            }
        }
    }
}
