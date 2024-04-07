package engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChaosGameDescriptionTest {

    @Test
    public void testToString() {
        // Arrange
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);
        Vector2D vector = new Vector2D(0, 0);
        AffineTransform2D affine = new AffineTransform2D(matrix, vector);
        transforms.add(affine);
        ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);

        String expected = "AffineTransform2D # Type of transformation\n" +
                "0.0,0.0 # Lower left\n" +
                "1.0,1.0 # Upper right\n" +
                "0.5,0.0,0.0,0.5,0.0,0.0 # 1st transform\n";

        // Act
        String actual = description.toString();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void testGetMinCoords() {
        // Arrange
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);

        // Act
        Vector2D actualMinCoords = description.getMinCoords();

        // Assert
        assertEquals(minCoords, actualMinCoords);
    }

    @Test
    public void testGetMaxCoords() {
        // Arrange
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);

        // Act
        Vector2D actualMaxCoords = description.getMaxCoords();

        // Assert
        assertEquals(maxCoords, actualMaxCoords);
    }

    @Test
    public void testGetTransforms() {
        // Arrange
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);
        Vector2D vector = new Vector2D(0, 0);
        AffineTransform2D affine = new AffineTransform2D(matrix, vector);
        transforms.add(affine);
        ChaosGameDescription description = new ChaosGameDescription(minCoords, maxCoords, transforms);

        // Act
        List<Transform2D> actualTransforms = description.getTransforms();

        // Assert
        assertEquals(transforms, actualTransforms);
    }

}