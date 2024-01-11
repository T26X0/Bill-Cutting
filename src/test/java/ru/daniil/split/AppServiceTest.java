package ru.daniil.split;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.daniil.split.config.AppService;
import ru.daniil.split.exceptions.NonValidNameException;
import ru.daniil.split.exceptions.PersonIsExistException;
import ru.daniil.split.exceptions.NegativeSpendException;

class AppServiceTest {

    public static final int NEW_VALID_SPEND = 1200;
    public static final int NEW_NEGATIVE_SPEND = -12;
    private AppService appService;

    @BeforeEach
    void setUp() {
        appService = new AppService();
    }

    @Test
    void addNewValidPerson() {

        boolean firstAnswer = appService.getAllPerson().contains("Daniil");
        try {
            appService.addNewPerson("Daniil");
        } catch (NonValidNameException | PersonIsExistException e) {
            System.out.println(e.getMessage());
        }

        boolean secondAnswer = appService.getAllPerson().contains("Daniil");
        Assertions.assertNotEquals(firstAnswer, secondAnswer);
    }

    @Test
    void addExistPerson() {

        try {
            appService.addNewPerson("Nikita");
        } catch (NonValidNameException | PersonIsExistException e) {
            System.out.println(e.getMessage());
        }
        Assertions.assertThrows(PersonIsExistException.class, () -> {
            appService.addNewPerson("Nikita");
        });
    }

    @Test
    void addNewNonValidPersons_1() {
        Assertions.assertThrows(NonValidNameException.class, () -> {
            appService.addNewPerson("Kostya Petrov");
        });
    }

    @Test
    void addNewNonValidPersons_2() {
        Assertions.assertThrows(NonValidNameException.class, () -> {
            appService.addNewPerson("Dab");
        });
    }

    @Test
    void addNewValidSpends() {
        try {
            appService.addSpend(NEW_VALID_SPEND);
        } catch (NegativeSpendException e) {
            System.out.println(e.getMessage());
        }
        Assertions.assertEquals(NEW_VALID_SPEND, appService.getAllSpends());
    }

    @Test
    void addNewNegativeSpends() {
        Assertions.assertThrows(NegativeSpendException.class, () -> {
            appService.addSpend(NEW_NEGATIVE_SPEND);
        });
    }

    @Test
    void displayEachPersonsShare() {
        int firstResult = appService.getAllSpends();
        try {
            appService.addSpend(100);
        } catch (NegativeSpendException e) {
            System.out.println(e.getMessage());
        }
        Assertions.assertTrue(firstResult < appService.getAllSpends());
    }
}
