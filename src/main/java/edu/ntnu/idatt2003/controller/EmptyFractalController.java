package edu.ntnu.idatt2003.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * This class is responsible for handling the file selection for creation of empty fractals.
 *
 * @author Kaamya Shinde
 * @version 0.2
 * @since 0.3.4
 */

public class EmptyFractalController {
  private boolean isAffine;
  private final GameController gameController;

  /**
   * Constructor that initializes the boolean value to true and the game controller.
   */
  public EmptyFractalController() {
    isAffine = false;
    gameController = GameController.getInstance();
  }

  /**
   * Method that switches the fractal type.
   *
   * @return The boolean value of the fractal type.
   */
  private boolean switchFractalType() {
    return isAffine = !isAffine;
  }
  /**
   * Method that creates the affine transformation.
   *
   * @param inputFileName           The name of the file that is to be created.
   * @param numberOfTransformations The text field that takes the number of transformations for the affine transformation.
   */
  private void affineCreation(String inputFileName, TextField numberOfTransformations) {
    ValidationController.validateInteger(numberOfTransformations.getText());
    int num = Integer.parseInt(numberOfTransformations.getText());
    gameController.createEmptyFractal(true, num, inputFileName);
  }
  /**
   * Method that creates the Julia transformation.
   *
   * @param inputFileName The name of the file that is to be created.
   */
  private void juliaCreation(String inputFileName) {
    gameController.createEmptyFractal(false, 0, inputFileName);

  }
  /**
   * Method that switches the fractal to be created.
   *
   * @param switchButton            The button that switches the fractal type.
   * @param container             The left body row of the scene where the button is added to or removed from.
   * @param numberOfTransformations The text field that takes the number of transformations for the affine transformation.
   */
  public void switchFractalToBeCreated(Button switchButton, Button registerFileButton, VBox container, TextField numberOfTransformations) {
    isAffine = switchFractalType();
    switchButton.setText("Switch to " + (isAffine ? "Julia" : "Affine"));
    registerFileButton.setText("Register " + (isAffine ? "Affine" : "Julia") + " file");
    if (!isAffine) {
      container.getChildren().remove(numberOfTransformations);
    } else {
      container.getChildren().add(numberOfTransformations);
    }
  }
  /**
   * Method that toggles between the creation of transformations.
   *
   * @param fileName                The text field that takes the file name.
   * @param numberOfTransformations The text field that takes the number of transformations for the affine transformation.
   */
  public void toggleBetweenTheCreationOfTransformations(TextField fileName, TextField numberOfTransformations) {
    String nameOfFile = fileName.getText();
    ValidationController.validateFileName(nameOfFile);
    if (isAffine) {
      affineCreation(nameOfFile, numberOfTransformations);
    } else {
      juliaCreation(nameOfFile);
    }
  }
}
