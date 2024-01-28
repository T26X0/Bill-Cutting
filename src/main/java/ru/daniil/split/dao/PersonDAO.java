package ru.daniil.split.dao;

import ru.daniil.split.exceptions.DuplicateResourceException;

import java.io.IOException;
import java.util.Set;

public interface PersonDAO {

    void insert(String personName) throws DuplicateResourceException, IOException;

    Set<String> getAllPersonNames() throws IOException;

    int getPersonCount() throws IOException;

    void addSpend(int newSpend) throws IOException;

    int getSpends() throws IOException;

    void reset() throws IOException;
}
