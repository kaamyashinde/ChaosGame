package edu.ntnu.idatt2003.model.utils;

/**
 * Class containing static methods to validate the input of the user.
 */
public class ModelValidators {

    //Vector2D validation -- finner ut av konkrete grenseverdier senere

    /**
     * Checks if the <code>vector</code> value is within the valid range.
     *
     * @param value is a Vector2D component value
     * @param component is a string indicating whether it's x0 or x1
     * @throws IllegalArgumentException if the value is out of range
     */
    public static void validateVectorValue(double value, String component) {
        if (component.equals("x0") && (value < -500 || value > 500)) {
            throw new IllegalArgumentException("x0 must be between -500 and 500");
        } else if (component.equals("x1") && (value < -500 || value > 500)) {
            throw new IllegalArgumentException("x1 must be between -500 and 500");
        } else if (!component.equals("x0") && !component.equals("x1")) {
            throw new IllegalArgumentException("Component must be either x0 or x1");
        }
    }

    //Matrix2x2 tingz

    /**
     * Checks if the <code>matrix</code> value is within the valid range.
     *
     * @param value is a Matrix2x2 component value
     * @param component is a string indicating whether it's a00, a01, a10, or a11
     * @throws IllegalArgumentException if the value is out of range
     */
    public static void validateMatrixValue(double value, String component) {
       /* if ((component.equals("a00") || component.equals("a01") || component.equals("a10") || component.equals("a11")) && (value < -500 || value > 500)) {
            throw new IllegalArgumentException(component + " must be between -500 and 500");
        } else if (!component.equals("a00") && !component.equals("a01") && !component.equals("a10") && !component.equals("a11")) {
            throw new IllegalArgumentException("Component must be either a00, a01, a10, or a11");
        }*/
    }

    //cOMPLEX Validation

    /** - gpt tingz
     * For eksempel, i Mandel brot-settet blir komplekse tall
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
       /* if (realPart < -3.5 || realPart > 2) {
            throw new IllegalArgumentException("realPart must be between -3.5 and 2");
        }*/
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
