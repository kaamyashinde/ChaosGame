package edu.ntnu.idatt2003.model.utils;

/**
 * Class containing static methods to validate the input of the user.
 */
public class ModelValidators {

    public static void validateVectorValue(double value) {
        if (value < -1000 || value > 1000) {
            throw new IllegalArgumentException("Value must be between -500 and 500");
        }
    }

    /**
     * Checks if the <code>complex</code> is null.
     *
     * @param realPart is a Complex object
     * @throws IllegalArgumentException if the complex is null
     */
    public static void validateRealPartValue(double realPart) {
       if (realPart < -10|| realPart > 5) {
            throw new IllegalArgumentException("realPart must be between -10 and 5");
        }
    }

    /**
     * Checks if the <code>complex</code> is null.
     *
     * @param imaginaryPart is a Complex object
     * @throws IllegalArgumentException if the complex is null
     */
    public static void validateImaginaryPartValue(double imaginaryPart) {
       if (imaginaryPart < -10 || imaginaryPart > 5) {
            throw new IllegalArgumentException("imaginaryPart must be between -1.5 and 1.5");
        }
    }

}
