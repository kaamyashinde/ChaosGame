package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to represent a description of a chaos game.
 * The description includes the coordinates of the lower left and
 * upper right corners of the canvas, as well as a list of transformations to apply to the points.
 * The transformations can be of the type AffineTransform2D or JuliaTransform.
 * The class also has a toString method to write the description to a file.
 *
 *
 * @see AffineTransform2D
 * @see JuliaTransform
 * @see Transform2D
 * @see Vector2D
 * @since 0.2.0
 * @version 1.4.5
 * @author 10041
 * @author 10072
 */
public class ChaosGameDescription {

  private Vector2D minCoords;
  private Vector2D maxCoords;
  private List<Transform2D> transforms;

  /**
   * Constructor for the ChaosGameDescription class.
   *
   * @param minCoords the coordinates of the lower left corner of the canvas
   * @param maxCoords the coordinates of the upper right corner of the canvas
   * @param transforms the list of transformations to apply to the points
   */
  public ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords,
                              List<Transform2D> transforms) {
    this.minCoords = minCoords;
    this.maxCoords = maxCoords;
    this.transforms = transforms;
  }


  /**
   * A constructor to create a deep copy of the ChaosGameDescription object.
   */
  public ChaosGameDescription(ChaosGameDescription description) {
    this.minCoords = description.minCoords;
    this.maxCoords = description.maxCoords;
    this.transforms = new ArrayList<>(description.transforms);
  }

  /**
   * Returns a Vector2D object with the coordinates of the lower left corner of the canvas.
   *
   * @return the coordinates of the lower left corner
   */
  public Vector2D getMinCoords() {
    return minCoords;
  }

  public void setMinCoords(Vector2D minCoords) {
    this.minCoords = minCoords;
  }

  /**
   * Returns a Vector2D object with the coordinates of the upper right corner of the canvas.
   *
   * @return the coordinates of the upper right corner
   */
  public Vector2D getMaxCoords() {
    return maxCoords;
  }

  public void setMaxCoords(Vector2D maxCoords) {
    this.maxCoords = maxCoords;
  }

  /**
   * Returns the list of transformations with the parameters of the chaos game.
   *
   * @return the list of transformations
   */
  public List<Transform2D> getTransforms() {
    return transforms;
  }

  public void setTransforms(List<Transform2D> transforms) {
    this.transforms = transforms;
  }


  /**
   * Writes the description of the chaos game to a file.
   * The description includes the type of the transformations,
   * the coordinates of the lower left and upper right corners
   * of the canvas, and the parameters of each transformation.
   *
   * @return a string with the description of the chaos game
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();

    if (!transforms.isEmpty()) {
      String transformType = transforms.get(0).getClass().getSimpleName();
      sb.append(transformType).append(" # Type of transformation\n");
    }

    sb.append(minCoords.getX0()).append(",").append(minCoords.getX1()).append(" # Lower left\n");

    sb.append(maxCoords.getX0()).append(",").append(maxCoords.getX1()).append(" # Upper right\n");
    addingTransformationToStringBuilder(sb);

    return sb.toString();
  }
  /**
   * Prints the transformations for the chaos game description.
   */
  private void addingTransformationToStringBuilder(StringBuilder sb){
    int transformNumber = 1;
    for (Transform2D transform : transforms) {
      if (transform instanceof AffineTransform2D) {
        AffineTransform2D affine = (AffineTransform2D) transform;
        Matrix2x2 matrix = affine.getMatrix();
        Vector2D vector = affine.getVector();
        sb.append(matrix.getA00()).append(",").append(matrix.getA01()).append(",")
            .append(matrix.getA10()).append(",").append(matrix.getA11()).append(",")
            .append(vector.getX0()).append(",").append(vector.getX1()).append(" # ")
            .append(transformNumber).append("st transform\n");
      } else if (transform instanceof JuliaTransform) {
        JuliaTransform julia = (JuliaTransform) transform;
        Vector2D point = julia.getPoint();
        int sign = julia.getSign();
        sb.append(point.getX0()).append(",").append(point.getX1()).append(",")
            .append(sign).append(" # ").append(transformNumber).append("st transform\n");
      }
      transformNumber++;
    }
  }

  /**
   * Returns the type of the transformations in the description.
   *
   * @return The type of the transformations
   */
  public Class<?> getTransformType() {
    if (transforms.isEmpty()) {
      return null;
    }
    return transforms.get(0).getClass();
  }
}
