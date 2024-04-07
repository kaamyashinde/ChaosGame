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

    public static void initiateTransformationsAffine(String path, int numberOfTransformations){
        ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
        Vector2D minCoords = new Vector2D(0, 0);
        Vector2D maxCoords = new Vector2D(1, 1);
        List<Transform2D> transforms = new ArrayList<>();
        Matrix2x2 matrix = new Matrix2x2(0.5, 0, 0, 0.5); // er nå en rotasjonsmatrise, før med 1,0,0,1 var det en identitetsmatrise

        // Create 3 transformations with slightly different vectors - //vet at opp til tre er riktig, med eksempelet vi har fått fra mappe tingz
        for (int i = 0; i < numberOfTransformations; i++) {
            Vector2D vector = new Vector2D(i * 0.25, 0); // Change the vector for each transformation
            AffineTransform2D affine = new AffineTransform2D(matrix, vector);
            transforms.add(affine);
        }

        chaosGameFileHandler.writeToFile(new ChaosGameDescription(minCoords, maxCoords, transforms), path);

    }

    //101 på hvordan JuliaTranformations fungerer

    public static void iniateTransformationsJulia(String path, int numberOfTransformations){
        ChaosGameFileHandler chaosGameFileHandler = new ChaosGameFileHandler();
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

        chaosGameFileHandler.writeToFile(new ChaosGameDescription(minCoords, maxCoords, transforms), path);
    }


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
        ChaosGameFileHandler.initiateTransformationsAffine("src/main/java/edu/ntnu/idatt2003/model/engine/testAffine.txt", 3);
        ChaosGameFileHandler.iniateTransformationsJulia("src/main/java/edu/ntnu/idatt2003/model/engine/testJulia.txt", 3);
        ChaosGameDescription testObject =  ChaosGameFileHandler.readFromFile("src/main/java/edu/ntnu/idatt2003/model/engine/testAffine.txt");
        System.out.println(testObject);
        ChaosGameFileHandler.initiateTransformationsAffine("src/main/java/edu/ntnu/idatt2003/model/engine/ReadToWrite.txt", 3);

    }


}
