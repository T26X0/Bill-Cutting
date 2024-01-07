package ru.daniil.sawing;

import java.util.*;

public class SawApp {

    private Map<String, List<Integer>> allPersonAndTheirSpend = new HashMap<>();
    private final String GENERAL_PERSON = "generalSpend";

    public SawApp() {
        allPersonAndTheirSpend.put(GENERAL_PERSON, new ArrayList<>());
    }

    public SawApp(String ... startingPerson) {
        allPersonAndTheirSpend.put(GENERAL_PERSON, new ArrayList<>());

        for (String personName : startingPerson) {
            addNewPerson(personName);
        }
    }

    public static void main(String[] args) {
        final Scanner input = new Scanner(System.in);
        SawApp sawApp = new SawApp();

        String answer;
        String personName;
        int newSpend;

        while (true) {
            System.out.println("____________________________________________________________________");
            System.out.println("                           All options:");
            System.out.println("  1. Add new person              |   4. Show all persons");
            System.out.println("  2. Add new general spend       |   5. Show all expenses");
            System.out.println("  3. Add new spend for person    |   6. Show all expenses for person");
            System.out.println("                  7. What everyone has to pay");

            answer = input.nextLine().trim();
            switch (answer) {
                case "1" -> {
                    System.out.println("Enter the name of the new participant:");
                    personName = input.nextLine().trim();
                    try {
                        sawApp.addNewPerson(personName);
                    } catch (IllegalArgumentException e) {
                        System.out.println("This user already exists");
                    }
                }
                case "2" -> {
                    System.out.println("Enter new spend");
                    newSpend = Integer.parseInt(input.nextLine());
                    sawApp.addGeneralSpend(newSpend);
                }
                case "3" -> {
                    System.out.println("Enter the person name:");
                    personName = input.nextLine().trim();

                    if (sawApp.personIsExist(personName)) {
                        System.out.println("Enter new spend:");
                        newSpend = Integer.parseInt(input.nextLine().trim());
                        sawApp.addSpend(personName, newSpend);
                        break;
                    }
                    System.out.println("This user has not been added to the general account");
                    System.out.println("Add? (y\\n)");
                    if (input.nextLine().equals("y")) {
                        try {
                            sawApp.addNewPerson(personName);
                        } catch (IllegalArgumentException e) {
                            System.out.println("This user already exists");
                        }
                    }
                }
                case "4" -> System.out.println(sawApp.getAllPerson());
                case "5" -> System.out.println(sawApp.getAllSpends());
                case "6" -> {
                    System.out.println("Enter person name:");
                    personName = input.nextLine().trim();
                    System.out.println(sawApp.getAllSpendsFor(personName));
                }
                case "7" -> System.out.println(sawApp.divideAmongEveryone());
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
