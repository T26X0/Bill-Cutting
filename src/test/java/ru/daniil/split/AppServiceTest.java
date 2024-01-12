package ru.daniil.split;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.daniil.split.config.AppService;
import ru.daniil.split.exceptions.NonValidNameException;
import ru.daniil.split.exceptions.PersonIsExistException;
import ru.daniil.split.exceptions.NegativeSpendException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotEquals(firstAnswer, secondAnswer);
    }

    @Test
    void addExistPerson() {

        try {
            appService.addNewPerson("Nikita");
        } catch (NonValidNameException | PersonIsExistException e) {
            System.out.println(e.getMessage());
        }
        assertThrows(PersonIsExistException.class, () -> {
            appService.addNewPerson("Nikita");
        });
    }

    @Test
    void addNewNonValidPersons_1() {
        assertThrows(NonValidNameException.class, () -> {
            appService.addNewPerson("Kostya Petrov");
        });
    }

    @Test
    void addNewNonValidPersons_2() {
        assertThrows(NonValidNameException.class, () -> {
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
        assertEquals(NEW_VALID_SPEND, appService.getAllSpends());
    }

    @Test
    void addNewNegativeSpends() {
        assertThrows(NegativeSpendException.class, () -> {
            appService.addSpend(NEW_NEGATIVE_SPEND);
        });
    }

    @Test
    void displayEachPersonsShare() throws NonValidNameException, PersonIsExistException, NegativeSpendException {
        AppService service2 = createAppService(0);
        AppService service1 = createAppService(0, "Daniil", "Nikita", "Oleg");
        AppService service3 = createAppService(200, "Daniil", "Nikita", "Oleg");
        AppService service4 = createAppService(200, "Daniil", "Nikita", "Oleg", "Maxim");
        AppService service5 = createAppService(789);
        AppService service6 = createAppService(789, "Daniil", "Nikita", "Oleg", "Maxim");

        assertAll(
                () -> assertEquals(new BigDecimal(0), service1.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(0), service2.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(67), service3.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(50), service4.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(0), service5.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(198), service6.divideAmongEveryone())
        );
    }

    private AppService createAppService(int allSpends, String... allPersons) throws NegativeSpendException, NonValidNameException, PersonIsExistException {
        AppService service = new AppService();
        service.addSpend(allSpends);

        if (allPersons == null) {
            return service;
        }
        for (String personName : allPersons) {
            service.addNewPerson(personName);
        }

        return service;
    }
}
