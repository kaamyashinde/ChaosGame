package edu.ntnu.idatt2003.model.utils;

/**
 * Class containing static methods to validate the input of the user.
 */
public class ModelValidators {

    //Vector2D validation -- finner ut av konkrete grenseverdier senere

    /**
     * Checks if the <code>vector</code> is null.
     *
     * @param x0 is a Vector2D object
     * @throws IllegalArgumentException if the vector is null
     */
    public static void validatex0Value(double x0) {
        if (x0 < 0 || x0 > 1) {
            throw new IllegalArgumentException("x0 must be between 0 and 1");
        }
    }

    /**
     * Checks if the <code>vector</code> is null.
     *
     * @param x1 is a Vector2D object
     * @throws IllegalArgumentException if the vector is null
     */
    public static void validatex1Value(double x1) {
        if (x1 < 0 || x1 > 1) {
            throw new IllegalArgumentException("x1 must be between 0 and 1");
        }
    }

    // Matrix2x2
    //tallene av -10 og 10 kommer fra gpt
    /**
     * Checks if the <code>matrix</code> is null.
     *
     * @param a00 is a Matrix2x2 object
     * @throws IllegalArgumentException if the matrix is null
     */
    public static void validateA00Value(double a00) {
        if (a00 < -10 || a00 > 10) {
            throw new IllegalArgumentException("a00 must be between -10 and 10");
        }
    }

    /**
     * Checks if the <code>matrix</code> is null.
     *
     * @param a01 is a Matrix2x2 object
     * @throws IllegalArgumentException if the matrix is null
     */
    public static void validateA01Value(double a01) {
        if (a01 < -10 || a01 > 10) {
            throw new IllegalArgumentException("a01 must be between -10 and 10");
        }
    }

    /**
     * Checks if the <code>matrix</code> is null.
     *
     * @param a10 is a Matrix2x2 object
     * @throws IllegalArgumentException if the matrix is null
     */
    public static void validateA10Value(double a10) {
        if (a10 < -10 || a10 > 10) {
            throw new IllegalArgumentException("a10 must be between -10 and 10");
        }

    }

    /**
     * Checks if the <code>matrix</code> is null.
     *
     * @param a11 is a Matrix2x2 object
     * @throws IllegalArgumentException if the matrix is null
     */
    public static void validateA11Value(double a11) {
        if (a11 < -10 || a11 > 10) {
            throw new IllegalArgumentException("a11 must be between -10 and 10");
        }
    }

    //Complex validation

    /** - gpt tingz
     * For eksempel, i Mandelbrot-settet blir komplekse tall
     * 𝑐
     * c vanligvis begrenset til et rektangel definert av realdelen fra -2.5 til 1 og den imaginære delen fra -1 til 1.
     */

    /**
     * Checks if the <code>complex</code> is null.
     *
     * @param realPart is a Complex object
     * @throws IllegalArgumentException if the complex is null
     */
    public static void validateRealPartValue(double realPart) {
        if (realPart < -2.5 || realPart > 1) {
            throw new IllegalArgumentException("realPart must be between -2.5 and 1");
        }
    }

    /**
     * Checks if the <code>complex</code> is null.
     *
     * @param imaginaryPart is a Complex object
     * @throws IllegalArgumentException if the complex is null
     */
    public static void validateImaginaryPartValue(double imaginaryPart) {
        if (imaginaryPart < -1 || imaginaryPart > 1) {
            throw new IllegalArgumentException("imaginaryPart must be between -1 and 1");
        }
    }

}
