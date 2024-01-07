package ru.daniil.sawing;

import java.util.*;

public class SawApp {

    final Scanner input = new Scanner(System.in);
    private Map<String, List<Integer>> allPersonAndTheirSpend = new HashMap<>();
    private final String GENERAL_PERSON = "generalSpend";

    public SawApp() {
        allPersonAndTheirSpend.put(GENERAL_PERSON, new ArrayList<>());
    }

    public static void main(String[] args) {
        SawApp sawApp = new SawApp();
        sawApp.addNewPerson("Daniil");
        sawApp.addNewPerson("Nikita");
        sawApp.addGeneralSpend(100);
        sawApp.addSpend("Daniil", 100);
        System.out.println(sawApp.getAllPerson());
        System.out.println(sawApp.divideAmongEveryone());
    }

    public void addNewPerson(String personName) {
        if (allPersonAndTheirSpend.containsKey(personName)) {
            throw new IllegalArgumentException("Person + {" + personName + "} already exist");
        }
        allPersonAndTheirSpend.put(personName, new ArrayList<>());
    }

    public List<String> getAllPerson() {
        List<String> allPersons = new ArrayList<>(allPersonAndTheirSpend.keySet());
        allPersons.remove(GENERAL_PERSON);
        return allPersons;
    }

    public void addGeneralSpend(int newSpend) {
        allPersonAndTheirSpend.get(GENERAL_PERSON).add(newSpend);
    }

    public void addSpend(String personName, int newSpend) {
        if (newSpend <= 0) {
            throw new IllegalArgumentException("Spending can't be negative");
        }
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
}

class Tests {

    public static void main(String[] args) {
        String answer;
        SawApp sawApp = new SawApp();
        while (true) {
            System.out.println("All options:");
            System.out.println("   . Add new person");
            System.out.println("   . Add new spend");
            System.out.println("   . Add new spend for person");
            System.out.println("   . What everyone has to pay");
            System.out.println("   . Show all person");
            System.out.println("   . Show all expenses for person");
            answer = sawApp.input.nextLine();

            switch (answer) {
                case "1" -> {

                }
                case "2" -> {

                }
                case "3" -> {

                }
                case "4" -> {

                }
                case "5" -> {

                }
                default -> {
                    System.out.println("You made a mistake when entering data");
                }
            }
        }
    }

    private static void testForTheRightResult() {
        SawApp sawApp = new SawApp();
        sawApp.addNewPerson("Daniil");
        sawApp.addNewPerson("Nikita");
        sawApp.addNewPerson("Maxim");

        sawApp.addSpend("Nikita", 100);
        sawApp.addSpend("Daniil", 100);
        sawApp.addGeneralSpend(100);
        int expectedResult = 100;
        assert sawApp.divideAmongEveryone() == expectedResult;
    }
}
