package basicLinalg;

/**
 * A class to represent a complex number that inherits the Vector2D class.
 * Apart from the constructor, it has a method to find the square root of the complex number.
 * The addition and subtraction methods are inherited from the Vector2D class.
 * @since 0.1.0
 * @version 0.2.0
 * @author 10041
 */

public class Complex extends Vector2D {
  /**
   * The real part of the complex number.
   */
  private final double realPart;
  /**
   * The imaginary part of the complex number.
   */
  private final double imaginaryPart;

  /**
   * Constructor for the Complex class.
   *
   * @param inputRealPart      the real part of the complex number.
   * @param inputImaginaryPart the imaginary part of the complex number.
   */
  public Complex(double inputRealPart, double inputImaginaryPart) {
    super(inputRealPart, inputImaginaryPart);
    this.realPart = inputRealPart;
    this.imaginaryPart = inputImaginaryPart;
  }
  /**
   * The method finds the product of two complex numbers.
   * @param other instance of the Complex class.
   * @return the product of the two complex numbers as an instance of the Complex class.
   */
  public Complex multiply(Complex other){
    double real = this.realPart * other.realPart - this.imaginaryPart * other.imaginaryPart;
    double imaginary = this.realPart * other.imaginaryPart + this.imaginaryPart * other.realPart;
    return new Complex(real, imaginary);
  }

  /**
   * Method to find the square root of the complex number.
   *
   * <p>The length of the complex number is found using the Pythagorean theorem,
   * and the angle is found using the arctan function.
   * The square root of the length is found and the angle is halved to find the new angle.
   *
   * @return the square root of the complex number as an instance of Complex class.
   */
  public Complex sqrt() {
    double r = Math.sqrt(this.realPart * this.realPart + this.imaginaryPart * this.imaginaryPart);
    double theta = Math.atan2(this.imaginaryPart, this.realPart);
    double newR = Math.sqrt(r);
    double newTheta = theta / 2;

    double newRealPart = newR * Math.cos(newTheta);
    double newImaginaryPart = newR * Math.sin(newTheta);

    return new Complex(newRealPart, newImaginaryPart);
  }

  @Override
  public Complex subtract(Vector2D other) {
    // Anta at other er en instans av Complex; utfør typekontroll og konvertering om nødvendig
    if (other instanceof Complex) {
      Complex otherComplex = (Complex) other;
      double real = this.realPart - otherComplex.realPart;
      double imaginary = this.imaginaryPart - otherComplex.imaginaryPart;
      return new Complex(real, imaginary);
    } else {
      // Håndter feil eller kast en exception
      throw new IllegalArgumentException("Other must be an instance of Complex");
    }
  }

}
