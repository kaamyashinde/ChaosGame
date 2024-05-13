package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
  private static final String FILE_PATH = "src/main/resources/";
  ArrayList<ChaosGameDescription> chaosGameDescriptions = new ArrayList<>();
  ChaosGameDescription chaosGameDescription;
  private Stage stage;

  public Controller(Stage stage) {
    this.stage = stage;
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Julia.txt"));
  }

  /**
   * Method that displays the current chaosGameDescription Object in the text fields.
   */
  public void displayCanvasCoordinates(
      int index, ChaosGameDescription inputDescription,
      TextField inputMatrixA00,
      TextField inputMatrixA01,
      TextField inputMatrixA10,
      TextField inputMatrixA11,
      TextField inputVectorB0,
      TextField inputVectorB1) {
    AffineTransform2D affine = (AffineTransform2D) inputDescription.getTransforms().get(index);
    inputMatrixA00.setText(String.valueOf(affine.getMatrix().getA00()));
    inputMatrixA01.setText(String.valueOf(affine.getMatrix().getA01()));
    inputMatrixA10.setText(String.valueOf(affine.getMatrix().getA10()));
    inputMatrixA11.setText(String.valueOf(affine.getMatrix().getA11()));
    inputVectorB0.setText(String.valueOf(affine.getVector().getX0()));
    inputVectorB1.setText(String.valueOf(affine.getVector().getX1()));

  }


  /**
   * Method that creates the buttons for the different fractals and returns them.
   *
   * @param fractalName The name of the fractal.
   * @return The button for the fractal.
   */

  public Button createPresetFractalButton(String fractalName) {
    Button button = new Button(fractalName);
    button.setOnAction(e -> {
      switch (fractalName) {
        case "Julia":
          chaosGameDescription = readChaosGameDescriptionFromFile("Julia.txt");
          chaosGameDescriptions.add(chaosGameDescription);
          break;
        case "Barnsley":
          chaosGameDescription = readChaosGameDescriptionFromFile("Barnsley.txt");
          chaosGameDescriptions.add(chaosGameDescription);
          break;
        case "Sierpinski":
          chaosGameDescription = readChaosGameDescriptionFromFile("Affine.txt");
          chaosGameDescriptions.add(chaosGameDescription);
          break;
        default:
          break;
      }
    });
    return button;
  }

  public ChaosGame returnChaosGame() {
    ChaosGameDescription desc = getChaosGameDescription();
    return new ChaosGame(desc, 1000, 1000);
  }

  /**
   * Method that reads the chaosGameDescription object from a file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH + fileName);
  }

  public ChaosGameDescription getChaosGameDescription() {
    return chaosGameDescriptions.get(chaosGameDescriptions.size() - 1);
  }


}
