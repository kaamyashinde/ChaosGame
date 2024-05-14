package edu.ntnu.idatt2003.model.basicLinalg;

/** The class Complex represents a complex number with a real and an imaginary part. */
public class Complex extends Vector2D {

  /**
   * The constructor initializes the complex number with the given real and imaginary parts.
   *
   * @param realPart the real part
   * @param imaginaryPart the imaginary part
   */
  public Complex(double realPart, double imaginaryPart) {
    super(realPart, imaginaryPart);
  }

  /**
   * Method for getting the real part of the complex number.
   *
   * @return double The real part of the complex number.
   */
  public double getReal() {
    return getX0();
  }

  /**
   * Method for getting the imaginary part of the complex number.
   *
   * @return double The imaginary part of the complex number.
   */
  public double getImaginary() {
    return getX1();
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
    return new Complex(real, imag);
  }

  /**
   * Method for subtracting two complex numbers.
   *
   * @param other the other complex number to add to this complex number
   * @return Complex The sum of the two complex numbers.
   */
  public Complex subtract(Complex other) {
    if (other == null) {
      throw new ClassCastException("Complex number expected");
    }
    double real = getX0() - other.getX0();
    double imag = getX1() - other.getX1();
    return new Complex(real, imag);
  }

}

