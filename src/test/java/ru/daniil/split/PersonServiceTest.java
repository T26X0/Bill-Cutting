package ru.daniil.split;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.daniil.split.service.PersonService;
import ru.daniil.split.exceptions.NonValidArgumentException;
import ru.daniil.split.exceptions.DoubleEntryException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PersonServiceTest {

    public static final int NEW_VALID_SPEND = 1200;
    public static final int NEW_NEGATIVE_SPEND = -12;
    private PersonService personService;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
    }

    @Test
    void addNewValidPerson() throws NonValidArgumentException, DoubleEntryException {
        personService.addNewPerson("Lerya");
        assertNotEquals(false, personService.getAllPerson().contains("Daniil"));
    }

    @Test
    void addNewNonValidPerson() {
        assertAll(
                () ->assertThrows(NonValidArgumentException.class, () -> personService.addNewPerson("abc")),
                () ->assertThrows(NonValidArgumentException.class, () -> personService.addNewPerson("abc abc"))
        );

    }

    @Test
    void addExistPerson() throws NonValidArgumentException, DoubleEntryException {
        personService.addNewPerson("Nikita");
        assertThrows(DoubleEntryException.class, () -> {
            personService.addNewPerson("Nikita");
        });
    }

    @Test
    void addNewValidSpends() throws NonValidArgumentException {
        personService.addSpend(NEW_VALID_SPEND);
        assertEquals(NEW_VALID_SPEND, personService.getAllSpends());
    }

    @Test
    void addNewNegativeSpends() {
        assertThrows(NonValidArgumentException.class, () -> {
            personService.addSpend(NEW_NEGATIVE_SPEND);
        });
    }

    @Test
    @DisplayName("Test case for testing different variations")
    void displayEachPersonsShare() throws NonValidArgumentException, DoubleEntryException, NonValidArgumentException {
        PersonService service2 = createAppService(0);
        PersonService service1 = createAppService(0, "Daniil", "Nikita", "Oleg");
        PersonService service3 = createAppService(200, "Daniil", "Nikita", "Oleg");
        PersonService service4 = createAppService(200, "Daniil", "Nikita", "Oleg", "Maxim");
        PersonService service5 = createAppService(789);
        PersonService service6 = createAppService(789, "Daniil", "Nikita", "Oleg", "Maxim");

        assertAll(
                () -> assertEquals(new BigDecimal(0), service1.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(0), service2.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(67), service3.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(50), service4.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(0), service5.divideAmongEveryone()),
                () -> assertEquals(new BigDecimal(198), service6.divideAmongEveryone())
        );
    }

    /**
     *  A method that creates a new service with given parameters
     * @param allSpends -> the sum of all expenses
     * @param allPersons -> Array of all usernames
     */
    private PersonService createAppService(int allSpends, String... allPersons) throws NonValidArgumentException, NonValidArgumentException, DoubleEntryException {
        PersonService service = new PersonService();
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
