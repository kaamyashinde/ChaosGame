package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.basicLinalg.Complex;

public class JuliaTransform extends Transform2D {

    private Complex point;
    private int sign;

    public JuliaTransform(Complex inputPoint, int inputSign) {
        this.point = inputPoint;
        this.sign = inputSign;
    }

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
