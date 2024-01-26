package ru.daniil.split.service;

import ru.daniil.split.dao.PersonDAO;
import ru.daniil.split.exceptions.NonValidArgumentException;
import ru.daniil.split.exceptions.DuplicateResourceException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class PersonService {

    private final int MAX_NAME_LENGTH = 15;
    private final int MIN_NAME_LENGTH = 4;
    private final PersonDAO personDAO;

    public PersonService(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void addNewPerson(String personName) throws NonValidArgumentException, DuplicateResourceException {
        isValidPersonName(personName);
        personDAO.insert(personName);
    }

    public void addSpend(int newSpend) throws NonValidArgumentException {
        isValidSpend(newSpend);
        personDAO.addSpend(newSpend);
    }

    public Set<String> getAllPerson() {
        return personDAO.getAllPersonNames();
    }

    public int getPersonCount() {
        return personDAO.getPersonCount();
    }

    public int getAllSpends() {
        return personDAO.getSpends();
    }

    public BigDecimal divideAmongEveryone() {
        int personCount = personDAO.getPersonCount();
        if (personCount == 0) {
            return new BigDecimal(0);
        }

        BigDecimal allExpenses = new BigDecimal(personDAO.getSpends());
        BigDecimal persons = new BigDecimal(personCount);
        return allExpenses.divide(persons, RoundingMode.CEILING);
    }

    private void isValidSpend(int newSpend) throws NonValidArgumentException {
        if (newSpend < 0) {
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
}
