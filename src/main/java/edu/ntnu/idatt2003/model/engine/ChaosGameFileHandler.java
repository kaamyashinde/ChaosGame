package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;


import java.io.*;
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
     * Writes a chaos game description to a file.
     * <p>
     * If the path does not exist, the method will create a new file.
     * @param chaosGameDescription the chaos game description to write
     * @param path the path to the file to write to
     */
public static void writeToFile(ChaosGameDescription chaosGameDescription, String path) {
    if (chaosGameDescription == null) {
        System.out.println("ChaosGameDescription is null. Please initialize it before calling writeToFile().");
        return;
    }
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
        boolean juliaTransformWritten = false;
        for (Transform2D transform : chaosGameDescription.getTransforms()) {
            if (transform instanceof AffineTransform2D) {
                AffineTransform2D affine = (AffineTransform2D) transform;
                Matrix2x2 matrix = affine.getMatrix();
                Vector2D vector = affine.getVector();
                writer.write(matrix.getA00() + "," + matrix.getA01() + "," + matrix.getA10() + "," + matrix.getA11() + "," + vector.getX0() + "," + vector.getX1());
                writer.newLine();
            } else if (transform instanceof JuliaTransform && !juliaTransformWritten) {
                JuliaTransform julia = (JuliaTransform) transform;
                Complex point = julia.getPoint();
                writer.write(point.getReal() + "," + point.getImaginary() + " # Real and imaginary parts of the constant c");
                writer.newLine();
                juliaTransformWritten = true;
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
    /**
     * Reads a chaos game description from a file.
     *
     * @param path the path to the file to read from
     * @return the chaos game description read from the file
     */
    public static ChaosGameDescription readFromFile(String path) {
        File file = new File(path);
        try (Scanner scanner = new Scanner(file)) {
            // Check if the file is empty
            if (!scanner.hasNextLine()) {
                // The file is empty
                // Return a default ChaosGameDescription or null
                return null;
            }

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
                    Complex point = new Complex(Double.parseDouble(transformParts[0].trim()), Double.parseDouble(transformParts[1].trim()));
                    transforms.add(new JuliaTransform(point, 1));
                    transforms.add(new JuliaTransform(point, -1));
                }
            }

            return new ChaosGameDescription(minCoords, maxCoords, transforms);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + path + ". Please try again.");
            return null;
        }
    }
    /**
     * Method that clears the contents of a file.
     */
    public static void clearFileContent(String filePath) {
        try {
            PrintWriter writer = new PrintWriter(new File(filePath));
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
            e.printStackTrace();
        }
    }

}
