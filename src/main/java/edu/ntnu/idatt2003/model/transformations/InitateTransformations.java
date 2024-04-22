package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * A class to initiate transformations for the Chaos Game.
 */
public class InitateTransformations {

    /**
     * Generates the minimum and maximum coordinates for a transformation.
     *
     * @param minCoordsX0 the minimum x-coordinate
     * @param minCoordsX1 the minimum y-coordinate
     * @param maxCoordsX0 the maximum x-coordinate
     * @param maxCoordsX1 the maximum y-coordinate
     * @return an array of Vector2D objects representing the minimum and maximum coordinates
     */
    /*
    public static Vector2D[] coordsForTransformation(double minCoordsX0, double minCoordsX1, double maxCoordsX0, double maxCoordsX1){
        Vector2D minCoords = new Vector2D(minCoordsX0, minCoordsX1);
        Vector2D maxCoords = new Vector2D(maxCoordsX0, maxCoordsX1);
        return new Vector2D[]{minCoords, maxCoords};
    }
    */


    /**
     * Generates a list of AffineTransform2D objects.
     *
     * @param numberOfTransformations the number of transformations to generate
     * @param matrix00 the a00 element of the transformation matrix
     * @param matrix01 the a01 element of the transformation matrix
     * @param matrix10 the a10 element of the transformation matrix
     * @param matrix11 the a11 element of the transformation matrix
     * @return a list of Transform2D objects representing the generated transformations
     */
    /*
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

    /**
     * Generates a list of JuliaTransform objects.
     *
     * @param numberOfTransformations the number of transformations to generate
     * @return a list of Transform2D objects representing the generated transformations
     */
    /*
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
    */


}