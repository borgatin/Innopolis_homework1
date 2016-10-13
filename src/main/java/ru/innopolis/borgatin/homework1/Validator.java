package ru.innopolis.borgatin.homework1;

/**
 * The <code>Validator</code> interface shoud be implemented
 * when a type must do input validation to any condition.
 * To implement <code>Validator</code>  interface need to override the
 * method <code>isValid</code>
 */
public interface Validator {
    public boolean isValid(double checkValue) throws IllegalResourceException;

}
