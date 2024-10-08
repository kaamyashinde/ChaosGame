package edu.ntnu.idatt2003.model.basicLinalg;

import edu.ntnu.idatt2003.model.utils.ModelValidators;

/**
 * A class to represent a 2D vector.
 * The class has accessor methods for the x and y components,
 * and methods to add and subtract vectors.
 *
 * @since 0.1.0
 * @version 1.1.2
 * @author Kaamya Shinde
 *
 */
public class Vector2D {
  private final double x0;
  private final double x1;


  /**
   * Constructor for the Vector 2D class.
   *
   * @param inputX0 the x component of the vector
   * @param inputX1 the y component of the vector
   */
  public Vector2D(double inputX0, double inputX1)
      throws IllegalArgumentException {
    ModelValidators.validateVectorValue(inputX0);
    ModelValidators.validateVectorValue(inputX1);
    this.x0 = inputX0;
    this.x1 = inputX1;
  }

  /**
   * Accessor method for the x component of the vector.
   *
   * @return the x component of the vector
   */
  public double getX0() {
    return this.x0;
  }

  /**
   * Accessor method for the y component of vector.
   * @return the y component of the vector
   */
  public double getX1() {
    return this.x1;
  }

  /**
   * Method to add two vectors together.
   *
   * @param inputVector2D the vector to add to this vector
   * @return the sum of the two vectors as an instance of Vector2D
   */
  public Vector2D add(Vector2D inputVector2D) {
    return new Vector2D(this.x0 + inputVector2D.x0, this.x1 + inputVector2D.x1) {
    };
  }

  /**
   * Method to subtract two vectors together.
   *
   * @param inputVector2D the vector to subtract to this vector
   * @return the difference of the two vectors as an instance of Vector2D
   */
  public Vector2D subtract(Vector2D inputVector2D) {
    return new Vector2D(this.x0 - inputVector2D.x0, this.x1 - inputVector2D.x1) {
    };
  }

}