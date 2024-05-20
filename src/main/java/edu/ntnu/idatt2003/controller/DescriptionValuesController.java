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
import javafx.scene.control.TextInputControl;

import java.util.List;

/**
 * The controller for the text fields in the GUI.
 * It is responsible for creating different types of lists for the text fields.
 *
 * @author Kaamya Shinde
 * @version 0.2
 * @since 0.3.3
 */

public class DescriptionValuesController {
  List<Transform2D> transforms;
  int transformNum;

  GameController gameController = GameController.getInstance();

  /**
   * Method that registers the coordinates of the max and min corners of the canvas from teh user input.
   *
   * @param inputList the list containing the text fields for the max and min coordinates
   */
  public void registerCoordinates(List<TextField> inputList) {
    inputList.forEach(textField -> ValidationController.validateDouble(textField.getText()));
    double minX = Double.parseDouble(inputList.get(0).getText());
    double minY = Double.parseDouble(inputList.get(1).getText());
    double maxX = Double.parseDouble(inputList.get(2).getText());
    double maxY = Double.parseDouble(inputList.get(3).getText());

    // Update the min and max coordinates in the current ChaosGameDescription
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    currentDescription.setMinCoords(new Vector2D(minX, minY));
    currentDescription.setMaxCoords(new Vector2D(maxX, maxY));

    // Update the ChaosGameDescription in the GameController
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

    // Update the C value in the current ChaosGameDescription
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    List<Transform2D> transform = List.of(new JuliaTransform(new Complex(c0, c1), 1));
    currentDescription.setTransforms(transform);

    // Update the ChaosGameDescription in the GameController
    gameController.setCurrentChaosGameDescription(currentDescription);
  }

  /**
   * Method that displays the C value for the Julia transformation.
   *
   * @param input     the chaos game description
   * @param inputList the list of text fields for the C value
   */
  public void displayC(ChaosGameDescription input, List<TextField> inputList) {
    // inputList.forEach(textField -> ValidationController.validateDouble(textField.getText()));

    JuliaTransform transform = (JuliaTransform) input.getTransforms().get(0);
    inputList.get(0).setText(String.valueOf(transform.getPoint().getReal()));
    inputList.get(1).setText(String.valueOf(transform.getPoint().getImaginary()));

  }

  /**
   * Method that registers the affine transformations from the user input.
   *
   * @param index          the index of the affine transformation
   * @param inputList      the list containing the text fields for the matrix and vector of the affine transformation.
   */
  public void registerAffineTransformations(int index, List<TextField> inputList) {
    inputList.forEach(textField -> ValidationController.validateDouble(textField.getText()));

    Matrix2x2 matrix = new Matrix2x2(Double.parseDouble(inputList.get(0).getText()), Double.parseDouble(inputList.get(1).getText()), Double.parseDouble(inputList.get(2).getText()), Double.parseDouble(inputList.get(3).getText()));
    Vector2D vector = new Vector2D(Double.parseDouble(inputList.get(4).getText()), Double.parseDouble(inputList.get(5).getText()));

    // Update the affine transformation in the current ChaosGameDescription
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    AffineTransform2D updatedTransform = new AffineTransform2D(matrix, vector);
    List<Transform2D> transforms = currentDescription.getTransforms();
    transforms.set(index, updatedTransform);
    currentDescription.setTransforms(transforms);

    // Update the ChaosGameDescription in the GameController
    gameController.setCurrentChaosGameDescription(currentDescription);
  }

  /**
   * Method that clears the text fields in a list.
   *
   * @param inputList the list containing the text fields to be cleared.
   */
  public void clearTextFields(List<TextField> inputList) {
    inputList.forEach(TextInputControl::clear);
  }

  /**
   * Method that displays the max and min coordinates of the canvas.
   *
   * @param inputDescription the chaos game description
   * @param inputTextFields  the list of text fields for the max and min coordinates
   */
  public void displayMaxAndMinCoords(ChaosGameDescription inputDescription, List<TextField> inputTextFields) {
    //inputTextFields.forEach(textField -> ValidationController.validateDouble(textField.getText()));

    inputTextFields.get(0).setText(String.valueOf(inputDescription.getMinCoords().getX0()));
    inputTextFields.get(1).setText(String.valueOf(inputDescription.getMinCoords().getX1()));
    inputTextFields.get(2).setText(String.valueOf(inputDescription.getMaxCoords().getX0()));
    inputTextFields.get(3).setText(String.valueOf(inputDescription.getMaxCoords().getX1()));
  }

  /**
   * Method that displays the affine transformations. This is done based on the index of the affine transformation.
   *
   * @param index           the index of the affine transformation
   * @param inputDesc       the chaos game description
   * @param inputTextFields the list of text fields for the affine transformations
   */
  public void displayAffineTransformations(int index, ChaosGameDescription inputDesc, List<TextField> inputTextFields) {
   // inputTextFields.forEach(textField -> ValidationController.validateDouble(textField.getText()));

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
