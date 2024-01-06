package ru.daniil.sawing;

import java.io.StringBufferInputStream;
import java.util.*;

public class SawApp {

    final Scanner input = new Scanner(System.in);
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

    public List<String> getAllPerson() {
        List<String> allUsers = new ArrayList<>(allPersonAndTheirSpend.keySet());
        return allUsers;
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
        sawApp.addSpend(100);
        int expectedResult = 100;
        assert sawApp.divideAmongEveryone() == expectedResult;
    }
}
