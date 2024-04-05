package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
// src/main/java/edu/ntnu/idatt2003/model/engine/testAffine.txt


import java.util.ArrayList;
import java.util.List;

public class ChaosGameFileHandler {

    public ChaosGameFileHandler() {
    }

    public void writeToFile(ChaosGameDescription chaosGameDescription, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Check if we have transformations and write the type of the first one
            if (!chaosGameDescription.getTransforms().isEmpty()) {
                String transformType = chaosGameDescription.getTransforms().get(0).getClass().getSimpleName();
                writer.write(transformType + " # Type of transformation");
                writer.newLine();
            }

            // Write the coordinates of the lower left corner
            Vector2D minCoords = chaosGameDescription.getMinCoords();
            writer.write(minCoords.getX0() + "," + minCoords.getX1() + " # Lower left");
            writer.newLine();

            // Write the coordinates of the upper right corner
            Vector2D maxCoords = chaosGameDescription.getMaxCoords();
            writer.write(maxCoords.getX0() + "," + maxCoords.getX1() + " # Upper right");
            writer.newLine();

            // Write the parameters of each transformation
            int transformNumber = 1;
            for (Transform2D transform : chaosGameDescription.getTransforms()) {
                if (transform instanceof AffineTransform2D) {
                    AffineTransform2D affine = (AffineTransform2D) transform;
                    Matrix2x2 matrix = affine.getMatrix();
                    Vector2D vector = affine.getVector();
                    writer.write(matrix.getA00() + "," + matrix.getA01() + "," + matrix.getA10() + "," + matrix.getA11() + "," + vector.getX0() + "," + vector.getX1() + " # " + transformNumber + "st transform");
                } else if (transform instanceof JuliaTransform) {
                    JuliaTransform julia = (JuliaTransform) transform;
                    Complex point = julia.getPoint();
                    writer.write(point.getReal() + "," + point.getImaginary() + " # " + transformNumber + "st transform");
                }
                writer.newLine();
                transformNumber++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void initiateTransformationsAffine(){
        ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5); // er nå en rotasjonsmatrise, før med 1,0,0,1 var det en identitetsmatrise

        // Create 3 transformations with slightly different vectors - //vet at opp til tre er riktig, med eksempelet vi har fått fra mappe tingz
        for (int i = 0; i < 3; i++) {
            Vector2D vector = new Vector2D(i * 0.25, 0); // Change the vector for each transformation
            AffineTransform2D affine = new AffineTransform2D(matrix, vector);
            transforms.add(affine);
        }

        chaosGameFileHandler.writeToFile(new ChaosGameDescription(minCoords, maxCoords, transforms), "src/main/java/edu/ntnu/idatt2003/model/engine/testAffine.txt");

    }

    //101 på hvordan JuliaTranformations fungerer

    public static void iniateTransformationsJulia(){
        ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
        Vector2D minCoords = new Vector2D(-1.6, -1);
        Vector2D maxCoords = new Vector2D(1.6, 1);
        List<Transform2D> transforms = new ArrayList<>();

        // Create 3 JuliaTransforms with slightly different complex points
        for (int i = 0; i < 3; i++) {
            double realPart = -.74543 + i * 0.01; // Change the real part for each transformation
            double imaginaryPart = .11301 + i * 0.01; // Change the imaginary part for each transformation
            Complex point = new Complex(realPart, imaginaryPart);
            JuliaTransform julia = new JuliaTransform(point, 1);
            transforms.add(julia);
        }

        chaosGameFileHandler.writeToFile(new ChaosGameDescription(minCoords, maxCoords, transforms), "src/main/java/edu/ntnu/idatt2003/model/engine/testJulia.txt");
    }


    public static void main(String[] args) {

        ChaosGameFileHandler.initiateTransformationsAffine();
        ChaosGameFileHandler.iniateTransformationsJulia();
    }


}
