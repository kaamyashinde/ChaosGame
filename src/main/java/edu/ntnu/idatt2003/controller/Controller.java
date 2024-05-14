package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
  private static final String FILE_PATH = "src/main/resources/";
  ArrayList<ChaosGameDescription> chaosGameDescriptions;
  ChaosGameDescription currentDescription;
  List<Transform2D> affineTransforms = new ArrayList<>();
  Stage stage;
  ChaosGame chaosGame;
  int transformNum;
  GraphicsContext gc;
  Vector2D minCoords;
  Vector2D maxCoords;

  public Controller(Stage stage) {
    this.stage = stage;
    chaosGameDescriptions = new ArrayList<>();
    loadChaosGamePresets();
  }

  /**
   * Method that loads all the presets for the different fractals.
   */
  private void loadChaosGamePresets() {
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Julia.txt"));
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Barnsley.txt"));
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Affine.txt"));
  }



  /**
   * Method that creates an appropriate stack pane canvas to display the fractal on.
   */
  public StackPane createGamePaneCanvas() {
    chaosGame = new ChaosGame(readChaosGameDescriptionFromFile("Julia.txt"), 500, 500);
    Canvas canvas = new Canvas(500, 500);
    gc = canvas.getGraphicsContext2D();
    StackPane chaosGamePane = new StackPane();
    chaosGamePane.getChildren().add(canvas);
    return chaosGamePane;
  }

  /**
   * Method that is responsible for adding in the gradient color to the canvas.
   */
  public void addGradientColor(double X0, double X1) {
    Color startColor = Color.rgb(230, 183, 183); // bright red
    Color endColor = Color.rgb(215, 8, 8); // dark red
    double fraction = X1 / 500; // assuming the height of the canvas is 500
    Color gradientColor = startColor.interpolate(endColor, fraction);
    gc.setFill(gradientColor);
    gc.fillRect(X0, X1, 1, 1);
  }

  /**
   * Method that is responsible for removing the gradient color from the canvas.
   */
  public void removeGradientColor(double X0, double X1) {
    gc.clearRect(X0, X1, 1, 1);
  }

  /**
   * Method that displays the current chaosGameDescription's maximum and minimum coordinates.
   */
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
   * Method that sets the text fields in a list to be editable or not.
   */
  public void setEditable(List<TextField> inputList, boolean isEditable) {
    inputList.forEach(textField -> textField.setEditable(isEditable));
  }


  /**
   * Method that reads the chaosGameDescription object from a file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH + fileName);
  }

  /**
   * Method that removes and adds the input fields for the affine and julia transformations depending on the case number.*
   *
   * @param caseNum                   the case number
   *                                  0 for Julia
   *                                  1 for Affine
   * @param inputVBox                 the VBox containing the input fields
   * @param inputTransformationNumber the input field for the transformation number
   * @param inputTransformationList   the list of input fields for the transformations
   * @param inputTraverseButtons      the list of buttons for traversing the transformations
   * @param inputConstantC            the input field for the constant c
   */
  public void switchBetweenDisplayOfAffineAndJuliaValues(int caseNum, VBox inputVBox, TextField inputTransformationNumber, List<TextField> inputTransformationList, List<Button> inputTraverseButtons, TextField inputConstantC) {
    inputTransformationNumber.setText("1");
    inputTransformationNumber.setEditable(false);

    if (caseNum == 0) {
      inputTransformationList.forEach(textField -> inputVBox.getChildren().remove(textField));
      inputTraverseButtons.forEach(button -> inputVBox.getChildren().remove(button));
      inputVBox.getChildren().remove(inputTransformationNumber);
      if (!inputVBox.getChildren().contains(inputConstantC)) {
        inputVBox.getChildren().add(inputConstantC);
      }
    } else {
      inputVBox.getChildren().remove(inputConstantC);
      if (!inputVBox.getChildren().contains(inputTransformationNumber)) {
        inputTransformationList.forEach(textField -> inputVBox.getChildren().add(textField));
        inputTraverseButtons.forEach(button -> inputVBox.getChildren().add(button));
        inputVBox.getChildren().add(inputTransformationNumber);
      }

    }
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
    List<Transform2D> transforms = inputDesc.getTransforms();
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

  /**
   * Method that registers the coordinates of the max and min corners of the canvas from teh user input.
   */
  public void registerCoordinates(List<TextField> inputList) {
    minCoords = new Vector2D(Double.parseDouble(inputList.get(0).getText()), Double.parseDouble(inputList.get(1).getText()));
    maxCoords = new Vector2D(Double.parseDouble(inputList.get(2).getText()), Double.parseDouble(inputList.get(3).getText()));
    System.out.println(minCoords.getX0() + " " + minCoords.getX1() + " | " + maxCoords.getX0() + " " + maxCoords.getX1());
  }

  /**
   * Method that registers the affine transformations from the user input.
   * It then returns the matrix.
   *
   * @param inputTextFields the list of input fields for the affine transformations
   * @return the matrix
   */
  public Matrix2x2 registerAffineTransformationMatrix(List<TextField> inputTextFields) {
    return new Matrix2x2(Double.parseDouble(inputTextFields.get(0).getText()), Double.parseDouble(inputTextFields.get(1).getText()), Double.parseDouble(inputTextFields.get(2).getText()), Double.parseDouble(inputTextFields.get(3).getText()));
  }

  /**
   * Method that registers the affine transformations from the user input.
   * It then returns the vector.
   *
   * @param inputTextFields the list of input fields for the affine transformations
   * @return the vector
   */

  public Vector2D registerAffineTransformationVector(List<TextField> inputTextFields) {
    return new Vector2D(Double.parseDouble(inputTextFields.get(4).getText()), Double.parseDouble(inputTextFields.get(5).getText()));
  }

  public void registerAffineTransformation(List<TextField> inputTextFields, int numOfTransforms) {
    if (affineTransforms.size() < numOfTransforms){
    Matrix2x2 matrix = registerAffineTransformationMatrix(inputTextFields);
    Vector2D vector = registerAffineTransformationVector(inputTextFields);
    AffineTransform2D affine = new AffineTransform2D(matrix, vector);
    affineTransforms.add(affine);}
    else {
      System.out.println("You have reached the maximum number of transformations");
      System.out.println("updating the chaos game so that u can run the game.");
      createNewChaosGameDescription();
    }
  }
  /**
   * Method that clears the text fields in a list.
   */
  public void clearTextFields(List<TextField> inputList) {
    inputList.forEach(TextInputControl::clear);}

/**
 * Method that creates a new chaos game description object from the user input.
 */
public void createNewChaosGameDescription(){
  ChaosGameDescription newDescription = new ChaosGameDescription(minCoords, maxCoords, affineTransforms);
  System.out.println(affineTransforms.size());
  chaosGameDescriptions.add(newDescription);
 affineTransforms.clear();
}
/**
 * Method that returns the newest chaos game description object.
 */
public ChaosGameDescription getNewestChaosGameDescription(){
  return chaosGameDescriptions.get(chaosGameDescriptions.size()-1);}
//TODO; fix why the affineTransforms is empty
}
