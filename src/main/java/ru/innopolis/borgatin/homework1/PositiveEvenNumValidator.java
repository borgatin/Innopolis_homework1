package ru.innopolis.borgatin.homework1;

/**
 * Created by innopolis on 12.10.16.
 */
public class PositiveEvenNumValidator implements Validator {



    @Override
    public boolean isValid(double checkValue) throws IllegalResourceException {
        int tempInt = (int) checkValue;
        if (checkValue - tempInt != 0.0) //если передано дробное число,
            throw new IllegalResourceException(String.format("Ресурс содержит некорректные данные - дробное число")); //выбросим исключение
        else if (tempInt > 0 && tempInt % 2 == 0) //если прочитанное число четное и положительное
        {
            return true;
        } else {
            return false;
        }
    }
}
