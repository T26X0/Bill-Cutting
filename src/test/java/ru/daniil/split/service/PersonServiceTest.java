package ru.daniil.split.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.daniil.split.service.PersonService;
import ru.daniil.split.exceptions.NonValidArgumentException;
import ru.daniil.split.exceptions.DoubleEntryException;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

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
        assertNotEquals(false, personService.getAllPerson().contains("Lerya"));
    }

    @Test
    void addNewNonValidPerson() {
        assertAll(
                () -> assertThrows(NonValidArgumentException.class, () -> personService.addNewPerson("abc")),
                () -> assertThrows(NonValidArgumentException.class, () -> personService.addNewPerson("abc abc"))
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

    @ParameterizedTest()
    @MethodSource()
    void displayEachPersonsShare(int expectedResult, PersonService service) {
        assertEquals(new BigDecimal(expectedResult), service.divideAmongEveryone());
    }

    static Stream<Object[]> displayEachPersonsShare() throws NonValidArgumentException, DoubleEntryException {
        return Stream.of(
                new Object[]{0, createAppService(0, List.of())},
                new Object[]{0, createAppService(0, List.of("Daniil", "Nikita", "Oleg"))},
                new Object[]{67, createAppService(200, List.of("Daniil", "Nikita", "Oleg"))},
                new Object[]{50, createAppService(200, List.of("Daniil", "Nikita", "Oleg", "Maxim"))},
                new Object[]{0, createAppService(789, List.of())},
                new Object[]{198, createAppService(789, List.of("Daniil", "Nikita", "Oleg", "Maxim"))}
        );
    }

    /**
     * A method that creates a new service with given parameters
     *
     * @param allSpends  -> the sum of all expenses
     * @param allPersons -> Array of all usernames
     */
    private static PersonService createAppService(int allSpends, List<String> allPersons) throws NonValidArgumentException, DoubleEntryException {
        PersonService service = new PersonService();
        service.addSpend(allSpends);

        for (String personName : allPersons) {
            service.addNewPerson(personName);
        }

        return service;
    }
}
