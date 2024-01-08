package ru.daniil.sawing.config;

import ru.daniil.sawing.myExceptions.PersonIsExistException;

import java.math.BigDecimal;
import java.util.*;

public class AppService {

    private int allSpends = 0;
    private final Set<String> allPersons = new HashSet<>();
    private final String GENERAL_PERSON = "generalSpend";

    public void addNewPerson(String personName) {
        if (allPersons.contains(personName)) {
            throw new PersonIsExistException("Person + {" + personName + "} already exist");
        }
        allPersons.add(personName);
    }

    public Set<String> getAllPerson() {
        return allPersons;
    }

    public void addSpend(int newSpend) {
        isValidSpend(newSpend);
        allSpends += newSpend;
    }

    public BigDecimal divideAmongEveryone() {
        return new BigDecimal(allSpends / allPersons.size());
    }

    private static void isValidSpend(int newSpend) {
        if (newSpend <= 0) {
            throw new IllegalArgumentException("Spending can't be negative");
        }
    }
}
