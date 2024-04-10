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
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A class to handle reading and writing of chaos game descriptions to and from files.
 */

public class ChaosGameFileHandler {

    /**
     * Constructor for the ChaosGameFileHandler class.
     */
    public ChaosGameFileHandler() {
    }

    /**
     * Method to write a chaos game description to a file.
     *
     * If the path does not exist, the method will create a new file.
     * @param chaosGameDescription the chaos game description to write
     * @param path the path to the file to write to
     */
    public static void writeToFile(ChaosGameDescription chaosGameDescription, String path) {
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

    /**
     * Method to initiate a chaos game description with affine transformations and write it to a file.
     *
     * @param path the path to the file to write to
     * @param numberOfTransformations the number of transformations to create
     */
    public static ChaosGameDescription initiateTransformationsAffine(String path, int numberOfTransformations){
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5);

        // Create 3 transformations with slightly different vectors
        for (int i = 0; i < numberOfTransformations; i++) {
            double yComponent = (i == 1) ? 0.5 : 0; // Change the y-component for the second transformation
            Vector2D vector = new Vector2D(i * 0.25, yComponent);
            AffineTransform2D affine = new AffineTransform2D(matrix, vector);
            transforms.add(affine);
        }
        return new ChaosGameDescription(minCoords, maxCoords, transforms);

    }

    //Må lese nærmere på hvordan JuliaTranformations fungerer konkret

    /**
     * Method to initiate a chaos game description with Julia transformations and write it to a file.
     *
     * @param path the path to the file to write to
     * @param numberOfTransformations the number of transformations to create
     */
    public static ChaosGameDescription initiateTransformationsJulia(String path, int numberOfTransformations){
        Vector2D minCoords = new Vector2D(-1.6, -1);
        Vector2D maxCoords = new Vector2D(1.6, 1);
        List<Transform2D> transforms = new ArrayList<>();

        // Create 3 JuliaTransforms with slightly different complex points
        for (int i = 0; i < numberOfTransformations; i++) {
            double realPart = -.74543 + i * 0.01; // Change the real part for each transformation
            double imaginaryPart = .11301 + i * 0.01; // Change the imaginary part for each transformation
            Complex point = new Complex(realPart, imaginaryPart);
            JuliaTransform julia = new JuliaTransform(point, 1);
            transforms.add(julia);
        }
        return new ChaosGameDescription(minCoords, maxCoords, transforms);
    }

    /**
     * Method to read a chaos game description from a file.
     *
     * @param path the path to the file to read from
     * @return the chaos game description read from the file
     */
    public static ChaosGameDescription readFromFile(String path) {
        File file = new File(path);
        try (Scanner scanner = new Scanner(file)) {
            // Read the type of transformation
            String transformType = scanner.nextLine().split("#")[0].trim();

            // Read the coordinates of the lower left corner
            String[] minCoordsParts = scanner.nextLine().split("#")[0].trim().split(",");
            Vector2D minCoords = new Vector2D(Double.parseDouble(minCoordsParts[0]), Double.parseDouble(minCoordsParts[1]));

            // Read the coordinates of the upper right corner
            String[] maxCoordsParts = scanner.nextLine().split("#")[0].trim().split(",");
            Vector2D maxCoords = new Vector2D(Double.parseDouble(maxCoordsParts[0]), Double.parseDouble(maxCoordsParts[1]));

            // Read the parameters of each transformation
            List<Transform2D> transforms = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String[] transformParts = scanner.nextLine().split("#")[0].trim().split(",");
                if (transformType.equals("AffineTransform2D")) {
                    Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(transformParts[0]), Double.parseDouble(transformParts[1]), Double.parseDouble(transformParts[2]), Double.parseDouble(transformParts[3]));
                    Vector2D vector = new Vector2D(Double.parseDouble(transformParts[4]), Double.parseDouble(transformParts[5]));
                    transforms.add(new AffineTransform2D(matrix, vector));
                } else if (transformType.equals("JuliaTransform")) {
                    Complex point = new Complex(Double.parseDouble(transformParts[0]), Double.parseDouble(transformParts[1]));
                    transforms.add(new JuliaTransform(point, 1));
                }
            }

            return new ChaosGameDescription(minCoords, maxCoords, transforms);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path);
            return null;
        }
    }


    public static void main(String[] args) {


        //det som skjer her er at jeg skriver en tekstfil med en affinetransformasjon, og så leser jeg den samme filen og skriver den til en annen fil

        //samtidig som jeg skriver en fil med en juliatransformasjon
        ChaosGameFileHandler.initiateTransformationsAffine("src/main/java/edu/ntnu/idatt2003/resources/testAffine.txt", 3);
        ChaosGameFileHandler.initiateTransformationsJulia("src/main/java/edu/ntnu/idatt2003/resources/testJulia.txt", 3);
        ChaosGameDescription testObject =  ChaosGameFileHandler.readFromFile("src/main/java/edu/ntnu/idatt2003/resources/testAffine.txt");
        System.out.println(testObject);
        ChaosGameFileHandler.initiateTransformationsAffine("src/main/java/edu/ntnu/idatt2003/resources/ReadToWrite.txt", 3);

    }


}
