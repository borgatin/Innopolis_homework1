package ru.innopolis.borgatin.homework1;

/**
 * Данный класс предназначен для выброса исключения при некорректно принятом ресурсе
 */
class IllegalResourceException extends Exception {

    IllegalResourceException(String message){
        System.err.println(message);
    }
}
