package tranformations;

import basicLinalg.Matrix2x2;
import basicLinalg.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AffineTransform2DTest {

    @Test
    void transform() {
        Matrix2x2 matrix = new Matrix2x2(1, 2, 3, 4);
        Vector2D vector = new Vector2D(7, 8);

        AffineTransform2D affine = new AffineTransform2D(matrix, vector);

        Vector2D toMultiplyWith = new Vector2D(5, 6);

        Vector2D result = affine.transform(toMultiplyWith);
        Vector2D expected = new Vector2D(24, 47);

        assertEquals(result.getX0(), expected.getX0());
        assertEquals(result.getX1(), expected.getX1());


    }
}