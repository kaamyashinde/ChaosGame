package edu.ntnu.idatt2003.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * This class is responsible for handling the file selection for creation of empty fractals.
 *
 * @author Kaamya Shinde
 * @version 0.1
 * @since 0.3.4
 */

public class EmptyFractalController {
  Boolean isAffine;
  GameController gameController;

  /**
   * Constructor that initializes the boolean value to true and the game controller.
   */
  public EmptyFractalController() {
    isAffine = false;
    gameController = new GameController();
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
   * Method that switches the fractal to be created.
   *
   * @param switchButton            The button that switches the fractal type.
   * @param container             The left body row of the scene where the button is added to or removed from.
   * @param numberOfTransformations The text field that takes the number of transformations for the affine transformation.
   */
  public void switchFractalToBeCreated(Button switchButton, VBox container, TextField numberOfTransformations) {
    isAffine = switchFractalType();
    System.out.println("Switched to " + (isAffine ? "Affine" : "Julia"));
    System.out.println("So it should display if " + isAffine + " + isAffine");
    switchButton.setText("Switch to " + (isAffine ? "Julia" : "Affine"));
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
    System.out.println("This is the file name: " + nameOfFile);
    if (isAffine) {
      affineCreation(nameOfFile, numberOfTransformations);
    } else {
      juliaCreation(nameOfFile);
    }
  }

  /**
   * Method that creates the affine transformation.
   *
   * @param inputFileName           The name of the file that is to be created.
   * @param numberOfTransformations The text field that takes the number of transformations for the affine transformation.
   */
  private void affineCreation(String inputFileName, TextField numberOfTransformations) {
    int num = Integer.parseInt(numberOfTransformations.getText());
    System.out.println("Number of transformations: " + num);
    gameController.createEmptyFractal(true, num, inputFileName);
    System.out.println("Affine transformation created!");

  }

  /**
   * Method that creates the Julia transformation.
   *
   * @param inputFileName The name of the file that is to be created.
   */
  private void juliaCreation(String inputFileName) {
    gameController.createEmptyFractal(false, 0, inputFileName);
    System.out.println("Julia transformation created!");

  }
}
