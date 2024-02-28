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
    public Vector2D transform(Vector2D z) {
        // Convert the Vector2D z to a Complex number
        Complex zComplex = new Complex(z.getX0(), z.getX1());

        // Perform the transformation: sqrt(p * (z - c))
        Complex temp = zComplex.subtract(point);
        Complex multiplied = temp.multiply(new Complex(sign, 0));
        Complex result = multiplied.sqrt();

        // Convert the result back to a Vector2D and return it
        return new Vector2D(result.getX0(), result.getX1());
    }


}
