package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ChaosGameFileHandler {

    public ChaosGameFileHandler() {
    }
    /*
    public ChaosGameDescription readFromFile(String inputPath) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(inputPath));
            // Assuming the file format specifies minCoords and maxCoords first, followed by transformation details
            Vector2D minCoords = parseVector2D(lines.get(1));
            Vector2D maxCoords = parseVector2D(lines.get(2));
            List<Transform2D> transforms = new ArrayList<>();
            for (int i = 2; i < lines.size(); i++) {
                transforms.add(parseTransform2D(lines.get(i)));
            }
            return new ChaosGameDescription(minCoords, maxCoords, transforms);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
*/
    public void writeToFile(String outputPath, ChaosGameDescription description) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputPath))) {

            //skal fikse det slik at det skrives bare en gang, en blanding av det som er under, og så følger det den nevnte strukturen.
            writer.write(description.getTransforms().getFirst().toString());

            /*
            for (Transform2D transform : description.getTransforms()) {
                // Serialize each Transform2D - you need to define how Transform2D is serialized
                writer.write(transform.toString()); // Assuming Transform2D has a suitable toString method
                writer.newLine();
            }
            */

            writer.write(description.getMinCoords().toString());
            writer.newLine();
            writer.write(description.getMaxCoords().toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //må fikse parsemetodene, slik at de kan parse en string til en Vector2D og en Transform2D på riktig måte
    //og så begynner tingz å utforme seg
    /*
    // Placeholder method to parse a Vector2D from a string
    private Vector2D parseVector2D(String line) {
        // Implement parsing logic based on expected format
        return new Vector2D(0, 0); // Placeholder return
    }

    // Placeholder method to parse a Transform2D from a string
    private Transform2D parseTransform2D(String line) {
        // Implement parsing logic based on expected format
        return new Transform2D(); // Placeholder return, assuming a default constructor exists
    }
    */

}
