package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class InitateTransformations {
    public static Vector2D[] coordsForTransformation(double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        return new Vector2D[]{minCoords, maxCoords};
    }
    public static List<Transform2D> listOfTransformationsAffineGeneration(int numberOfTransformations, double matrix00, double matrix01, double matrix10, double matrix11){
        Matrix2x2 matrix = new Matrix2x2(matrix00, matrix01, matrix10, matrix11);
        List<Transform2D> transforms = new ArrayList<>();
        for (int i = 0; i < numberOfTransformations; i++) {
            double yComponent = (i == 1) ? 0.5 : 0; // Change the y-component for the second transformation
            Vector2D vector = new Vector2D(i * 0.25, yComponent);
            AffineTransform2D affine = new AffineTransform2D(matrix, vector);
            transforms.add(affine);
        }
        return transforms;
    }
    public static List<Transform2D> listOfTransformationsJuliaGeneration(int numberOfTransformations){
        List<Transform2D> transforms = new ArrayList<>();
        for (int i = 0; i < numberOfTransformations; i++) {
            double realPart = -.74543 + i * 0.01; // Change the real part for each transformation
            double imaginaryPart = .11301 + i * 0.01; // Change the imaginary part for each transformation
            Complex point = new Complex(realPart, imaginaryPart);
            JuliaTransform julia = new JuliaTransform(point, 1);
            transforms.add(julia);
        }
        return transforms;
    }
}
