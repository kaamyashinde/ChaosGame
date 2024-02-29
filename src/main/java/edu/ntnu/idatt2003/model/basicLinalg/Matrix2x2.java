package edu.ntnu.idatt2003.model.basicLinalg;

/**
 * Class representing a 2x2 matrix.
 *
 * <p> contains a constructor to create a 2x2 matrix object with associated attributes.
 * The class also includes a method to multiply the matrix with a vector.
 *
 */
public class Matrix2x2 {

    private double a00;

    private double a01;

    private double a10;

    private double a11;

    /**
     * Constructor for the Matrix2x2 class
     */
    public Matrix2x2(double inputA00, double inputA01, double inputA10, double inputA11) {
        this.a00 = inputA00;
        this.a01 = inputA01;
        this.a10 = inputA10;
        this.a11 = inputA11;


    }

    /**
     *Multiplies a matrix with a vector
     *
     * @param vector the vector to multiply with the matrix
     * @return Vector2D
     */
    public Vector2D multiply(Vector2D vector) {
        double x = a00 * vector.getX0()+ a01 * vector.getX1();
        double y = a10 * vector.getX0() + a11 * vector.getX1();
        return new Vector2D(x, y);
    }


}
