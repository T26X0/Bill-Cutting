package ru.daniil.sawing;

import java.util.HashMap;
import java.util.Map;

public class SawApp {

    private Map<String, Integer> allPersonAndTheirSpend = new HashMap<>();
    private final String GENERAL_PERSON = "generalSpend";
    private int allSpends = 0;

    public SawApp() {
        allPersonAndTheirSpend.put(GENERAL_PERSON, allSpends);
    }

    public void addNewPerson(String personName) {
        if (allPersonAndTheirSpend.containsKey(personName)) {
            throw new IllegalArgumentException("Person + {" + personName + "} already exist");
        }
        allPersonAndTheirSpend.put(personName, 0);
    }

    public void addSpend(int newSpend) {
        addSpend(GENERAL_PERSON, newSpend);
    }

    public void addSpend(String personName, int newSpend) {
        if (newSpend <= 0) {
            throw new IllegalArgumentException("Spending can't be negative");
        }

        allSpends += newSpend;

        int oldSpend = allPersonAndTheirSpend.get(personName);
        oldSpend += newSpend;
        allPersonAndTheirSpend.put(personName, oldSpend);
    }

    public double divideAmongEveryone() {
        return (double) allSpends / (allPersonAndTheirSpend.size() - 1);
    }
}

class Tests {
    public static void main(String[] args) {
        testForTheRightResult();
    }

    private static void testForTheRightResult() {
        SawApp sawApp = new SawApp();
        sawApp.addNewPerson("Daniil");
        sawApp.addNewPerson("Nikita");
        sawApp.addNewPerson("Maxim");

        sawApp.addSpend("Nikita", 100);
        sawApp.addSpend("Daniil", 100);
        sawApp.addSpend(100);
        int expectedResult = 100;
        assert sawApp.divideAmongEveryone() == expectedResult;
    }
}
