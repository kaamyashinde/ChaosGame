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
// src/main/java/edu/ntnu/idatt2003/model/engine/test.txt


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ChaosGameFileHandler {

    public ChaosGameFileHandler() {
    }

    public void writeToFile(ChaosGameDescription chaosGameDescription, String path) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            // Check if we have transformations and write the type of the first one
            if (!chaosGameDescription.getTransforms().isEmpty()) {
                String transformType = chaosGameDescription.getTransforms().get(0).getClass().getSimpleName();
                writer.write(transformType);
                writer.newLine();
            }

            // Write the coordinates of the lower left corner
            Vector2D minCoords = chaosGameDescription.getMinCoords();
            writer.write(minCoords.getX0() + "," + minCoords.getX1());
            writer.newLine();

            // Write the coordinates of the upper right corner
            Vector2D maxCoords = chaosGameDescription.getMaxCoords();
            writer.write(maxCoords.getX0() + "," + maxCoords.getX1());
            writer.newLine();

            // Write the parameters of each transformation
            for (Transform2D transform : chaosGameDescription.getTransforms()) {
                if (transform instanceof AffineTransform2D) {
                    AffineTransform2D affine = (AffineTransform2D) transform;
                    Matrix2x2 matrix = affine.getMatrix();
                    Vector2D vector = affine.getVector();
                    writer.write(matrix.getA00() + "," + matrix.getA01() + "," + matrix.getA10() + "," + matrix.getA11() + "," + vector.getX0() + "," + vector.getX1());
                } else if (transform instanceof JuliaTransform) {
                    JuliaTransform julia = (JuliaTransform) transform;
                    Complex point = julia.getPoint();
                    writer.write(point.getReal() + "," + point.getImaginary());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        //use the write to file method to write to a file

        ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(1, 0, 0, 1);
        Vector2D vector = new Vector2D(0, 0);

        //create a new affine transformation

        AffineTransform2D affine = new AffineTransform2D(matrix, vector);

        //add the affine transformation to the list of transformations

        transforms.add(affine);
        chaosGameFileHandler.writeToFile(new ChaosGameDescription(minCoords, maxCoords, transforms), "src/main/java/edu/ntnu/idatt2003/model/engine/test.txt");

    }


}
