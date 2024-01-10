package ru.daniil.sawing.myExceptions;

public class NonValidNameException extends IllegalArgumentException {

    public NonValidNameException(String s) {
        super(s);
    }
}
