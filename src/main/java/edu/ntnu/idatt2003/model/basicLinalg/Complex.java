package edu.ntnu.idatt2003.model.basicLinalg;

import edu.ntnu.idatt2003.model.utils.ModelValidators;

/**
 * A class to represent a complex number that inherits the Vector2D class.
 * Apart from the constructor, it has a method to find the square root of the complex number.
 * The addition and subtraction methods are inherited from the Vector2D class.
 * @since 0.1.0
 * @version 0.2.0
 * @see Vector2D
 */
public class Complex extends Vector2D {

  /**
   * Constructor for the Complex class.
   *
   * @param inputRealPart the real part of the complex number.
   * @param inputImaginaryPart the imaginary part of the complex number.
   */
  public Complex(double inputRealPart, double inputImaginaryPart) {
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
  public static Complex createComplex(double inputRealPart, double inputImaginaryPart) throws IllegalArgumentException {
    ModelValidators.validateRealPartValue(inputRealPart);
    ModelValidators.validateImaginaryPartValue(inputImaginaryPart);
    return new Complex(inputRealPart, inputImaginaryPart);
  }

  /**
   * Method to find the square root of the complex number.
   *
   * <p>The length of the complex number is found using the Pythagorean theorem,
   * and the angle is found using the arctan function.
   * The square root of the length is found, and the angle is halved to find the new angle.
   *
   * @return the square root of the complex number as an instance of Complex class.
   */
  public Complex[] sqrt() {
    double r = Math.sqrt(this.getX0() * this.getX0() + this.getX1() * this.getX1());
    double theta = Math.atan2(this.getX1(), this.getX0());
    double newR = Math.sqrt(r);
    double newTheta = theta / 2;

    double newRealPart1 = newR * Math.cos(newTheta);
    double newImaginaryPart1 = newR * Math.sin(newTheta);

    double newRealPart2 = -newR * Math.cos(newTheta);
    double newImaginaryPart2 = -newR * Math.sin(newTheta);

    return new Complex[] {
            Complex.createComplex(newRealPart1, newImaginaryPart1),
            Complex.createComplex(newRealPart2, newImaginaryPart2)
    };
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
}
