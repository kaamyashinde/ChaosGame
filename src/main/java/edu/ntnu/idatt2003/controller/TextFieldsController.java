package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * The controller for the text fields in the GUI.
 * It is responsible for creating different types of lists for the text fields.
 *
 * @author Kaamya Shinde
 * @version 0.1
 * @since 3.2.0
 */

public class TextFieldsController {
  Vector2D minCoords;
  Vector2D maxCoords;
  /**
   * Method that creates a list containing the max and min coordinates text fields.
   *
   * @return a list containing the max and min coordinates text fields.
   */
  public List<TextField> maxAndMinCoordsTextFieldsList() {

    TextField minCoordsX0 = new TextField();
    TextField minCoordsX1 = new TextField();
    TextField maxCoordsX0 = new TextField();
    TextField maxCoordsX1 = new TextField();

    minCoordsX0.setPromptText("Min X0");
    minCoordsX1.setPromptText("Min X1");
    maxCoordsX0.setPromptText("Max X0");
    maxCoordsX1.setPromptText("Max X1");

    return List.of(minCoordsX0, minCoordsX1, maxCoordsX0, maxCoordsX1);
  }

  /**
   * Method that creates a list containing the buttons used to control the viewing of the Affine transformations.
   *
   * @return a list containing the buttons used to control the viewing of the Affine transformations.
   */
  public List<Button> affineTransformationButtonsList() {
    Button previousTransformation = new Button("Previous Transformation");
    Button nextTransformation = new Button("Next Transformation");
    return List.of(previousTransformation, nextTransformation);
  }

  /**
   * Method that creates a list containing the matrix and vector for a specific affine transformation.
   *
   * @return a list containing the matrix and vector for a specific affine transformation.
   */
  public List<TextField> affineTransformationTextFieldsList() {
    TextField matrixA00 = new TextField();
    TextField matrixA01 = new TextField();
    TextField matrixA10 = new TextField();
    TextField matrixA11 = new TextField();
    TextField vectorB0 = new TextField();
    TextField vectorB1 = new TextField();
    matrixA00.setPromptText("Matrix A00");
    matrixA01.setPromptText("Matrix A01");
    matrixA10.setPromptText("Matrix A10");
    matrixA11.setPromptText("Matrix A11");
    vectorB0.setPromptText("Vector B0");
    vectorB1.setPromptText("Vector B1");

    return List.of(matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1);
  }
  /**
   * Method that registers the coordinates of the max and min corners of the canvas from teh user input.
   */
  public void registerCoordinates(List<TextField> inputList) {
    minCoords = new Vector2D(Double.parseDouble(inputList.get(0).getText()), Double.parseDouble(inputList.get(1).getText()));
    maxCoords = new Vector2D(Double.parseDouble(inputList.get(2).getText()), Double.parseDouble(inputList.get(3).getText()));
    System.out.println(minCoords.getX0() + " " + minCoords.getX1() + " | " + maxCoords.getX0() + " " + maxCoords.getX1());
  }


}
