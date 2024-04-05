package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;

/**
 * A class to represent a 2D affine transformation as a transformation on the form T(x) = Ax + b.
 * A is the matrix and b is the vector.
 *
 * @author 10041
 * @version 0.1.0
 * @see Transform2D
 * @see Matrix2x2
 * @see Vector2D
 * @since 0.1.0
 */

public class AffineTransform2D extends Transform2D {
    /**
     * The matrix of the affine transformation.
     */

    private Matrix2x2 matrix;
    /**
     * The vector of the affine transformation.
     */
    private Vector2D vector;

    /**
     * Constructor for the AffineTransform2D class.
     *
     * @param inputMatrix the matrix of the affine transformation
     * @param inputVector the vector of the affine transformation
     */

    public AffineTransform2D(Matrix2x2 inputMatrix, Vector2D inputVector) {
        this.matrix = inputMatrix;
        this.vector = inputVector;
    }

    /**
     * Method to transform a 2D point using the affine transformation.
     *
     * @param inputPoint the point to transform
     * @return the transformed point as an instance of Vector2D
     */
    public Vector2D transform(Vector2D inputPoint) {
        return matrix.multiply(inputPoint).add(vector);
    }

    /**
     * Method to get the matrix of the affine transformation.
     *
     * @return the matrix of the affine transformation
     */

    public Matrix2x2 getMatrix() {
        return matrix;

    }

    /**
     * Method to get the vector of the affine transformation.
     *
     * @return the vector of the affine transformation
     */

    public Vector2D getVector() {
        return vector;

    }
}
