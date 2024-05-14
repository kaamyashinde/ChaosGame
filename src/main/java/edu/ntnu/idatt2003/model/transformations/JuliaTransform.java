package edu.ntnu.idatt2003.model.transformations;
import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;

/**
 * The class JuliaTransform represents a transformation in 2D space. The transformation is a square
 * root transformation of a complex number. Responsibility: Represent a transformation in 2D space.
 */
public class JuliaTransform extends Transform2D {
    private Complex point;
    private final int sign;

    /**
     * Instantiates a new Julia transform.
     *
     * @param point the point
     * @param sign the sign
     */
    public JuliaTransform(Complex point, int sign) {
        this.point = point;
        this.sign = sign;
    }

    /**
     * Method for transforming a point using the transformation. Overrides the transform method in the
     * Transform2D interface. The method transforms a point using the square root transformation of a
     * complex number.
     *
     * @param point the point to be transformed
     * @return Vector2D The resulting point.
     */
    @Override
    public Vector2D transform(Vector2D point) {
        Complex z = new Complex(point.getX0(), point.getX1());
        Complex subtracted = z.subtract(this.point);
        Complex result = subtracted.sqrt();

        if (this.sign == -1) {
            return new Vector2D(result.getReal() * -1, result.getImaginary() * -1);
        }

        return new Vector2D(result.getReal(), result.getImaginary());
    }

    public Complex getPoint() {
        return point;
    }

    public void setComplex(Complex complex) {
        this.point = complex;
    }
    public int getSign(){
        return sign;
    }
}

