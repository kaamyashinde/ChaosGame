package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.basicLinalg.Complex;

/**
 * Class representing a 2D transformation of the form T(z) = sqrt(p * (z - c)).
 * P is a complex number and c is a complex number.
 *
 * <p> The class includes a method to transform a 2D point using the transformation.
 *
 * @see Transform2D
 * @see Complex
 * @see Vector2D
 * @since 0.1.0
 */
public class JuliaTransform extends Transform2D {

    private Complex point;
    private int sign;

    /**
     * Constructor for the JuliaTransform class
     *
     * @param inputPoint the complex number c
     * @param inputSign  the sign of the transformation
     */
    public JuliaTransform(Complex inputPoint, int inputSign) {
        this.point = inputPoint;
        this.sign = inputSign;
    }

    /**
     * Transform a 2D point using the Julia transformation.
     *
     * @param point the point to transform
     * @return the transformed point as an instance of Vector2D
     */
    @Override
    public Vector2D transform(Vector2D point) {
        Complex z = new Complex(point.getX0(), point.getX1());
        Vector2D subtracted = z.subtract(this.point);
        Complex subtractedComplex = new Complex(subtracted.getX0(), subtracted.getX1());
        Complex sqrt = subtractedComplex.sqrt();

        if (this.sign == 1) {
            return new Vector2D(sqrt.getX0(), sqrt.getX1());
        } else {
            return new Vector2D(-sqrt.getX0(), -sqrt.getX1());
        }
    }


}
