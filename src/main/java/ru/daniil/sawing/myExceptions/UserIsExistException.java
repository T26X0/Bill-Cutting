package ru.daniil.sawing.myExceptions;

public class UserIsExistException extends IllegalArgumentException {
    public UserIsExistException(String s) {
        super(s);
    }
}
