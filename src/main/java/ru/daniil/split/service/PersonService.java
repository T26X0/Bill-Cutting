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
    private int allSpends = 0;
    private final Set<String> allPersons = new HashSet<>();

    public void addNewPerson(String personName) throws NonValidArgumentException, DuplicateResourceException {
        isValidPersonName(personName);
        personIsExist(personName);
        allPersons.add(personName);
    }

    public void addSpend(int newSpend) throws NonValidArgumentException {
        isValidSpend(newSpend);
        allSpends += newSpend;
    }

    public Set<String> getAllPerson() {
        return new HashSet<>(allPersons);
    }

    public int getAllSpends() {
        return allSpends;
    }

    public BigDecimal divideAmongEveryone() {
        if (allPersons.isEmpty()) {
            return new BigDecimal(0);
        }

        BigDecimal allExpenses = new BigDecimal(allSpends);
        BigDecimal personCount = new BigDecimal(allPersons.size());
        return allExpenses.divide(personCount, RoundingMode.CEILING);
    }

    private void isValidSpend(int newSpend) throws NonValidArgumentException  {
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

    private void personIsExist(String personName) throws DuplicateResourceException {
        if (allPersons.contains(personName)) {
            throw new DuplicateResourceException("Person + {" + personName + "} already exist");
        }
    }
}
