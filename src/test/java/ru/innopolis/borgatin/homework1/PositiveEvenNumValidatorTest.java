package ru.innopolis.borgatin.homework1;

import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;


/**
 * Created by innopolis on 13.10.16.
 */
public class PositiveEvenNumValidatorTest {
    private static Logger logger = LoggerFactory.getLogger(PositiveEvenNumValidatorTest.class);
    private Validator validator;

    @Before
    public void beforeTest(){
        validator = new PositiveEvenNumValidator();

    }

    @Test
    public void isValidTest(){
        for (int i = 2; i<=100; i+=2){
            try {
                assertTrue("Validator test not complite", validator.isValid(i));
            } catch (IllegalResourceException e) {
                e.printStackTrace();
                assertTrue("Validator test not complite", false);
            }
        }


        for (int i = 1; i<100; i+=2){
            try {
                assertFalse("Validator test not complite", validator.isValid(i));
            } catch (IllegalResourceException e) {
                e.printStackTrace();
                assertTrue("Validator test not complite", false);
            }
        }


        for (int i = -100; i<=0; i++){
            try {
                assertFalse("Validator test not complite", validator.isValid(i));
            } catch (IllegalResourceException e) {
                e.printStackTrace();
                assertTrue("Validator test not complite", false);
            }
        }

        for (double d : new double[]{1.23, 2.2,10.2}){
            try {
                assertFalse("Validator test not complite", validator.isValid(d));
            } catch (IllegalResourceException e) {
                e.printStackTrace();
                assertTrue("Validator test not complite", false);
            }
        }




    }


}
