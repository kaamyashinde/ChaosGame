package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.DescriptionValuesController;
import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.controller.ObjectListController;
import edu.ntnu.idatt2003.controller.ValidationController;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import static edu.ntnu.idatt2003.view.PopupScene.*;

/**
 * This class is responsible for creating the pop-up windows for editing the values of the chaos game description.
 *
 * @author Kaamya Shinde
 * @version 0.5
 * @since 0.3.4
 */

public class EditValuesPopUp {
  DescriptionValuesController descriptionValuesController = new DescriptionValuesController();
  ObjectListController objectListController = new ObjectListController();
  GameController gameController = GameController.getInstance();
  ChaosGameDescription currentDescription;


  public void setChaosGameDescription() {
    currentDescription = gameController.getCurrentChaosGameDescription();
  }

  public void setChaosGameDescriptionWithInput(ChaosGameDescription input) {
    currentDescription = input;
    gameController = GameController.getInstance();
  }

  /**
   * Method that creates the register button for the pop-up window.
   *
   * @return registerButton The register button.
   */
  private Button createRegisterButton() {
    Button registerButton = new Button("Register");
    registerButton.getStyleClass().add("edit-popup-button");
    return new Button("Register");
  }


  /**
   * Method that creates the pop-up window for editing the C value.
   */


  public void createConstantCPopup() {
    Stage popupStage = createPopupStage("Edit C", gameController.getPrimaryStage());
    VBox popupLayout = createPopupLayout(popupStage);
    //container for the two values
    VBox displayMatrix = new VBox();
    //container for the real part
    VBox left = new VBox();
    //container for the imaginary part
    VBox right = new VBox();

    Label real = new Label("Real part");
    Label imag = new Label("Imaginary part");

    List<TextField> textFields = objectListController.constantCTextFieldsList();
    left.getChildren().addAll(real, textFields.get(0));
    right.getChildren().addAll(imag, textFields.get(1));

    displayMatrix.getChildren().addAll(left, right);

    HBox spacer = new HBox();
    HBox forButtons = new HBox();

    spacer.setPrefHeight(20);
    Button registerButton = createRegisterButton();
    forButtons.getChildren().add(registerButton);
    forButtons.setAlignment(Pos.CENTER);

    descriptionValuesController.displayC(gameController.getCurrentChaosGameDescription(), textFields);
    popupLayout.getChildren().addAll(displayMatrix, spacer, forButtons);
    dimBackground(gameController.getPrimaryStage(), popupStage);
    showPopupStage(popupStage, popupLayout, 300, 300);
    registerButton.setOnAction(e -> {
      try {
        descriptionValuesController.registerC(textFields);
        popupStage.close();
      } catch (Exception exception) {
        UserFeedback.displayError("Invalid input for the complex number value.", "Please ensure that the input is a number. Remember to use '.' as the decimal separator.");
      }
    });
  }


  /**
   * Method that creates the pop-up window for editing the min and max coordinates.
   */
  public void createEditMaxAndMinPopup() {
    Stage popupStage = createPopupStage("Edit Min and Max", gameController.getPrimaryStage());
    VBox popupLayout = createPopupLayout(popupStage);
    HBox minValues = new HBox();
    HBox maxValues = new HBox();
    HBox spacer = new HBox();
    spacer.setPrefHeight(20);
    HBox forButtons = new HBox();
    List<TextField> textFields = objectListController.maxAndMinCoordsTextFieldsList();
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    descriptionValuesController.displayMaxAndMinCoords(currentDescription, textFields);
    Label minLabel = new Label("Min");
    VBox minContainer = new VBox();
    minContainer.getChildren().addAll(minLabel, minValues);
    minValues.getChildren().addAll(textFields.get(0), textFields.get(1));

    Label maxLabel = new Label("Max");
    VBox maxContainer = new VBox();
    maxContainer.getChildren().addAll(maxLabel, maxValues);
    maxValues.getChildren().addAll(textFields.get(2), textFields.get(3));
    Button registerButton = createRegisterButton();
    forButtons.getChildren().add(registerButton);
    forButtons.setAlignment(Pos.CENTER);
    popupLayout.getChildren().addAll(minContainer, maxContainer, spacer, forButtons);
    dimBackground(gameController.getPrimaryStage(), popupStage);
    showPopupStage(popupStage, popupLayout, 300, 300);
    registerButton.setOnAction(e -> {
      try {
        descriptionValuesController.registerCoordinates(textFields);
        popupStage.close();
      } catch (Exception exception) {
        UserFeedback.displayError("Invalid input for the max or min value.", "Please ensure that the input is a number. Remember to use '.' as the decimal separator.");
      }
    });
  }

