package edu.ntnu.idatt2003.controller;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;


import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

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
  List<Transform2D> transforms;
  int transformNum;

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

  /**
   * Method that registers the coordinates of the max and min corners of the canvas from teh user input.
   */
  public void registerCoordinates(List<TextField> inputList) {
    minCoords = new Vector2D(Double.parseDouble(inputList.get(0).getText()), Double.parseDouble(inputList.get(1).getText()));
    maxCoords = new Vector2D(Double.parseDouble(inputList.get(2).getText()), Double.parseDouble(inputList.get(3).getText()));
    System.out.println(minCoords.getX0() + " " + minCoords.getX1() + " | " + maxCoords.getX0() + " " + maxCoords.getX1());
  }

  /**
   * Method that registers the C value for the Julia transformation.
   *
   * @param inputList the list containing the text fields for the C value.
   */

  public void registerC(List<TextField> inputList) {
    Complex c = new Complex(Double.parseDouble(inputList.get(0).getText()), Double.parseDouble(inputList.get(1).getText()));
    System.out.println(c.getX0() + " " + c.getX1());
  }
  public void displayC(ChaosGameDescription input, List<TextField> inputList){
    JuliaTransform transform = (JuliaTransform) input.getTransforms().get(0);
    inputList.get(0).setText(String.valueOf(transform.getPoint().getReal()));
    inputList.get(1).setText(String.valueOf(transform.getPoint().getImaginary()));

  }

  /**
   * Method that registers the affine transformations from the user input.
   * @param inputList the list containing the text fields for the matrix and vector of the affine transformation.
   */
  public void registerAffineTransformations(List<TextField> inputList) {
    double[][] matrix = new double[2][2];
    matrix[0][0] = Double.parseDouble(inputList.get(0).getText());
    matrix[0][1] = Double.parseDouble(inputList.get(1).getText());
    matrix[1][0] = Double.parseDouble(inputList.get(2).getText());
    matrix[1][1] = Double.parseDouble(inputList.get(3).getText());
    double[] vector = new double[2];
    vector[0] = Double.parseDouble(inputList.get(4).getText());
    vector[1] = Double.parseDouble(inputList.get(5).getText());
    System.out.println(matrix[0][0] + " " + matrix[0][1] + " " + matrix[1][0] + " " + matrix[1][1] + " | " + vector[0] + " " + vector[1]);

  }


  /**
   * Method that clears the text fields in a list.
   * @param inputList the list containing the text fields to be cleared.
   */
  public void clearTextFields(List<TextField> inputList) {
    inputList.forEach(TextInputControl::clear);
  }

  public void displayMaxAndMinCoords(ChaosGameDescription inputDescription, List<TextField> inputTextFields) {
    inputTextFields.get(0).setText(String.valueOf(inputDescription.getMinCoords().getX0()));
    inputTextFields.get(1).setText(String.valueOf(inputDescription.getMinCoords().getX1()));
    inputTextFields.get(2).setText(String.valueOf(inputDescription.getMaxCoords().getX0()));
    inputTextFields.get(3).setText(String.valueOf(inputDescription.getMaxCoords().getX1()));
  }

  /**
   * Method that displays the current chaosGameDescriptions's Affine transformations.
   */
  public void displayAffineTransformations(int index, ChaosGameDescription inputDesc, List<TextField> inputTextFields) {
    AffineTransform2D affine = (AffineTransform2D) inputDesc.getTransforms().get(index);
    inputTextFields.get(0).setText(String.valueOf(affine.getMatrix().getA00()));
    inputTextFields.get(1).setText(String.valueOf(affine.getMatrix().getA01()));
    inputTextFields.get(2).setText(String.valueOf(affine.getMatrix().getA10()));
    inputTextFields.get(3).setText(String.valueOf(affine.getMatrix().getA11()));
    inputTextFields.get(4).setText(String.valueOf(affine.getVector().getX0()));
    inputTextFields.get(5).setText(String.valueOf(affine.getVector().getX1()));
  }

  /**
   * Method that is used to display the affine transformations correctly. It also handles the traversing between the different transformations.
   *
   * @param inputDesc                     the chaos game description
   * @param inputTraverseButtons          the list of buttons for traversing the transformations
   * @param inputTransformationTextFields the list of input fields for the transformations
   * @param inputTransformationNumber     the input field for the transformation number
   */
  public void displayCorrectAffineTransformation(ChaosGameDescription inputDesc, List<Button> inputTraverseButtons, List<TextField> inputTransformationTextFields, TextField inputTransformationNumber) {
    System.out.println("displaying the config info by running displayCorrectAffineTransformation");
    transforms = inputDesc.getTransforms();
    transformNum = 0;
    displayAffineTransformations(transformNum, inputDesc, inputTransformationTextFields);
    inputTraverseButtons.get(0).setOnAction(e -> {
      transformNum--;
      if (transformNum < 0) {
        transformNum = transforms.size() - 1;
      }
      displayAffineTransformations(transformNum, inputDesc, inputTransformationTextFields);
      inputTransformationNumber.setText(String.valueOf(transformNum + 1));
    });
    inputTraverseButtons.get(1).setOnAction(e -> {
      transformNum++;
      if (transformNum == transforms.size()) {
        transformNum = 0;
      }
      displayAffineTransformations(transformNum, inputDesc, inputTransformationTextFields);
      inputTransformationNumber.setText(String.valueOf(transformNum + 1));
    });
  }






}
