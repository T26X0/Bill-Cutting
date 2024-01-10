package ru.daniil.sawing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.daniil.sawing.config.AppService;
import ru.daniil.sawing.myExceptions.NonValidNameException;
import ru.daniil.sawing.myExceptions.PersonIsExistException;
import ru.daniil.sawing.myExceptions.ZeroSpendsException;

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
        appService.addNewPerson("Daniil");

        boolean secondAnswer = appService.getAllPerson().contains("Daniil");
        Assertions.assertNotEquals(firstAnswer, secondAnswer);
    }

    @Test
    void addExistPerson() {

        appService.addNewPerson("Nikita");
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
        appService.addSpend(NEW_VALID_SPEND);
        Assertions.assertEquals(NEW_VALID_SPEND, appService.getAllSpends());
    }

    @Test
    void addNewNegativeSpends() {
        Assertions.assertThrows(ZeroSpendsException.class, () -> {
            appService.addSpend(NEW_NEGATIVE_SPEND);
        });
    }

    @Test
    void displayEachPersonsShare() {
        int firstResult = appService.getAllSpends();
        appService.addSpend(100);
        Assertions.assertTrue(firstResult < appService.getAllSpends());
    }
}
