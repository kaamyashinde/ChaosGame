package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChaosCanvasTest {
    private ChaosCanvas canvasToTest;
    @BeforeEach
    void setUp(){
        canvasToTest = new ChaosCanvas(
                5, 5, new Vector2D(0, 0), new Vector2D(5, 5)
        );
    }

    @Test
    void putAndGetPixel() {
        canvasToTest.putPixel(new Vector2D(0,0));
        int pixel1 = canvasToTest.getPixel(new Vector2D(0,0));
        canvasToTest.putPixel(new Vector2D(0,2));
        int pixel2 = canvasToTest.getPixel(new Vector2D(0,2));
        assertEquals(1, pixel1);
        assertEquals(1, pixel2);
    }

    @Test
    void getCanvasArray() {
        canvasToTest.putPixel(new Vector2D(0,0));
        int[][] resultOfCanvas = canvasToTest.getCanvasArray();

        int[][] expectedCanvas = new int[5][5];
        double x0 = canvasToTest.coordsToIndices(new Vector2D(0,0)).getX0();
        double x1 = canvasToTest.coordsToIndices(new Vector2D(0,0)).getX1();
        expectedCanvas[(int)x0][(int)x1] = 1;

        assertArrayEquals(expectedCanvas, resultOfCanvas);
    }

    @Test
    void clear() {
        canvasToTest.putPixel(new Vector2D(0,0));
        canvasToTest.clear();
        int[][] canvas = canvasToTest.getCanvasArray();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(0, canvas[i][j]);
            }
        }
    }
}