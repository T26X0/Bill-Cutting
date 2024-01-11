package ru.daniil.split;

import ru.daniil.split.config.AppService;
import ru.daniil.split.exceptions.NonValidNameException;
import ru.daniil.split.exceptions.PersonIsExistException;
import ru.daniil.split.exceptions.ZeroSpendsException;

import java.util.Scanner;
import java.util.Set;


public final class SawApp {

    private static final Scanner input = new Scanner(System.in);
    private final AppService appService = new AppService();
    private final int INPUT_ERROR = -1;

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
                case "1" -> addNewPerson();
                case "2" -> addNewSpend();
                case "3" -> showAllPersons();
                case "4" -> showAllSpends();
                case "5" -> displayEachPersonsShare();
                case "6" -> exit();
                default -> System.out.println("You made a mistake when entering data");
            }
        }
    }

    public void showAppMenu() {
        System.out.println();
        System.out.println("________________________________________________________");
        System.out.println("                      All options:");
        System.out.println("  1. Add new person        |      3. Show all persons");
        System.out.println("  2. Add new spend         |      4. Show all spends");
        System.out.println("              5. What everyone has to pay");
        System.out.println("                        6. Exit");
    }

    public void addNewPerson() {
        System.out.println("Enter the name of the new participant:");
        String personName = getNextString();
        try {
            appService.addNewPerson(personName);
        } catch (PersonIsExistException e) {
            System.out.println("A person " + personName + " is exist.");
        } catch (NonValidNameException e) {
            System.out.println("The username entered is incorrect. " +
                    "    * It must be between 4 and 15 characters long" +
                    "    * Must be one word");
        }
    }

    public void addNewSpend() {

        System.out.println("Enter new spend: ");
        int newSpend = getNextInteger();

        if (newSpend == INPUT_ERROR) {
            System.out.println("The application only considers integer values");
        }
        if (newSpend == 0) {
            System.out.println("Can't add zero");
        } else {
            try {
                appService.addSpend(newSpend);
            } catch (ZeroSpendsException e) {
                System.out.println("Can't add negative number");
            }
        }
    }

    public void showAllPersons() {
        Set<String> allPersons = appService.getAllPerson();

        if (!allPersons.isEmpty()) {
            int personCount = 1;
            System.out.println("Total registered {" + allPersons.size() + "} persons");
            for (String personName : allPersons) {
                System.out.println(personCount + ". " + personName);
                personCount++;
            }
        } else {
            System.out.println("No registered persons");
        }
    }

    public void showAllSpends() {
        int allSpends = appService.getAllSpends();

        if (allSpends == 0) {
            System.out.println("No expenses");
        } else {
            System.out.println("Всего было потрачено: " + allSpends);
        }
    }

    public void displayEachPersonsShare() {

        System.out.println("Each person's share: " + appService.divideAmongEveryone());
    }

    public void exit() {

        System.exit(0);
    }

    private String getNextString() {
        return input.nextLine().trim();
    }

    private int getNextInteger() {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            return INPUT_ERROR;
        }
    }
}
