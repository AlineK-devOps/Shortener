package main;

import java.math.BigInteger;
import java.security.SecureRandom;

/** Вспомогательный класс **/

public class Helper {
    /** Генерирует случайную строку **/
    public static String generateRandomString(){
        return new BigInteger(130, new SecureRandom()).toString(36);
    }

    /** Выводит текст в консоль **/
    public static void printMessage(String message){
        System.out.println(message);
    }
}
