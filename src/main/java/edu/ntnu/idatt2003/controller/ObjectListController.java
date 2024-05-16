package edu.ntnu.idatt2003.controller;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.List;

/**
 * This class is responsible for creating lists of various GUI elements to be displayed in the view.
 * It aids in the separation of concerns between the view and the model.
 * @version 0.1
 * @since 0.3.4
 * @author Kaamya Shinde
 */

public class ObjectListController {
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
   * Method that creates a list containing the text fields for the C value in the Julia transformation.
   *
   * @return a list containing the text fields for the C value in the Julia transformation.
   */
  public List<TextField> constantCTextFieldsList() {
    TextField X0 = new TextField();
    TextField X1 = new TextField();
    X0.setPromptText("X0");
    X1.setPromptText("X1");
    return List.of(X0, X1);
  }
}
