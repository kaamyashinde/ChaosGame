package basicLinalg;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComplexTest {

    /**
     * Testing the square root method in the Complex class.
     */
    @Test
    @DisplayName("Square root of a complex number")
    void sqrt(){
        Complex c1 = new Complex(4, 4);
        Complex c2 = c1.sqrt();

        double expectedR = Math.sqrt(32);

        double expectedX0 = (Math.sqrt(expectedR) ) * Math.cos(Math.PI / 8);
        double expectedX1 = (Math.sqrt(expectedR) ) * Math.sin(Math.PI / 8);

        assertEquals(expectedX0, c2.getX0());
        assertEquals(expectedX1, c2.getX1());

    }


}