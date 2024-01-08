package ru.daniil.sawing.config;

import java.util.*;

public class SawAppConfig {

    private Map<String, List<Integer>> allPersonAndTheirSpend = new HashMap<>();
    private final String GENERAL_PERSON = "generalSpend";

    public SawAppConfig() {
        allPersonAndTheirSpend.put(GENERAL_PERSON, new ArrayList<>());
    }

    public SawAppConfig(String ... startingPerson) {
        allPersonAndTheirSpend.put(GENERAL_PERSON, new ArrayList<>());

        for (String personName : startingPerson) {
            addNewPerson(personName);
        }
    }

    public void addNewPerson(String personName) {
        if (allPersonAndTheirSpend.containsKey(personName)) {
            throw new IllegalArgumentException("Person + {" + personName + "} already exist");
        }
        allPersonAndTheirSpend.put(personName, new ArrayList<>());
    }

    public boolean personIsExist(String personName) {
        List<String> allPersons = new ArrayList<>(allPersonAndTheirSpend.keySet());
        return allPersons.contains(personName);
    }

    public List<String> getAllPerson() {
        List<String> allPersons = new ArrayList<>(allPersonAndTheirSpend.keySet());
        allPersons.remove(GENERAL_PERSON);
        return allPersons;
    }

    public List<Integer> getAllSpends() {
        return getAllSpendsFor(GENERAL_PERSON);
    }

    public List<Integer> getAllSpendsFor(String personName) {
        List<Integer> allSpends = new ArrayList<>(allPersonAndTheirSpend.get(personName));
        return allSpends;
    }

    public void addGeneralSpend(int newSpend) {
        isValidSpend(newSpend);
        allPersonAndTheirSpend.get(GENERAL_PERSON).add(newSpend);
    }

    public void addSpend(String personName, int newSpend) {
        if (!allPersonAndTheirSpend.containsKey(personName)) {
            throw new IllegalArgumentException("Person {" + personName + "} does non exist");
        }
        isValidSpend(newSpend);
        allPersonAndTheirSpend.get(personName).add(newSpend);
        addGeneralSpend(newSpend);
    }

    public double divideAmongEveryone() {
        int sumAllSpends = 0;
        List<Integer> allSpends = new ArrayList<>(allPersonAndTheirSpend.get(GENERAL_PERSON));
        for (Integer integer : allSpends) {
            sumAllSpends += integer;
        }
        return (double) sumAllSpends / (allPersonAndTheirSpend.size() - 1);
    }

    private static void isValidSpend(int newSpend) {
        if (newSpend <= 0) {
            throw new IllegalArgumentException("Spending can't be negative");
        }
    }
}
