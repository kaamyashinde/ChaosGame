package edu.ntnu.idatt2003.model.factory;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescriptionCreator;

/**
 * A factory class for creating default ChaosGameDescriptions.
 * The default ChaosGameDescriptions are:
 * <ul>
 *   <li>Julia Set</li>
 *   <li>Barnsley Fern</li>
 *   <li>Sierpinski Triangle</li>
 * </ul>
 *
 * @author Kaamya Shinde
 * @version 0.1
 * @see ChaosGameDescription
 * @see ChaosGameDescriptionCreator
 * @since 0.3.7
 */
public class ChaosGameDescriptionFactory {
  /**
   * Initiates the Julia set preset.
   *
   * @return an object of the ChaosGameDescription class containing
   * the coordinates and transformations for the Julia set.
   */

  public static ChaosGameDescription defaultJuliaSet() {
    Vector2D[] coords = ChaosGameDescriptionCreator.coordsForTransformation(-1.6, -1.0, 1.6, 1.0);
    return ChaosGameDescriptionCreator.createJuliaChaosGameDescription(10, -.74543,
        .11301, coords[0].getX0(), coords[0].getX1(), coords[1].getX0(), coords[1].getX1());
  }

  /**
   * Initiates the Barnsley fern preset.
   *
   * @return an object of the ChaosGameDescription class containing
   * the coordinates and transformations for the Barnsley fern.
   */
  public static ChaosGameDescription defaultBarnsleyFern() {
    return ChaosGameDescriptionCreator.createBarnsleyFernChaosGameDescription();
  }

  /**
   * Initiates the Sierpinski triangle preset.
   *
   * @return an object of the ChaosGameDescription class containing the coordinates
   * and transformations for the Sierpinski triangle.
   */
  public static ChaosGameDescription defaultSierpinskiTriangle() {
    return ChaosGameDescriptionCreator.createAffineChaosGameDescriptionManual(3,
        new double[][]{{0.5, 0, 0, 0.5}, {0.5, 0, 0, 0.5}, {0.5, 0, 0, 0.5}}, new double[][]{{0, 0}, {0, 1}, {1, 0}},
        0, 0, 1, 1);
  }

  /**
   * Initiates an empty Julia set preset.
   *
   * @return an object of the ChaosGameDescription class containing the coordinates
   * and transformations for an empty Julia set.
   */
  public static ChaosGameDescription emptyJuliaSet() {
    return ChaosGameDescriptionCreator.createJuliaChaosGameDescriptionManual(0, 0, 1, 0, 0, 0, 0);
  }

  /**
   * Initiates an empty Affine set preset.
   *
   * @return an object of the ChaosGameDescription class containing the coordinates
   * and transformations for an empty Affine set.
   */
  public static ChaosGameDescription emptyAffineSet(int numberOfTransformations) {
    double[][] matrices = new double[numberOfTransformations][4];
    double[][] vectors = new double[numberOfTransformations][2];
    return ChaosGameDescriptionCreator.createAffineChaosGameDescriptionManual(numberOfTransformations,
        matrices, vectors, 0, 0, 0, 0);
  }


}
