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
 *
 * @since 0.2.0
 * @version 1.4.5
 * @author 10041
 *
 */

public class ChaosGameFileHandler {

  /**
   * Constructor for the ChaosGameFileHandler class.
   */
  private ChaosGameFileHandler() {
  }

  /**
   * Writes a chaos game description to a file.
   * If the path does not exist, the method will create a new file.
   *
   * @param chaosGameDescription the chaos game description to write
   * @param path                 the path to the file to write to
   */
  public static void writeToFile(ChaosGameDescription chaosGameDescription, String path) {
    if (chaosGameDescription == null) {
      return;
    }
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
      if (!chaosGameDescription.getTransforms().isEmpty()) {
        String transformType = chaosGameDescription
            .getTransforms().get(0).getClass().getSimpleName();
        writer.write(transformType + " # Type of transformation");
        writer.newLine();
      }

      Vector2D minCoords = chaosGameDescription.getMinCoords();
      writer.write(minCoords.getX0() + "," + minCoords.getX1() + " # Lower left");
      writer.newLine();

      Vector2D maxCoords = chaosGameDescription.getMaxCoords();
      writer.write(maxCoords.getX0() + "," + maxCoords.getX1() + " # Upper right");
      writer.newLine();

      writeTheValuesForEachTransformation(chaosGameDescription, writer);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes the values for each transformation in the chaos game description.
   * @param chaosGameDescription the chaos game description to write
   * @param writer the writer to write to
   * @throws IOException if an I/O error occurs
   */

  private static void writeTheValuesForEachTransformation(ChaosGameDescription chaosGameDescription, BufferedWriter writer) throws IOException {
    boolean juliaTransformWritten = false;
    for (Transform2D transform : chaosGameDescription.getTransforms()) {
      if (transform instanceof AffineTransform2D) {
        AffineTransform2D affine = (AffineTransform2D) transform;
        Matrix2x2 matrix = affine.getMatrix();
        Vector2D vector = affine.getVector();
        writer.write(matrix.getA00() + "," + matrix.getA01() + "," + matrix.getA10()
            + "," + matrix.getA11() + "," + vector.getX0() + "," + vector.getX1());
        writer.newLine();
      } else if (transform instanceof JuliaTransform && !juliaTransformWritten) {
        JuliaTransform julia = (JuliaTransform) transform;
        Complex point = julia.getPoint();
        writer.write(point.getReal() + "," + point.getImaginary()
            + " # Real and imaginary parts of the constant c");
        writer.newLine();
        juliaTransformWritten = true;
      }
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
      if (!scanner.hasNextLine()) {
        return null;
      }

      String transformType = scanner.nextLine().split("#")[0].trim();

      String[] minCoordsParts = scanner.nextLine().split("#")[0].trim().split(",");
      Vector2D minCoords = new Vector2D(Double.parseDouble(minCoordsParts[0]),
          Double.parseDouble(minCoordsParts[1]));

      String[] maxCoordsParts = scanner.nextLine().split("#")[0].trim().split(",");
      Vector2D maxCoords = new Vector2D(Double.parseDouble(maxCoordsParts[0]),
          Double.parseDouble(maxCoordsParts[1]));

      List<Transform2D> transforms = getTransformsFromFile(scanner, transformType);

      return new ChaosGameDescription(minCoords, maxCoords, transforms);
    } catch (FileNotFoundException e) {
      System.out.println("File not found: " + path + ". Please try again.");
      return null;
    }
  }

  /**
   * Reads the transforms from a file.
   * @param scanner the scanner to read from
   * @param transformType the type of transformation
   * @return a list of transforms
   */
  private static List<Transform2D> getTransformsFromFile(Scanner scanner, String transformType) {
    List<Transform2D> transforms = new ArrayList<>();
    while (scanner.hasNextLine()) {
      String[] transformParts = scanner.nextLine().split("#")[0].trim().split(",");
      if (transformType.equals("AffineTransform2D")) {
        Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(transformParts[0]), Double.parseDouble(transformParts[1]),
            Double.parseDouble(transformParts[2]), Double.parseDouble(transformParts[3]));
        Vector2D vector = new Vector2D(Double.parseDouble(transformParts[4]),
            Double.parseDouble(transformParts[5]));
        transforms.add(new AffineTransform2D(matrix, vector));
      } else if (transformType.equals("JuliaTransform")) {
        Complex point = Complex.createComplex(Double.parseDouble(transformParts[0].trim()),
            Double.parseDouble(transformParts[1].trim()));
        transforms.add(new JuliaTransform(point, 1));
        transforms.add(new JuliaTransform(point, -1));
      }
    }
    return transforms;
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
