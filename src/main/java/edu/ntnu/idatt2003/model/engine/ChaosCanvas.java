package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;

/**
 * A class tha represents a canvas to draw the fractals on.
 * The canvas itself is represented  as a two-dimensional table of integers.
 * The dimensions are gonna be M for the number of rows and N for the number of columns.
 * We are going to represent a blank pixel with the integer 0 and a colored pixel with the integer 1.
 * Remember that the index, i, for the rows goes downwards while the index, j, for the columns goes from let to right.
 * This is the opposite of the usual mathematical convention where the index for the rows goes upwards.
 * In order to convert this, we shall use Affine transformation.
 * <p>
 * //TODO: figure out how the version number works. coz are we talking about the version of the product or the class?
 *
 * @author 10041
 * @version 0.2.0
 * @see Matrix2x2
 * @see Vector2D
 * @see AffineTransform2D
 * @since 0.2.0
 */

public class ChaosCanvas {
    /**
     * The canvas represented as a two-dimensional table of integers.
     */
    private int[][] canvas;
    /**
     * The number of columns in the canvas (N).
     */
    private int width;
    /**
     * The number of rows in the canvas (M).
     */
    private int height;
    /**
     * The minimum coordinates of the canvas.
     */
    private Vector2D minCoords;
    /**
     * The maximum coordinates of the canvas.
     */
    private Vector2D maxCoords;
    /**
     * The affine transformation to convert coordinates to indices.
     */
    private AffineTransform2D transformCoordsToIndices;

    /**
     * Constructor for the ChaosCanvas class.
     * The constructor initialises the attributes of the class and calculates the affine transformation.
     *
     * @param inputWidth     the value of M
     * @param inputHeight    the value of N
     * @param inputMinCoords the minimum coordinates of the canvas
     * @param inputMaxCoords the maximum coordinates of the canvas
     */

    public ChaosCanvas(int inputWidth, int inputHeight, Vector2D inputMinCoords, Vector2D inputMaxCoords) {
        this.width = inputWidth;
        this.height = inputHeight;
        this.minCoords = inputMinCoords;
        this.maxCoords = inputMaxCoords;
        this.canvas = new int[height][width];
        this.transformCoordsToIndices = calculateAffineTransformation();
    }

    /**
     * The private method is used in the constructor to create an object of AffineTransform2D.
     * This is done by first initialising an object of the Matrix2D and Vector 2D class using the height, width, maxCoords and minCoords.
     *
     * @return the object of AffineTransform2D class
     */
    private AffineTransform2D calculateAffineTransformation() {
        double a01 = (height - 1) / (minCoords.getX1() - maxCoords.getX1());
        double a10 = (width - 1) / (maxCoords.getX0() - minCoords.getX0());
        double b1 = ((height - 1) * maxCoords.getX1()) / (maxCoords.getX1() - minCoords.getX1());
        double b2 = ((width - 1) * minCoords.getX0()) / (minCoords.getX0() - maxCoords.getX0());

        Matrix2x2 a = new Matrix2x2(0, a01, a10, 0);
        Vector2D b = new Vector2D(b1, b2);

        return new AffineTransform2D(a, b);

    }
    public Vector2D coordsToIndices(Vector2D coords) {
        return transformCoordsToIndices.transform(coords);
    }

    /**
     * Method to get the pixel value at a given point.
     *
     * @param point the point to get the pixel value at
     * @return the value of the pixel
     */

    public int getPixel(Vector2D point) {
        Vector2D index = coordsToIndices(point);
        int i = (int) index.getX0();
        int j = (int) index.getX1();
        return canvas[i][j];
    }

    /**
     * Method to put a pixel at a given point.
     *
     * @param point the point to put the pixel at
     */
    public void putPixel(Vector2D point) {
        Vector2D index = coordsToIndices(point);
        int i = (int) index.getX0();
        int j = (int) index.getX1();
        canvas[i][j] = 1;
    }

    /**
     * Method to get the canvas as a two-dimensional table of integers.
     *
     * @return the canvas as a two-dimensional table of integers
     */

    public int[][] getCanvasArray() {
        return canvas;
    }

    /**
     * Method to clear the canvas by setting all the pixels to 0.
     */
    public void clear() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                canvas[i][j] = 0;
            }
        }
    }
}