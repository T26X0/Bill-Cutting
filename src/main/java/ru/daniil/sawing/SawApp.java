package ru.daniil.sawing;

import ru.daniil.sawing.config.AppService;
import ru.daniil.sawing.myExceptions.PersonIsExistException;

import java.util.Scanner;


public final class SawApp {

    private static final Scanner input = new Scanner(System.in);
    private final AppService appService = new AppService();
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
                case "1" -> addNewPerson();
                case "2" -> addNewSpend();
                case "3" -> displayEachPersonsShare();
                case "4" -> actionsToExit();
                default -> System.out.println("You made a mistake when entering data");
            }
        }
    }

    public void showAppMenu() {
        System.out.println("________________________________");
        System.out.println("All options:");
        System.out.println("  1. Add new person");
        System.out.println("  2. Add new spend");
        System.out.println("  3. What everyone has to pay");
        System.out.println("  4. Exit");
    }

    public void addNewPerson() {
        System.out.println("Enter the name of the new participant:");
        personName = getNextString();
        try {
            appService.addNewPerson(personName);
        } catch (PersonIsExistException e) {
            System.out.println("This user already exists");
        }
    }

    public void addNewSpend() {
        System.out.println("Enter new spend");
        try {
            newSpend = getNextInteger();
            appService.addSpend(newSpend);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public void displayEachPersonsShare() {
        System.out.println(appService.divideAmongEveryone());
    }

    public void actionsToExit() {
        System.exit(0);
    }

    private String getNextString() {
        return input.nextLine().trim();
    }

    private int getNextInteger() throws NumberFormatException {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("The application only considers integer values");
        }
    }
}
