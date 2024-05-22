package engine;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChaosGameDescriptionTest {
    ChaosGameDescription affineDescription;
    ChaosGameDescription juliaDescription;
    List<Transform2D> affineTransforms;
    List<Transform2D> juliaTransform;
    @BeforeEach
    public void setUp(){
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);

        affineTransforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);
        Vector2D vector = new Vector2D(0, 0);
        AffineTransform2D affine = new AffineTransform2D(matrix, vector);
        affineTransforms.add(affine);
        affineDescription = new ChaosGameDescription(minCoords, maxCoords, affineTransforms);

        juliaTransform = new ArrayList<>();
        Complex point = Complex.createComplex(1, 0);
        JuliaTransform julia = new JuliaTransform(point, 1);
        juliaTransform.add(julia);
        juliaDescription = new ChaosGameDescription(minCoords, maxCoords, juliaTransform);
    }
    @Test
    public void testToStringAffine() {
        String expected =
            """
                AffineTransform2D # Type of transformation
                0.0,0.0 # Lower left
                1.0,1.0 # Upper right
                0.5,0.0,0.0,0.5,0.0,0.0 # 1st transform
                """;

        String actual = affineDescription.toString();

        assertEquals(expected, actual);
    }

    @Test
    public void testToStringJulia(){
        String expected =
            """
                JuliaTransform # Type of transformation
                0.0,0.0 # Lower left
                1.0,1.0 # Upper right
                0.5,0.0,0.0,0.5,0.0,0.0 # 1st transform
                """;

        String actual = juliaDescription.toString();
    }

    @Test
    public void testGetMinCoords() {
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D actualMinCoords = affineDescription.getMinCoords();

        assertEquals(minCoords, actualMinCoords);
    }

    @Test
    public void testGetMaxCoords() {
        Vector2D maxCoords = new Vector2D(1, 1);
        Vector2D actualMaxCoords = affineDescription.getMaxCoords();

        assertEquals(maxCoords, actualMaxCoords);
    }

    @Test
    public void testGetTransforms() {
        List<Transform2D> actualTransforms = affineDescription.getTransforms();

        assertEquals(affineTransforms, actualTransforms);
    }

    @Test
    public void setMinCoordsTest() {
        Vector2D newMinCoords = new Vector2D(1, 1);
        affineDescription.setMinCoords(newMinCoords);

        assertEquals(newMinCoords, affineDescription.getMinCoords());
    }

    @Test
    public void setMaxCoordsTest() {
        Vector2D newMaxCoords = new Vector2D(2, 2);
        affineDescription.setMaxCoords(newMaxCoords);

        assertEquals(newMaxCoords, affineDescription.getMaxCoords());
    }

    @Test
    public void setTransforms(){
        List<Transform2D> newTransforms = new ArrayList<>();
        Matrix2x2 newMatrix = new Matrix2x2(0.5, 0, 0, 0.5);
        Vector2D newVector = new Vector2D(0, 0);
        AffineTransform2D newAffine = new AffineTransform2D(newMatrix, newVector);
        newTransforms.add(newAffine);
        affineDescription.setTransforms(newTransforms);

        assertEquals(newTransforms, affineDescription.getTransforms());
    }

}