package ru.maven.jborn.exceptions;

public class UserDuplicateException extends RuntimeException {

    public UserDuplicateException() {
        System.out.println("ОШИБКА: Есть совпадения");
        ;
    }
}
