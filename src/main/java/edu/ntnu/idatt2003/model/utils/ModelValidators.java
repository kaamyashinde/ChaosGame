package edu.ntnu.idatt2003.model.utils;

/**
 * Class containing static methods to validate the input of the user.
 *
 * @since 1.0.0
 * @version 1.2.3
 * @see edu.ntnu.idatt2003.model.transformations.AffineTransform2D
 * @see edu.ntnu.idatt2003.model.transformations.JuliaTransform
 * @author 10041
 */
public class ModelValidators {

  /**
   * Checks if the <code>value</code> is within a reasonable range for vector values.
   * The value must be between -1000 and 1000 inclusive.
   *
   * @param value the value to be validated
   *              A double representing the vector value to be checked.
   * @throws IllegalArgumentException if the value is not within the range -1000 to 1000 inclusive
   */
  public static void validateVectorValue(double value) {
    if (value < -1000 || value > 1000) {
      throw new IllegalArgumentException("Value must be between -1000 and 1000");
    }
  }

  /**
   * Checks if the <code>realPart</code> of a complex number is within a reasonable range.
   * The real part must be between -10 and 5 inclusive.
   *
   * @param realPart the real part to be validated
   *                 A double representing the real part of a complex number to be checked.
   * @throws IllegalArgumentException if the real part is not within the range -10 to 5 inclusive
   */
  public static void validateRealPartValue(double realPart) {
    if (realPart < -10 || realPart > 5) {
      throw new IllegalArgumentException("realPart must be between -10 and 5");
    }
  }

  /**
   * Checks if the <code>imaginaryPart</code> of a complex number is within a reasonable range.
   * The imaginary part must be between -1.5 and 1.5 inclusive.
   *
   * @param imaginaryPart the imaginary part to be validated
   *                      A double representing the imaginary part of a complex number to be checked.
   * @throws IllegalArgumentException if the imaginary part is not within the range -1.5 to 1.5 inclusive
   */
  public static void validateImaginaryPartValue(double imaginaryPart) {
    if (imaginaryPart < -7 || imaginaryPart > 7) {
      throw new IllegalArgumentException("imaginaryPart must be between 7 and 7");
    }
  }

}
