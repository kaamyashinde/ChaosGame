package basicLinalg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * JUnit tests for the class Vector2D. The tests are for the methods that add and subtract vectors.
 */
class Vector2DTest {

    private Vector2D v1;
    private Vector2D v2;

    /**
     * Creates instances of the object Vector2D for testing.
     */
    @BeforeEach
    public void setUp() {
        v1 = new Vector2D(1, 2);
        v2 = new Vector2D(3, 4);
    }

    /**
     * Testing the add method in the Vector2D class.
     */
    @Test
    @DisplayName("Add two vectors")
    void add() {
        Vector2D v3 = v1.add(v2);
        assertEquals(4, v3.getX0());
        assertEquals(6, v3.getX1());
    }

    /**
     * Testing the subtract method in the Vector2D class.
     */
    @Test
    @DisplayName("Subtract two vectors")
    void subtract() {
        Vector2D v3 = v1.subtract(v2);
        assertEquals(-2, v3.getX0());
        assertEquals(-2, v3.getX1());
    }

}