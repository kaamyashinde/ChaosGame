package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.observer.ChaosGameObserver;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import java.util.ArrayList;
import java.util.List;

/**
 * A class tha represents a canvas to draw the fractals on.
 * The canvas itself is represented  as a two-dimensional table of integers.
 * The dimensions are gonna be M for the number of rows and N for the number of columns.
 * We are going to represent a blank pixel with the
 * integer 0 and a colored pixel with the integer 1.
 * Remember that the index, i, for the rows goes downwards while the index,
 * j, for the columns goes from let to right.
 * This is the opposite of the usual mathematical convention
 * where the index for the rows goes upwards.
 * In order to convert this, we shall use Affine transformation.
 *
 *
 *
 *
 * @see Matrix2x2
 * @see Vector2D
 * @see AffineTransform2D
 * @since 0.2.0
 * @version 1.2.5
 * @author 10041
 */

public class ChaosCanvas {

  private final int[][] canvas;
  private final int width;
  private final int height;
  private final Vector2D minCoords;
  private final Vector2D maxCoords;
  private final AffineTransform2D transformCoordsToIndices;
  private final List<ChaosGameObserver> observers = new ArrayList<>();

  /**
   * Constructor for the ChaosCanvas class.
   * The constructor initialises the attributes of the class
   * and calculates the affine transformation.
   *
   * @param inputWidth     the value of M
   * @param inputHeight    the value of N
   * @param inputMinCoords the minimum coordinates of the canvas
   * @param inputMaxCoords the maximum coordinates of the canvas
   */

  public ChaosCanvas(int inputWidth, int inputHeight,
                     Vector2D inputMinCoords, Vector2D inputMaxCoords) {
    this.width = inputWidth;
    this.height = inputHeight;
    this.minCoords = inputMinCoords;
    this.maxCoords = inputMaxCoords;
    this.canvas = new int[height][width];
    this.transformCoordsToIndices = calculateAffineTransformation();
  }

  /**
   * The private method is used in the constructor to create an object of AffineTransform2D.
   * This is done by first initialising an object of the Matrix2D
   * and Vector 2D class using the height, width, maxCoords and minCoords.
   *
   * @return the object of AffineTransform2D class
   */
  private AffineTransform2D calculateAffineTransformation() {
    double a01 = (height - 1) / (minCoords.getX1() - maxCoords.getX1());
    double a10 = (width - 1) / (maxCoords.getX0() - minCoords.getX0());
    double b1 = ((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1());
    double b2 = ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0());

    Matrix2x2 a = new Matrix2x2(a10, 0, 0, a01);
    Vector2D b = new Vector2D(b2, b1);

    return new AffineTransform2D(a, b);

  }

  /**
   * The method is used to convert the coordinates of the canvas to the indices of the canvas.
   * @param coords the coordinates to convert
   * @return the indices of the canvas
   */
  public Vector2D coordsToIndices(Vector2D coords) {
    return transformCoordsToIndices.transform(coords);
  }

  /**
   * Returns the integer representation of the pixel at the given point.
   *
   * @param point the point to get the pixel at
   * @return the value of the pixel at the given point
   */

  public int getPixel(Vector2D point) {
    Vector2D index = coordsToIndices(point);
    int i = (int) index.getX0();
    int j = (int) index.getX1();
    return canvas[i][j];
  }

  /**
   * Puts a pixel at the given point.
   *
   * @param point the point to put the pixel at
   * @throws ArrayIndexOutOfBoundsException if the point is outside the canvas
   */
  public void putPixel(Vector2D point) throws ArrayIndexOutOfBoundsException {
    Vector2D index = coordsToIndices(point);
    int i = (int) index.getX0();
    int j = (int) index.getX1();
    canvas[i][j] = 1;
    notifyObserverAddPixel(i, j);


  }

  /**
   * Returns the dimension of the canvas as a two-dimensional
   * table of integers representing the height and width
   * of the canvas.
   *
   * @return the canvas as a two-dimensional table of integers
   */

  public int[][] getCanvasArray() {
    return canvas;
  }

  /**
   * Clears the canvas by setting all the pixels to 0.
   */
  public void clear() {
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        canvas[i][j] = 0;
        notifyObserverRemovePixel(i, j);
      }
    }
  }

  /**
   * Adds an observer to the list of observers.
   * @param observer the observer to add
   */
  public void addObserver(ChaosGameObserver observer) {
    observers.add(observer);
  }

  /**
   * Notifies all observers in the list of observers.
   * @param x0 the x-coordinate of the pixel
   * @param x1 the y-coordinate of the pixel
   */
  public void notifyObserverRemovePixel(double x0, double x1) {
    observers.forEach(observer -> observer.updateRemovePixel(x0, x1));
  }

  /**
   * Notifies all observers in the list of observers.
   */
  public void notifyObserverAddPixel(double x0, double x1) {
    observers.forEach(observer -> observer.updateAddPixel(x0, x1));
  }

  /**
   * Removes an observer from the list of observers.
   * @param observer the observer to remove
   */
  public void removeObserver(ChaosGameObserver observer) {
    observers.remove(observer);
  }

  /**
   * Returns the size of the observers list.
   * @return the size of the observers list
   */
  public int getObserversSize() {
    return observers.size();
  }
}