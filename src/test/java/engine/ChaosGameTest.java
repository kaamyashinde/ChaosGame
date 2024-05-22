package engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ChaosGameTest {
    private ChaosGame chaosGame;
    private ChaosGameDescription chaosGameDescription;
    private Vector2D minCoords;
    private Vector2D maxCoords;
    private List<Transform2D> transforms;

    @BeforeEach
    void setUp() {
        minCoords = new Vector2D(0, 0);
        maxCoords = new Vector2D(1, 1);
        transforms = List.of(
                new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0, 0)),
                new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.25, 0.5)),
                new AffineTransform2D(new Matrix2x2(0.5, 0, 0, 0.5), new Vector2D(0.5, 0)));
        chaosGameDescription = new ChaosGameDescription(minCoords, maxCoords, transforms);
        chaosGame = new ChaosGame(chaosGameDescription, 500, 500);
    }

    @Test
    void getCanvas() {
        ChaosCanvas canvas = chaosGame.getCanvas();
        assertNotNull(canvas);
    }
    @Test
    void runSteps(){
        chaosGame.runSteps(1000);
        int[][] canvasArray = chaosGame.getCanvas().getCanvasArray();
        int sum = 0;
        for (int i = 0; i < canvasArray.length; i++) {
            for (int j = 0; j < canvasArray[0].length; j++) {
                sum += canvasArray[i][j];
            }
        }
        assertTrue(sum > 0);
    }

    @Test
    void getDescriptionTest(){
        assertEquals(chaosGameDescription, chaosGame.getDescription());
    }

    @Test
    void setDescriptionTest(){
        chaosGame.setDescription(null);
        assertNull(chaosGame.getDescription());
    }

}