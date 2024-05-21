package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.List;

/**
 * The controller for the handling of the chaos game description values.
 * It is responsible for registering, displaying and clearing the values of the chaos game description.
 *
 * @author 10041
 * @version 0.4
 * @since 0.3.3
 */

public class DescriptionValuesController {
  private final GameController gameController = GameController.getInstance();
  private List<Transform2D> transforms;
  private int transformNum;
  /**
   * Method that handles the action for the next button.
   *
   * @param inputDesc                     the chaos game description
   * @param inputTransformationTextFields the list of text fields for the affine transformations
   * @param inputTransformationNumber     the text field for the affine transformation number
   */
  private void handleNextButtonAction(ChaosGameDescription inputDesc, List<TextField> inputTransformationTextFields, TextField inputTransformationNumber) {
    transformNum = (transformNum + 1) % transforms.size();
    updateTransformationDisplay(inputDesc, inputTransformationTextFields, inputTransformationNumber);
  }
  /**
   * Method that handles the action for the previous button.
   *
   * @param inputDesc                     the chaos game description
   * @param inputTransformationTextFields the list of text fields for the affine transformations
   * @param inputTransformationNumber     the text field for the affine transformation number
   */
  private void handlePreviousButtonAction(ChaosGameDescription inputDesc, List<TextField> inputTransformationTextFields, TextField inputTransformationNumber) {
    transformNum = (transformNum - 1 + transforms.size()) % transforms.size();
    updateTransformationDisplay(inputDesc, inputTransformationTextFields, inputTransformationNumber);
  }
  /**
   * Method that sets the actions for the buttons for traversing the affine transformations.
   *
   * @param inputTraverseButtons          the list of buttons for traversing the affine transformations
   * @param inputDesc                     the chaos game description
   * @param inputTransformationTextFields the list of text fields for the affine transformations
   * @param inputTransformationNumber     the text field for the affine transformation number
   */
  private void setButtonActions(List<Button> inputTraverseButtons, ChaosGameDescription inputDesc, List<TextField> inputTransformationTextFields, TextField inputTransformationNumber) {
    inputTraverseButtons.get(0).setOnAction(e -> handlePreviousButtonAction(inputDesc, inputTransformationTextFields, inputTransformationNumber));
    inputTraverseButtons.get(1).setOnAction(e -> handleNextButtonAction(inputDesc, inputTransformationTextFields, inputTransformationNumber));
  }
  /**
   * Method that updates the display of the affine transformations.
   *
   * @param inputDesc                     the chaos game description
   * @param inputTransformationTextFields the list of text fields for the affine transformations
   * @param inputTransformationNumber     the text field for the affine transformation number
   */
  private void updateTransformationDisplay(ChaosGameDescription inputDesc, List<TextField> inputTransformationTextFields, TextField inputTransformationNumber) {
    displayAffineTransformations(transformNum, inputDesc, inputTransformationTextFields);
    inputTransformationNumber.setText(String.valueOf(transformNum + 1));
  }
  /**
   * Method that displays the affine transformations. This is done based on the index of the affine transformation.
   *
   * @param index           the index of the affine transformation
   * @param inputDesc       the chaos game description
   * @param inputTextFields the list of text fields for the affine transformations
   */
  private void displayAffineTransformations(int index, ChaosGameDescription inputDesc, List<TextField> inputTextFields) {
    AffineTransform2D affine = (AffineTransform2D) inputDesc.getTransforms().get(index);
    inputTextFields.get(0).setText(String.valueOf(affine.getMatrix().getA00()));
    inputTextFields.get(1).setText(String.valueOf(affine.getMatrix().getA01()));
    inputTextFields.get(2).setText(String.valueOf(affine.getMatrix().getA10()));
    inputTextFields.get(3).setText(String.valueOf(affine.getMatrix().getA11()));
    inputTextFields.get(4).setText(String.valueOf(affine.getVector().getX0()));
    inputTextFields.get(5).setText(String.valueOf(affine.getVector().getX1()));
  }
  /**
   * Method that displays the C value for the Julia transformation.
   *
   * @param input     the chaos game description
   * @param inputList the list of text fields for the C value
   */
  public void displayC(ChaosGameDescription input, List<TextField> inputList) {
    JuliaTransform transform = (JuliaTransform) input.getTransforms().get(0);
    inputList.get(0).setText(String.valueOf(transform.getPoint().getReal()));
    inputList.get(1).setText(String.valueOf(transform.getPoint().getImaginary()));
  }

