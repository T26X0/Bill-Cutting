package ru.daniil.sawing;

import ru.daniil.sawing.config.SawAppService;

import java.util.Scanner;


public final class SawApp {

    private static final Scanner input = new Scanner(System.in);
    private final SawAppService appConfig = new SawAppService();
    private String personName;
    private int newSpend;

    public static void main(String[] args) {
        SawApp app = new SawApp();
        app.runApp();
    }

    public void runApp() {

        while (true) {
            String answer;

            showAppMenu();

            answer = getNextString();
            switch (answer) {
                case "1" -> actionsToAddNewPerson();
                case "2" -> actionsToAddNewGeneralSpend();
                case "3" -> actionsToAddNewSpendForPerson();
                case "4" -> actionsToShowAllPersons();
                case "5" -> actionsToShowAllSpends();
                case "6" -> actionsToShowAllPersonSpends();
                case "7" -> actionsToDisplayEachPersonsShare();
                case "8" -> actionsToExit();
                default -> System.out.println("You made a mistake when entering data");
            }
        }
    }

    public void showAppMenu() {
        System.out.println("____________________________________________________________________");
        System.out.println("                           All options:");
        System.out.println("  1. Add new person              |   4. Show all persons");
        System.out.println("  2. Add new general spend       |   5. Show all spends");
        System.out.println("  3. Add new spend for person    |   6. Show all spends for person");
        System.out.println("                  7. What everyone has to pay");
        System.out.println("                            8. Exit");
    }

    public void actionsToAddNewPerson() {
        System.out.println("Enter the name of the new participant:");
        personName = getNextString();
        try {
            appConfig.addNewPerson(personName);
        } catch (IllegalArgumentException e) {
            System.out.println("This user already exists");
        }
    }

    public void actionsToAddNewGeneralSpend() {
        System.out.println("Enter new spend");
        newSpend = getNextInteger();
        appConfig.addGeneralSpend(newSpend);
    }

    public void actionsToAddNewSpendForPerson() {
        System.out.println("Enter the person name:");
        personName = getNextString();

        if (appConfig.personIsExist(personName)) {
            System.out.println("Enter new spend:");
            newSpend = getNextInteger();
            appConfig.addSpend(personName, newSpend);
        } else {
            System.out.println("This user has not been added to the general account");
            System.out.println("Add? (y\\n)");
            if (getNextString().equals("y")) {
                try {
                    appConfig.addNewPerson(personName);
                } catch (IllegalArgumentException e) {
                    System.out.println("This user already exists");
                }
            }
        }
    }

    public void actionsToShowAllPersons() {
        System.out.println(appConfig.getAllPerson());
    }

    public void actionsToShowAllSpends() {
        System.out.println(appConfig.getAllSpends());
    }

    public void actionsToShowAllPersonSpends() {
        System.out.println("Enter person name:");
        personName = getNextString();

        if (!appConfig.personIsExist(personName)) {
            System.out.println("This user has not been added to the general account");
            System.out.println("Add? (y\\n)");
            if (getNextString().equals("y")) {
                try {
                    appConfig.addNewPerson(personName);
                } catch (IllegalArgumentException e) {
                    System.out.println("This user already exists");
                }
            }
        }
        System.out.println(appConfig.getAllSpendsFor(personName));
    }

    public void actionsToDisplayEachPersonsShare() {
        System.out.println(appConfig.divideAmongEveryone());
    }

    public void actionsToExit() {
        System.exit(0);
    }

    private String getNextString() {
        return input.nextLine().trim();
    }

    private int getNextInteger() {
        return Integer.parseInt(input.nextLine().trim());
    }
}
