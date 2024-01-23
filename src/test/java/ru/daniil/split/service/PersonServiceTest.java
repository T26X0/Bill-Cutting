package ru.daniil.split.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.daniil.split.dao.InMemoryPersonDAO;
import ru.daniil.split.exceptions.DuplicateResourceException;
import ru.daniil.split.exceptions.NonValidArgumentException;

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
        personService = new PersonService(new InMemoryPersonDAO());
    }

    @Test
    void addNewValidPerson() throws NonValidArgumentException, DuplicateResourceException {
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
    void addExistPerson() throws NonValidArgumentException, DuplicateResourceException {
        personService.addNewPerson("Nikita");
        assertThrows(DuplicateResourceException.class, () -> {
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

    @MethodSource
    @ParameterizedTest
    void displayEachPersonsShare(BigDecimal expectedResult, int money, List<String> names) throws NonValidArgumentException, DuplicateResourceException {
        personService.addSpend(money);
        for (String personName : names) {
            personService.addNewPerson(personName);
        }

        BigDecimal result = personService.divideAmongEveryone();

        assertEquals(expectedResult, result);
    }

    static Stream<Object[]> displayEachPersonsShare() {
        return Stream.of(
                new Object[]{new BigDecimal(0), 0, List.of()},
                new Object[]{new BigDecimal(0), 0, List.of("Daniil", "Nikita", "Oleg")},
                new Object[]{new BigDecimal(67), 200, List.of("Daniil", "Nikita", "Oleg")},
                new Object[]{new BigDecimal(50), 200, List.of("Daniil", "Nikita", "Oleg", "Maxim")},
                new Object[]{new BigDecimal(0), 789, List.of()},
                new Object[]{new BigDecimal(198), 789, List.of("Daniil", "Nikita", "Oleg", "Maxim")}
        );
    }
}
