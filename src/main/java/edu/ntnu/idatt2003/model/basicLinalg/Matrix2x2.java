package edu.ntnu.idatt2003.model.basicLinalg;

/**
 * Class representing a 2x2 matrix.
 *
 * <p>contains a constructor to create a 2x2 matrix object with associated attributes.
 * The class also includes a method to multiply the matrix with a vector.
 *
 *
 * @since 0.1.0
 * @version 1.2.0
 * @author Kevin Dennis Mazali
 */
public class Matrix2x2 {
  private double a00;

  private double a01;

  private double a10;

  private double a11;

  /**
   * Constructor for the Matrix2x2 class.
   */
  public Matrix2x2(double inputA00, double inputA01, double inputA10, double inputA11)
      throws IllegalArgumentException {
    this.a00 = inputA00;
    this.a01 = inputA01;
    this.a10 = inputA10;
    this.a11 = inputA11;
  }

  /**
   * Multiplies a matrix with a vector.
   *
   * @param vector the vector to multiply with the matrix
   * @return Vector2D
   */
  public Vector2D multiply(Vector2D vector) {
    double x = a00 * vector.getX0() + a01 * vector.getX1();
    double y = a10 * vector.getX0() + a11 * vector.getX1();
    return new Vector2D(x, y);
  }

  /**
   * Returns a double representing the value of a00.
   *
   * @return a00 element of the matrix
   */
  public double getA00() {
    return a00;
  }

  /**
   * Returns a double representing the value of a01.
   *
   * @return a01 element of the matrix
   */
  public double getA01() {
    return a01;
  }

  /**
   * Returns a double representing the value of a10.
   *
   * @return a10 element of the matrix
   */
  public double getA10() {
    return a10;
  }

  /**
   * Returns a double representing the value of a11.
   *
   * @return a11 element of the matrix
   */
  public double getA11() {
    return a11;
  }


}
