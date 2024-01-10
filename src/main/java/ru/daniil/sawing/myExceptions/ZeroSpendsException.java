package ru.daniil.sawing.myExceptions;

public class ZeroSpendsException extends IllegalArgumentException {

    public ZeroSpendsException(String s) {
        super(s);
    }
}