  /**
   * Method that displays the correct affine transformation based on the index.
   *
   * @param inputDesc                     the chaos game description
   * @param inputTraverseButtons          the list of buttons for traversing the affine transformations
   * @param inputTransformationTextFields the list of text fields for the affine transformations
   * @param inputTransformationNumber     the text field for the affine transformation number
   */
  public void displayCorrectAffineTransformation(ChaosGameDescription inputDesc, List<Button> inputTraverseButtons, List<TextField> inputTransformationTextFields, TextField inputTransformationNumber) {
    transforms = inputDesc.getTransforms();
    transformNum = 0;
    displayAffineTransformations(transformNum, inputDesc, inputTransformationTextFields);
    setButtonActions(inputTraverseButtons, inputDesc, inputTransformationTextFields, inputTransformationNumber);
  }
  /**
   * Method that displays the max and min coordinates of the canvas.
   *
   * @param inputDescription the chaos game description
   * @param inputTextFields  the list of text fields for the max and min coordinates
   */
  public void displayMaxAndMinCoords(ChaosGameDescription inputDescription, List<TextField> inputTextFields) {
    inputTextFields.get(0).setText(String.valueOf(inputDescription.getMinCoords().getX0()));
    inputTextFields.get(1).setText(String.valueOf(inputDescription.getMinCoords().getX1()));
    inputTextFields.get(2).setText(String.valueOf(inputDescription.getMaxCoords().getX0()));
    inputTextFields.get(3).setText(String.valueOf(inputDescription.getMaxCoords().getX1()));
  }
  /**
   * Method that registers the affine transformations from the user input.
   *
   * @param index     the index of the affine transformation
   * @param inputList the list containing the text fields for the matrix and vector of the affine transformation.
   */
  public void registerAffineTransformations(int index, List<TextField> inputList) {
    inputList.forEach(textField -> ValidationController.validateDouble(textField.getText()));

    Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(inputList.get(0).getText()), Double.parseDouble(inputList.get(1).getText()), Double.parseDouble(inputList.get(2).getText()), Double.parseDouble(inputList.get(3).getText()));
    Vector2D vector = new Vector2D(Double.parseDouble(inputList.get(4).getText()), Double.parseDouble(inputList.get(5).getText()));

    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    AffineTransform2D updatedTransform = new AffineTransform2D(matrix, vector);
    List<Transform2D> transforms = currentDescription.getTransforms();
    transforms.set(index, updatedTransform);
    currentDescription.setTransforms(transforms);

    gameController.setCurrentChaosGameDescription(currentDescription);
  }

  /**
   * Method that registers the C value for the Julia transformation.
   *
   * @param inputList the list containing the text fields for the C value.
   */
  public void registerC(List<TextField> inputList) {
    inputList.forEach(textField -> ValidationController.validateDouble(textField.getText()));

    double c0 = Double.parseDouble(inputList.get(0).getText());
    double c1 = Double.parseDouble(inputList.get(1).getText());

    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    List<Transform2D> transform = List.of(new JuliaTransform(Complex.createComplex(c0, c1), 1));
    currentDescription.setTransforms(transform);

    gameController.setCurrentChaosGameDescription(currentDescription);
  }

  public void registerCoordinates(List<TextField> inputList) {
    inputList.forEach(textField -> ValidationController.validateDouble(textField.getText()));
    double minX0 = Double.parseDouble(inputList.get(0).getText());
    double minX1 = Double.parseDouble(inputList.get(1).getText());
    double maxX0 = Double.parseDouble(inputList.get(2).getText());
    double maxX1 = Double.parseDouble(inputList.get(3).getText());

    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    currentDescription.setMinCoords(new Vector2D(minX0, minX1));
    currentDescription.setMaxCoords(new Vector2D(maxX0, maxX1));

    gameController.setCurrentChaosGameDescription(currentDescription);
  }

}
