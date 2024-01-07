package ru.daniil.sawing;

import java.util.*;

public class SawApp {

    private Map<String, List<Integer>> allPersonAndTheirSpend = new HashMap<>();
    private final String GENERAL_PERSON = "generalSpend";

    public SawApp() {
        allPersonAndTheirSpend.put(GENERAL_PERSON, new ArrayList<>());
    }

    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        String answer;
        SawApp sawApp = new SawApp();

        while (true) {
            System.out.println("All options:");
            System.out.println("  1. Add new person");
            System.out.println("  2. Add new general spend");
            System.out.println("  3. Add new spend for person");
            System.out.println("  4. What everyone has to pay");
            System.out.println("  5. Show all persons");
            System.out.println("  6. Show all expenses");
            System.out.println("  7. Show all expenses for person");

            answer = input.nextLine();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name of the new participant:");
                    sawApp.addNewPerson(input.nextLine());
                }
                case "2" -> {
                    System.out.println("Enter new spend");
                    sawApp.addGeneralSpend(Integer.parseInt(input.nextLine()));
                }
                case "3" -> {
                    String personName;
                    System.out.println("Enter the person name:");
                    personName = input.nextLine();
                    if (sawApp.personIsExist(personName)) {
                        System.out.println("Enter new spend:");
                        int newSpend = Integer.parseInt(input.nextLine());
                        sawApp.addSpend(personName, newSpend);
                        break;
                    }
                    System.out.println("This user has not been added to the general account");
                    System.out.println("Add? (y\\n)");
                    if (input.nextLine().equals("y")) {
                        sawApp.addNewPerson(personName);
                    }
                }
                case "4" -> {

                }
                case "5" -> {

                }
                case "6" -> {

                }
                case "7" -> {

                }
                default -> System.out.println("You made a mistake when entering data");
            }
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

    public void showAllPerson() {
        List<String> allPersons = new ArrayList<>(allPersonAndTheirSpend.keySet());
        allPersons.remove(GENERAL_PERSON);
        System.out.println(allPersons);
    }

    public void addGeneralSpend(int newSpend) {
        isValidSpend(newSpend);
        allPersonAndTheirSpend.get(GENERAL_PERSON).add(newSpend);
    }

    public void addSpend(String personName, int newSpend) {
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
