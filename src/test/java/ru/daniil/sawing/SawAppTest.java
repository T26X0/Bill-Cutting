package ru.daniil.sawing;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SawAppTest {

    @Test
    void addNewPerson() {
        SawApp sawApp = new SawApp();
        sawApp.addNewPerson("Daniil");
        Assertions.assertEquals(
                "Daniil",
                sawApp.getAllPerson().get(0));
    }

    @Test
    void  personIsExist() {
        SawApp sawApp = new SawApp();
        sawApp.addNewPerson("Lerya");
        Assertions.assertTrue(sawApp.personIsExist("Lerya"));
    }

    @Test
    void getAllPerson() {
        SawApp sawApp = new SawApp();
        sawApp.addNewPerson("Nikita");
        sawApp.addNewPerson("Maxim");
        sawApp.addNewPerson("Igor");

        Assertions.assertEquals(3, sawApp.getAllPerson().size());
    }

    @Test
    void checkAllPersons() {
        SawApp sawApp = new SawApp("Daniil", "Igor");
        Assertions.assertTrue(sawApp.personIsExist("Daniil"));
        Assertions.assertTrue(sawApp.personIsExist("Igor"));
        Assertions.assertEquals(2, sawApp.getAllPerson().size());
    }

    @Test
    void getAllSpends() {
        SawApp sawApp = new SawApp("Daniil");
        sawApp.addGeneralSpend(299);
        sawApp.addGeneralSpend(199);
        Assertions.assertEquals(2, sawApp.getAllSpends().size());
    }

    @Test
    void getAllSpendsFor() {
        SawApp sawApp = new SawApp("Daniil");
        sawApp.addSpend("Daniil", 299);
        sawApp.addSpend("Daniil", 199);
        Assertions.assertEquals(2, sawApp.getAllSpendsFor("Daniil").size());
    }

    @Test
    void divideAmongEveryone() {
        SawApp sawApp = new SawApp("Daniil");
        sawApp.addNewPerson("Kostya");
        sawApp.addSpend("Daniil", 100);
        sawApp.addSpend("Kostya", 100);
        sawApp.addGeneralSpend(100);
        Assertions.assertEquals(150, sawApp.divideAmongEveryone());
    }

    @Test
    void personNonExist() {
        SawApp sawApp = new SawApp("Daniil");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sawApp.addSpend("Nikita", 100);
        });
    }

    @Test
    void isNonValidValueForSpend() {
        SawApp sawApp = new SawApp("Daniil");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sawApp.addGeneralSpend(-100);
        });
    }

    @Test
    void thisPersonAlreadyExist() {
        SawApp sawApp = new SawApp("Daniil");
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            sawApp.addNewPerson("Daniil");
        });
    }
}