  /**
   * Method that creates the pop-up window for editing the affine transformations.
   */
  public void displayAffine() {
    Stage popupStage = createPopupStage("Edit affine transformations", gameController.getPrimaryStage());
    VBox popupLayout = createPopupLayout(popupStage);
    HBox values = new HBox();
    values.setAlignment(Pos.CENTER);
    VBox matrixValues = new VBox();
    Label matrixLabel = new Label("Matrix");
    HBox matrixRow1 = new HBox();
    HBox matrixRow2 = new HBox();
    matrixValues.getChildren().addAll(matrixLabel,matrixRow1, matrixRow2);
    List<TextField> textFields = objectListController.affineTransformationTextFieldsList();
    objectListController.setTextFieldWidth(textFields);
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    TextField transformationNumber = new TextField("1");
    transformationNumber.setPrefWidth(50);
    transformationNumber.setMinWidth(50);
    transformationNumber.setMaxWidth(60);
    transformationNumber.getStyleClass().add("display-number");

    List<Button> traverseButtons = objectListController.affineTransformationButtonsList();
    descriptionValuesController.displayCorrectAffineTransformation(currentDescription, traverseButtons, textFields, transformationNumber);
    matrixRow1.getChildren().addAll(textFields.get(0), textFields.get(1));
    matrixRow2.getChildren().addAll(textFields.get(2), textFields.get(3));
    VBox spacerBetweenMatrixAndVector = new VBox();
    spacerBetweenMatrixAndVector.setPrefWidth(20);
    VBox vectorValues = new VBox();
    Label vectorLabel = new Label("Vector");
    HBox vectorRow1 = new HBox();
    HBox vectorRow2 = new HBox();
    vectorValues.getChildren().addAll(vectorLabel,vectorRow1, vectorRow2);
    vectorRow1.getChildren().addAll(textFields.get(4));
    vectorRow2.getChildren().addAll(textFields.get(5));
    values.getChildren().addAll(matrixValues, spacerBetweenMatrixAndVector, vectorValues);
    HBox spacer = new HBox();
    spacer.setPrefHeight(20);

    VBox forButtons = new VBox();
    Button registerButton = createRegisterButton();

    HBox registerButtonBox = new HBox();
    registerButtonBox.setAlignment(Pos.CENTER);
    registerButtonBox.getChildren().add(registerButton);
    HBox.setMargin(registerButtonBox, new Insets(100));

    HBox transverseThroughTransformations = new HBox();
    transverseThroughTransformations.setAlignment(Pos.CENTER);
    transverseThroughTransformations.getChildren().addAll(traverseButtons.get(0), transformationNumber, traverseButtons.get(1));
    forButtons.setSpacing(20);
    forButtons.getChildren().addAll(transverseThroughTransformations, registerButtonBox);
    traverseButtons.forEach(button -> button.setPrefHeight(transformationNumber.getHeight()));
    popupLayout.getChildren().addAll(values, spacer, forButtons);
    dimBackground(gameController.getPrimaryStage(), popupStage);
    showPopupStage(popupStage, popupLayout, 300, 300);
    registerButton.setOnAction(e -> {
      try {
        ValidationController.validateInteger(transformationNumber.getText());
        descriptionValuesController.registerAffineTransformations(Integer.parseInt(transformationNumber.getText()) - 1, textFields);
        popupStage.close();

      } catch (Exception exception) {
        UserFeedback.displayError("Invalid input for the affine transformations.", "Please ensure that the input is a number. Remember to use '.' as the decimal separator.");
      }
    });
  }
}