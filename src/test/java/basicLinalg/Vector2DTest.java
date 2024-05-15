package basicLinalg;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class Vector2DTest {

    private Vector2D v1;
    private Vector2D v2;

    @BeforeEach
    public void setUp() {
        v1 = new Vector2D(1, 2);
        v2 = new Vector2D(3, 4);
    }

//positive test
    @Test
    @DisplayName("Add two vectors")
    void add() {
        Vector2D v3 = v1.add(v2);
        assertEquals(4, v3.getX0());
        assertEquals(6, v3.getX1());
    }

    @Test
    @DisplayName("Subtract two vectors")
    void subtract() {
        Vector2D v3 = v1.subtract(v2);
        assertEquals(-2, v3.getX0());
        assertEquals(-2, v3.getX1());
    }

    //negative test
    @Test
    @DisplayName("Invalid vector values")
    void invalidVectorValuesTest(){
        assertThrows(IllegalArgumentException.class, () -> {
            Vector2D invalidVector = new Vector2D(-600, 2); // x0 value is out of range
        });
    }
}