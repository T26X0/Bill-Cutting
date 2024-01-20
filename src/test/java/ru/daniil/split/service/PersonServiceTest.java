package ru.daniil.split.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.daniil.split.exceptions.DuplicateResourceException;
import ru.daniil.split.exceptions.NonValidArgumentException;
import ru.daniil.split.model.Person;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    void addNewValidSpends() throws NonValidArgumentException, DuplicateResourceException {
        personService.addNewPerson("Pavel");
        personService.addSpend("Pavel", NEW_VALID_SPEND);

        assertEquals(new BigDecimal(NEW_VALID_SPEND), personService.getAllSpends());
    }

    @Test
    void addNewNegativeSpends() {
        assertAll(
                () -> assertThrows(NonValidArgumentException.class, () -> {
                    personService.addSpend("Daniil", NEW_NEGATIVE_SPEND);
                }),
                () -> assertThrows(NonValidArgumentException.class, () -> {
                    personService.addSpend("Daniil", 0);
                })
        );
    }

    @MethodSource("provideAppliesToTest")
    @ParameterizedTest
    void displayEachPersonsShare(BigDecimal expectedResult, List<Integer> spends, List<String> names)
            throws NonValidArgumentException, DuplicateResourceException {

        for (int i = 0; i < names.size(); i++) {
            personService.addNewPerson(names.get(i));
            if (spends.get(i) != 0) personService.addSpend(names.get(i), spends.get(i));
        }

        BigDecimal result = personService.divideAmongEveryone();

        assertEquals(expectedResult, result);
    }

    private static Stream<Arguments> provideAppliesToTest() {
        return Stream.of(
                Arguments.of(new BigDecimal(200),
                        List.of(100, 200, 300),
                        List.of("Daniil", "Nikita", "Oleg")),
                Arguments.of(new BigDecimal(150),
                        List.of(100, 200, 300, 0),
                        List.of("Daniil", "Nikita", "Oleg", "Maxim")),
                Arguments.of(new BigDecimal(250),
                        List.of(100, 200, 300, 400),
                        List.of("Daniil", "Nikita", "Oleg", "Maxim"))
        );
    }
}
