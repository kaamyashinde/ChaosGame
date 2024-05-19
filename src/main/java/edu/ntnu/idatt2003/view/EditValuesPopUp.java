package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.DescriptionValuesController;
import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.controller.ObjectListController;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * This class is responsible for creating the pop-up windows for editing the values of the chaos game description.
 *
 * @author Kaamya Shinde
 * @version 0.4
 * @since 0.3.4
 */

public class EditValuesPopUp {
  DescriptionValuesController descriptionValuesController = new DescriptionValuesController();
  ObjectListController objectListController = new ObjectListController();
  GameController gameController;
  ChaosGameDescription currentDescription;
  public EditValuesPopUp(GameController gameController){
    this.gameController = gameController;
  }
  public void setChaosGameDescription(){
    currentDescription = gameController.getCurrentChaosGameDescription();
  }

  public void setChaosGameDescriptionWithInput(ChaosGameDescription input){
    currentDescription = input;
    gameController = GameController.getInstance();
  }
  /**
   * Method that initialises the pop-up stage.
   *
   * @param title The title of the pop-up stage.
   * @return popupStage The stage to be used.
   */

  private Stage createPopupStage(String title) {
    Stage popupStage = new Stage();
    popupStage.setTitle(title);
    return popupStage;
  }

  /**
   * Method that creates the layout of the pop-up window.
   *
   * @param popupStage The stage to be used.
   * @return popupLayout The layout of the pop-up window.
   */
  private VBox createPopupLayout(Stage popupStage) {
    VBox popupLayout = new VBox();
    popupLayout.prefWidthProperty().bind(popupStage.widthProperty());
    popupLayout.prefHeightProperty().bind(popupStage.heightProperty());
    return popupLayout;
  }

  /**
   * Method that creates the register button for the pop-up window.
   *
   * @return registerButton The register button.
   */
  private Button createRegisterButton() {
    return new Button("Register");
  }

  /**
   * Method that shows the pop-up stage.
   *
   * @param popupStage  The stage to be used.
   * @param popupLayout The layout of the pop-up window.
   */

  private void showPopupStage(Stage popupStage, VBox popupLayout) {
    Scene popuScene = new Scene(popupLayout, 300, 300);
    popupStage.setScene(popuScene);
    popupStage.show();
  }

  /**
   * Method that creates the pop-up window for editing the C value.
   *
   * @param gameController The game controller.
   */
  public void createConstantCPopup() {

    Stage popupStage = createPopupStage("Edit C");
    VBox popupLayout = createPopupLayout(popupStage);
    HBox cValues = new HBox();
    HBox spacer = new HBox();
    HBox forButtons = new HBox();
    List<TextField> textFields = objectListController.constantCTextFieldsList();
    cValues.getChildren().addAll(textFields.get(0), textFields.get(1));
    spacer.setPrefHeight(20);
    Button registerButton = createRegisterButton();
    forButtons.getChildren().add(registerButton);
    descriptionValuesController.displayC(gameController.getCurrentChaosGameDescription(), textFields);
    popupLayout.getChildren().addAll(cValues, spacer, forButtons);
    showPopupStage(popupStage, popupLayout);
    registerButton.setOnAction(e -> {
      descriptionValuesController.registerC(textFields, gameController);
      popupStage.close();
    });
  }

  /**
   * Method that creates the pop-up window for editing the min and max coordinates.
   *
   * @param gameController The game controller.
   */
  public void createEditMaxAndMinPopup() {
    Stage popupStage = createPopupStage("Edit Min and Max");
    VBox popupLayout = createPopupLayout(popupStage);
    HBox minValues = new HBox();
    HBox maxValues = new HBox();
    HBox spacer = new HBox();
    spacer.setPrefHeight(20);
    HBox forButtons = new HBox();
    List<TextField> textFields = objectListController.maxAndMinCoordsTextFieldsList();
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    descriptionValuesController.displayMaxAndMinCoords(currentDescription, textFields);
    minValues.getChildren().addAll(textFields.get(0), textFields.get(1));
    maxValues.getChildren().addAll(textFields.get(2), textFields.get(3));
    Button registerButton = createRegisterButton();
    forButtons.getChildren().add(registerButton);
    popupLayout.getChildren().addAll(minValues, maxValues, spacer, forButtons);
    showPopupStage(popupStage, popupLayout);
    registerButton.setOnAction(e -> {
      descriptionValuesController.registerCoordinates(textFields, gameController);
      popupStage.close();
    });
  }

  /**
   * Method that creates the pop-up window for editing the affine transformations.
   *
   * @param gameController The game controller.
   */
  public void displayAffine() {
    Stage popupStage = createPopupStage("Edit affine transformations");
    VBox popupLayout = createPopupLayout(popupStage);
    HBox values = new HBox();
    VBox matrixValues = new VBox();
    HBox matrixRow1 = new HBox();
    HBox matrixRow2 = new HBox();
    matrixValues.getChildren().addAll(matrixRow1, matrixRow2);
    List<TextField> textFields = objectListController.affineTransformationTextFieldsList();
    ChaosGameDescription currentDescription = gameController.getCurrentChaosGameDescription();
    TextField transformationNumber = new TextField();
    transformationNumber.setPromptText("Transformation Number");
    List<Button> traverseButtons = objectListController.affineTransformationButtonsList();
    descriptionValuesController.displayCorrectAffineTransformation(currentDescription, traverseButtons, textFields, transformationNumber);
    matrixRow1.getChildren().addAll(textFields.get(0), textFields.get(1));
    matrixRow2.getChildren().addAll(textFields.get(2), textFields.get(3));
    VBox spacerBetweenMatrixAndVector = new VBox();
    spacerBetweenMatrixAndVector.setPrefWidth(20);
    VBox vectorValues = new VBox();
    HBox vectorRow1 = new HBox();
    HBox vectorRow2 = new HBox();
    vectorValues.getChildren().addAll(vectorRow1, vectorRow2);
    vectorRow1.getChildren().addAll(textFields.get(4));
    vectorRow2.getChildren().addAll(textFields.get(5));
    values.getChildren().addAll(matrixValues, spacerBetweenMatrixAndVector, vectorValues);
    HBox spacer = new HBox();
    spacer.setPrefHeight(20);
    HBox forButtons = new HBox();
    Button registerButton = createRegisterButton();
    forButtons.getChildren().add(registerButton);
    forButtons.getChildren().addAll(traverseButtons.get(0), transformationNumber, traverseButtons.get(1));
    popupLayout.getChildren().addAll(values, spacer, forButtons);
    showPopupStage(popupStage, popupLayout);
    registerButton.setOnAction(e -> {
      descriptionValuesController.registerAffineTransformations(Integer.parseInt(transformationNumber.getText()), textFields, gameController);
      descriptionValuesController.clearTextFields(textFields);
    });
  }
}