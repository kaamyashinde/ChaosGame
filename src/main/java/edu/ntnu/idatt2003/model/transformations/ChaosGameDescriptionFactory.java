package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;

import java.util.ArrayList;
import java.util.List;

public class ChaosGameDescriptionFactory {

    /**
     * Generates the minimum and maximum coordinates for a transformation.
     *
     * @param minCoordsX0 the minimum x-coordinate
     * @param minCoordsX1 the minimum y-coordinate
     * @param maxCoordsX0 the maximum x-coordinate
     * @param maxCoordsX1 the maximum y-coordinate
     * @return an array of Vector2D objects representing the minimum and maximum coordinates
     */
    public static Vector2D[] coordsForTransformation(double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        return new Vector2D[]{minCoords, maxCoords};
    }

    public static ChaosGameDescription createAffineChaosGameDescription(int numberOfTransformations, double matrix00, double matrix01, double matrix10, double matrix11, double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        Matrix2x2 matrix = new Matrix2x2(matrix00, matrix01, matrix10, matrix11);
        List<Transform2D> transforms = new ArrayList<>();
        for (int i = 0; i < numberOfTransformations; i++) {
            double yComponent = (i == 1) ? 0.5 : 0; // Change the y-component for the second transformation
            Vector2D vector = new Vector2D(i * 0.25, yComponent);
            AffineTransform2D affine = new AffineTransform2D(matrix, vector);
            transforms.add(affine);
        }
        return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }

    public static ChaosGameDescription createAffineChaosGameDescriptionManual(int numberOfTransformations, double[][] matrices, double[][] vectors, double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        List<Transform2D> transforms = new ArrayList<>();
        for (int i = 0; i < numberOfTransformations; i++) {
            Matrix2x2 matrix = new Matrix2x2(matrices[i][0], matrices[i][1], matrices[i][2], matrices[i][3]);
            Vector2D vector = new Vector2D(vectors[i][0], vectors[i][1]);
            AffineTransform2D affine = new AffineTransform2D(matrix, vector);
            transforms.add(affine);
        }
        return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }

    public static ChaosGameDescription createJuliaChaosGameDescription(int numberOfTransformations, double realPartBase, double imaginaryPartBase, double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        List<Transform2D> transforms = new ArrayList<>();
        for (int i = 0; i < numberOfTransformations; i++) {
            double realPart = realPartBase + i * 0.01; // Change the real part for each transformation
            double imaginaryPart = imaginaryPartBase + i * 0.01; // Change the imaginary part for each transformation
            Complex point = new Complex(realPart, imaginaryPart);
            JuliaTransform julia = new JuliaTransform(point, 1);
            transforms.add(julia);
        }
        return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }

    public static ChaosGameDescription createJuliaChaosGameDescriptionManual(int numberOfTransformations, double[][] complexNumbers, int[] signs, double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        List<Transform2D> transforms = new ArrayList<>();
        for (int i = 0; i < numberOfTransformations; i++) {
            double realPart = complexNumbers[i][0];
            double imaginaryPart = complexNumbers[i][1];
            Complex point = new Complex(realPart, imaginaryPart);
            JuliaTransform julia = new JuliaTransform(point, signs[i]);
            transforms.add(julia);
        }
        return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }

    public static ChaosGameDescription createBarnsleyFernChaosGameDescription() {
        Vector2D minCoords = new Vector2D(-2.65, 0);
        Vector2D maxCoords = new Vector2D(2.65, 10);
        List<Transform2D> transforms = new ArrayList<>();

        Transform2D transform1 = new AffineTransform2D(
                new Matrix2x2(0, 0, 0, 0.16),
                new Vector2D(0, 0)
        );
        Transform2D transform2 = new AffineTransform2D(
                new Matrix2x2(0.85, 0.04, -0.04, 0.85),
                new Vector2D(0, 1.6)
        );
        Transform2D transform3 = new AffineTransform2D(
                new Matrix2x2(0.2, -0.26, 0.23, 0.22),
                new Vector2D(0, 1.6)
        );
        Transform2D transform4 = new AffineTransform2D(
                new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
                new Vector2D(0, 0.44)
        );

        transforms.add(transform1);
        transforms.add(transform2);
        transforms.add(transform3);
        transforms.add(transform4);

        return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }
}