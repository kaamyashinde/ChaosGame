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
        int pixel = canvasToTest.getPixel(new Vector2D(0,0));
        assertEquals(1, pixel);
    }

    @Test
    void getCanvasArray() {
        int[][] expectedCanvas = canvasToTest.getCanvasArray();
        int[][] resultOfCanvas = new int[5][5];
        resultOfCanvas[0][0] = 1;
        canvasToTest.putPixel(new Vector2D(0,0));
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