package ru.daniil.split.dao;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.daniil.split.exceptions.DuplicateResourceException;

import java.io.*;
import java.math.BigDecimal;
import java.util.Set;

public class JsonPersonDAO implements PersonDAO {

    private final String JSON_PATH;
    private final Model BASIC_MODEL;
    private final ObjectMapper objectMapper;
    private Model model;

    public JsonPersonDAO() {
        JSON_PATH = "src/main/resources/json-data.json";
        BASIC_MODEL = new Model();
        objectMapper = new ObjectMapper();
    }

    private Model readDataFromJson() throws IOException {
        return objectMapper.readValue(new File(JSON_PATH), Model.class);
    }

    private void writeDataInJson(Model model) throws IOException {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.writeValue(new File(JSON_PATH), model);
    }

    @Override
    public void insert(String personName) throws DuplicateResourceException, IOException {
        model = readDataFromJson();
        if (model.getPersonNames().contains(personName)) {
            throw new DuplicateResourceException("Such a record already exists");
        }
        model.addNewPerson(personName);
        writeDataInJson(model);
    }

    @Override
    public Set<String> getAllPersonNames() throws IOException {
        model = readDataFromJson();
        return model.getPersonNames();
    }

    @Override
    public int getPersonCount() throws IOException {
        model = readDataFromJson();
        return model.getPersonNames().size();
    }

    @Override
    public void addSpend(int newSpend) throws IOException {
        model = readDataFromJson();
        model.addNewSpend(new BigDecimal(newSpend));
        writeDataInJson(model);
    }

    @Override
    public int getSpends() throws IOException {
        model = readDataFromJson();

        if (model.getAllSpends() == null) {
            return 0;
        }
        return model.getAllSpends().intValue();
    }

    @Override
    public void reset() throws IOException {
        writeDataInJson(BASIC_MODEL);
    }
}
