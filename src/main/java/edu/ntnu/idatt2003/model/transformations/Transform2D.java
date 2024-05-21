package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
/**
 * The class Transform2D represents a transformation in 2D space.
 * Responsibility: Represent a transformation in 2D space.
 * @since 0.1.0
 * @version 1.0.0
 * @see AffineTransform2D
 * @see JuliaTransform
 * @author 10041
 */

public abstract class Transform2D {

  /**
   * Method for transforming a point using the transformation.
   * @param point the point to be transformed
   * @return Vector2D The resulting point.
   */
  public abstract Vector2D transform(Vector2D point);

}
