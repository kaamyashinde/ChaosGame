package tranformations;

import basicLinalg.Complex;
import basicLinalg.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JuliaTransformTest {

    @Test
    void transform2D() {

        // Create a JuliaTransform object
        JuliaTransform juliaTransform = new JuliaTransform(new Complex(0, 0), 1);

        // Create a Vector2D object
        Vector2D vector2D = new Vector2D(1, 1);

        // Perform the transformation
        Vector2D result = juliaTransform.Transform2D(vector2D);

        // Check the result
        assertEquals(1.0986841134678098, result.getX0());
        assertEquals(0.45508986056222733, result.getX1());


    }
}