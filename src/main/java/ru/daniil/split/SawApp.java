package ru.daniil.split;

import ru.daniil.split.dao.JsonPersonDAO;
import ru.daniil.split.service.PersonService;
import ru.daniil.split.exceptions.NonValidArgumentException;
import ru.daniil.split.exceptions.DuplicateResourceException;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;


public final class SawApp {

    private final Scanner input = new Scanner(System.in);
    private final PersonService personService = new PersonService(new JsonPersonDAO());

    public static void main(String[] args) {
        SawApp app = new SawApp();
        app.runApp();
    }

    public void runApp() {
        final String ADD_NEW_PERSON = "1";
        final String ADD_NEW_SPEND = "2";
        final String SHOW_ALL_PERSON = "3";
        final String SHOW_ALL_SPENDS = "4";
        final String DISPLAY_EACH_PERSONS_SHARE = "5";
        final String EXIT = "7";
        final String RESET = "6";

        while (true) {
            String userAnswer;

            showAppMenu();

            userAnswer = getNextString();
            switch (userAnswer) {
                case ADD_NEW_PERSON -> addNewPerson();
                case ADD_NEW_SPEND -> addNewSpend();
                case SHOW_ALL_PERSON -> showAllPersons();
                case SHOW_ALL_SPENDS -> showAllSpends();
                case DISPLAY_EACH_PERSONS_SHARE -> displayEachPersonsShare();
                case RESET -> reset();
                case EXIT -> exit();
                default -> showError("You made a mistake when entering data");
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
        System.out.println("                        6. Reset");
        System.out.println("                        7. Exit");
    }

    public void addNewPerson() {
        System.out.println("Enter the name of the new participant:");
        String personName = getNextString();
        try {
            personService.addNewPerson(personName);
        } catch (DuplicateResourceException e) {
            showError("This person is already registered");
        } catch (NonValidArgumentException e) {
            showError("The username entered is incorrect. \n" + "    * " + e.getMessage());
        } catch (IOException e) {
            showError("An error occurred while entering data, please try later");
        }
    }

    public void addNewSpend() {
        int newSpend;
        System.out.println("Enter new spend:");

        try {
            newSpend = getNextInteger();
        } catch (NumberFormatException e) {
            showError("spends can only be an integer");
            return;
        }
        try {
            personService.addSpend(newSpend);
        } catch (NonValidArgumentException e) {
            showError("Negative cannot be negative");
        } catch (IOException e) {
            showError("An error occurred while entering data, please try later");
        }
    }

    public void showAllPersons() {
        Set<String> allPersons;
        try {
            allPersons = personService.getAllPerson();
        } catch (IOException e) {
            showError("An error occurred while reading data, please try later");
            return;
        }

        if (allPersons.isEmpty()) {
            showError("No registered persons");
            return;
        }

        System.out.println("All registered persons:");
        allPersons.forEach(s -> System.out.println("* " + s));
    }

    public void showAllSpends() {
        int allSpends;
        try {
            allSpends = personService.getAllSpends();
        } catch (IOException e) {
            showError("An error occurred while reading data, please try later");
            return;
        }

        if (allSpends == 0) {
            showError("No expenses");
            return;
        }
        System.out.println("Total was spends: " + allSpends);
    }

    public void displayEachPersonsShare() {
        int quantityPerson;
        try {
            quantityPerson = personService.getPersonCount();
        } catch (IOException e) {
            showError("An error occurred while reading data, please try later");
            return;
        }

        if (quantityPerson == 0) {
            System.out.println("No users need to pay for the account");
            return;
        }

        BigDecimal result;
        try {
            result = personService.divideAmongEveryone();
        } catch (IOException e) {
            showError("An error occurred while reading data, please try later");
            return;
        }

        if (result.equals(new BigDecimal(0))) {
            System.out.println("No expenses");
            return;
        }

        System.out.println("Each person's share: " + result);
    }

    public void exit() {
        System.exit(0);
    }

    public void reset() {
        try {
            personService.reset();
        } catch (IOException e) {
            showError("An error occurred while reading data, please try later");
        }
    }

    private void showError(String string) {
        System.out.println(string);
    }

    private String getNextString() {
        return input.nextLine().trim();
    }

    private int getNextInteger() {
        return Integer.parseInt(input.nextLine().trim());
    }
}
