package ru.daniil.split.dao;

import ru.daniil.split.exceptions.DuplicateResourceException;

import java.util.Set;

public interface PersonDAO {

    void registerNewPerson(String personName) throws DuplicateResourceException;

    Set<String> getAllPersonNames();

    int getPersonCount();

    void addSpend(int newSpend);

    int getSpends();
}
