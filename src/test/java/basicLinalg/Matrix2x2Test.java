package basicLinalg;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Matrix2x2Test {
    private Matrix2x2 matrix;
    private Vector2D vector;

    @BeforeEach
    public void setUp() {
        matrix = new Matrix2x2(1, 2, 3, 4);
        vector = new Vector2D(5, 6);
    }

    //positive test
    /**
     * Testing the multiply method.
     */
    @Test
    @DisplayName("Testing the multiply method")
    void testMultiply() {
        Vector2D result = matrix.multiply(vector);
        assertEquals(17, result.getX0());
        assertEquals(39, result.getX1());
    }

}