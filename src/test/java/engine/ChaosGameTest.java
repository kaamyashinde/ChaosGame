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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        chaosGame = new ChaosGame(chaosGameDescription, 60, 20);
    }

    @Test
    void getCanvas() {
        ChaosCanvas canvas = chaosGame.getCanvas();
        assertNotNull(canvas);
    }


    @Test
    void runSteps() {
        int numOfSteps = 10;
        chaosGame.runSteps(numOfSteps);

        ChaosCanvas canvas = chaosGame.getCanvas();
        int[][] canvasArray = canvas.getCanvasArray();
        int numberOfPixels = 0;
        for (int i = 0; i < canvasArray.length; i++) {
            for (int j = 0; j < canvasArray[i].length; j++) {
                System.out.print(canvasArray[i][j] == 0 ? " " : "*");
                if (canvasArray[i][j] == 1){
                numberOfPixels++;

                }

            }
            System.out.println(" ");
        }
        assertEquals(numOfSteps, numberOfPixels);

    }
}