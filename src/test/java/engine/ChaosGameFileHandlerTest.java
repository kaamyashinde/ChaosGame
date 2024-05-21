package engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChaosGameFileHandlerTest {

    @Test
    public void testReadWriteString() {

        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);
        Vector2D vector = new Vector2D(0, 0);
        AffineTransform2D affine = new AffineTransform2D(matrix, vector);
        transforms.add(affine);
        ChaosGameDescription originalDescription = new ChaosGameDescription(minCoords, maxCoords, transforms);
        String path = "src/test/java/engine/readWriteTest.txt";


        ChaosGameFileHandler.writeToFile(originalDescription, path);
        ChaosGameDescription readDescription = ChaosGameFileHandler.readFromFile(path);


        assertEquals(originalDescription.toString(), readDescription.toString());
    }


}