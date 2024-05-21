package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
/**
 * The class Transform2D represents a transformation in 2D space.
 * Responsibility: Represent a transformation in 2D space.
 */

public abstract class Transform2D {

  public abstract Vector2D transform(Vector2D point);

}
