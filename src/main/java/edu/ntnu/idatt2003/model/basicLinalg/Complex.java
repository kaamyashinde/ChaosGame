package edu.ntnu.idatt2003.model.basicLinalg;

import edu.ntnu.idatt2003.model.utils.ModelValidators;

/**
 * A class to represent a complex number that inherits the Vector2D class.
 * Apart from the constructor, it has a method to find the square root of the complex number.
 * The addition and subtraction methods are inherited from the Vector2D class.
 *
 * @see Vector2D
 * @since 0.1.0
 * @version 1.3.2
 * @author Kevin Dennis Mazali
 */
public class Complex extends Vector2D {

  /**
   * Constructor for the Complex class.
   *
   * @param inputRealPart the real part of the complex number.
   * @param inputImaginaryPart the imaginary part of the complex number.
   */
  private Complex(double inputRealPart, double inputImaginaryPart) {
    super(inputRealPart, inputImaginaryPart);
  }

  /**
   * Static factory method for creating a Complex object with validation.
   *
   * @param inputRealPart the real part of the complex number.
   * @param inputImaginaryPart the imaginary part of the complex number.
   * @return a new Complex object
   * @throws IllegalArgumentException if the input values are not valid
   */
  public static Complex createComplex(double inputRealPart, double inputImaginaryPart)
          throws IllegalArgumentException {
    ModelValidators.validateRealPartValue(inputRealPart);
    ModelValidators.validateImaginaryPartValue(inputImaginaryPart);
    return new Complex(inputRealPart, inputImaginaryPart);
  }
  /**
   * Calculates the principal square root of the complex number. The principal square root is the
   * one with the non-negative real part. If both square roots have a real part of zero, the one
   * with positive imaginary part is considered principal.
   *
   * @return Complex - The principal square root of the complex number. This is a new Complex object
   *     whose real part is the square root of the magnitude (modulus) of the original complex
   *     number multiplied by the cosine of half the angle, and whose imaginary part is the square
   *     root of the magnitude of the original complex number multiplied by the sine of half the
   *     angle.
   */

  public Complex sqrt() {
    double magnitude = Math.sqrt(Math.sqrt(Math.pow(getX0(), 2) + Math.pow(getX1(), 2)));
    double angle = Math.atan2(getX1(), getX0()) / 2.0;

    double real = magnitude * Math.cos(angle);
    double imag = magnitude * Math.sin(angle);
    return Complex.createComplex(real, imag);
  }


  /**
   * Returns a double representing the real part of the complex number.
   *
   * @return the real part of the complex number
   */

  public double getReal() {
    return this.getX0();
  }

  /**
   * Returns a double representing the imaginary part of the complex number.
   *
   * @return the imaginary part of the complex number
   */
  public double getImaginary() {
    return this.getX1();
  }

  /**
   * Method for subtracting two complex numbers.
   *
   * @param other the other complex number to subtract to this complex number
   * @return the difference between the two complex numbers.
   */

  public Complex subtract(Complex other) {
    if (other == null) {
      throw new ClassCastException("Complex number expected");
    }
    double real = getX0() - other.getX0();
    double imag = getX1() - other.getX1();
    return Complex.createComplex(real, imag);
  }

}
