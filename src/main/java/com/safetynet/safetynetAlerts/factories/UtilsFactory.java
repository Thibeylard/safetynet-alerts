package com.safetynet.safetynetAlerts.factories;

import com.safetynet.safetynetAlerts.factories.enums.Addresses;

import java.util.Random;

public class UtilsFactory {

    protected static final String NUMBERS = "0123456789";
    protected static final String LETTERS = "abcdefghijklmnopqrstuvwxyz";

    /**
     * Generate a random String.
     *
     * @param setOfCharacters characters composing generated string
     * @param length          length of generated string
     * @return generated String
     */
    protected static String generateString(String setOfCharacters, int length) {

        char[] result = new char[length];
        for (int i = 0; i < length; i++) {
            result[i] = setOfCharacters.charAt(getRandom().nextInt(setOfCharacters.length()));
        }

        return new String(result);
    }

    /**
     * Generate a String representing a name : Upper case for first character, length between 4 and 9.
     *
     * @return generated name String.
     */
    protected static String generateName() {
        String name = generateString(LETTERS, getRandom().nextInt(6) + 4);
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    /**
     * Return a random Address from Adresses enum.
     *
     * @return Addresses value
     */
    protected static Addresses assignAddress() {
        return Addresses.values()[getRandom().nextInt(Addresses.values().length)];
    }

    /**
     * Get new Random instance.
     *
     * @return new Random
     */
    protected static Random getRandom() {
        return new Random();
    }
}
