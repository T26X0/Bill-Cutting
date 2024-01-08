package ru.daniil.sawing.myExceptions;

public class PersonIsExistException extends IllegalArgumentException {
    public PersonIsExistException(String s) {
        super(s);
    }
}
