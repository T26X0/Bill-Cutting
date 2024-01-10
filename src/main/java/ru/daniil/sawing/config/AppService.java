package ru.daniil.sawing.config;

import ru.daniil.sawing.myExceptions.NonValidNameException;
import ru.daniil.sawing.myExceptions.PersonIsExistException;
import ru.daniil.sawing.myExceptions.ZeroSpendsException;

import java.math.BigDecimal;
import java.util.*;

public class AppService {

    public static final int MAX_NAME_LENGTH = 15;
    public static final int MIN_NAME_LENGTH = 4;
    private int allSpends = 0;
    private final Set<String> allPersons = new HashSet<>();

    public void addNewPerson(String personName) throws NonValidNameException {
        isValidPersonName(personName);
        if (allPersons.contains(personName)) {
            throw new PersonIsExistException("Person + {" + personName + "} already exist");
        }
        allPersons.add(personName);
    }

    public void addSpend(int newSpend) throws ZeroSpendsException {
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
        return new BigDecimal(allSpends / allPersons.size());
    }

    private static void isValidSpend(int newSpend) throws ZeroSpendsException {
        if (newSpend <= 0) {
            throw new ZeroSpendsException("Spending can't be negative");
        }
    }

    private static void isValidPersonName(String personName) {
        if (personName.length() < MIN_NAME_LENGTH || personName.length() > MAX_NAME_LENGTH) {
            throw new NonValidNameException("The name cannot be shorter than 4 characters and longer than 15");
        }
        int wordCount = personName.split(" ").length;
        if (wordCount > 1) {
            throw new NonValidNameException("The person name can only consist of one word");
        }
    }
}