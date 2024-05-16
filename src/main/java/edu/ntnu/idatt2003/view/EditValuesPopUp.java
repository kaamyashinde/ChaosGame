package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.TextFieldsController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

/**
 * Class that creates a popup for editing the values of the Chaos Game.
 * The popup is used to edit the constant C, the max and min coordinates and the affine transformations.
 * The popup is created when the user clicks on the edit button in the GUI.
 *
 * @author Kaamya Shinde
 * @version 0.1
 * @since 0.3.4
 */
public class EditValuesPopUp {
  TextFieldsController textFieldsController = new TextFieldsController();

  /**
   * Method that creates a popup for editing the constant C.
   */
  public void createConstantCPopup() {
    Stage popupStage = new Stage();
    VBox popupLayout = new VBox();
    HBox cValues = new HBox();
    HBox spacer = new HBox();
    HBox forButtons = new HBox();
    popupLayout.prefWidthProperty().bind(popupStage.widthProperty());
    popupLayout.prefHeightProperty().bind(popupStage.heightProperty());
    popupStage.setTitle("Edit C");
    List<TextField> textFields = textFieldsController.constantCTextFieldsList();
    cValues.getChildren().addAll(textFields.get(0), textFields.get(1));
    spacer.setPrefHeight(20);
    Button registerButton = new Button("Register");
    forButtons.getChildren().add(registerButton);

    popupLayout.getChildren().addAll(cValues, spacer, forButtons);

    Scene popuScene = new Scene(popupLayout, 300, 300);
    popupStage.setScene(popuScene);
    popupStage.show();


    registerButton.setOnAction(e -> {
      textFieldsController.registerC(textFields);
      popupStage.close();
    });
  }

  /**
   * Method that creates a popup for editing the max and min coordinates.
   */
  public void createEditMaxAndMinPopup() {
    //create a new stage for the popup
    Stage popupStage = new Stage();
    VBox popupLayout = new VBox();
    HBox minValues = new HBox();
    HBox maxValues = new HBox();
    //add a horizontal spacer
    HBox spacer = new HBox();
    spacer.setPrefHeight(20);
    HBox forButtons = new HBox();
    popupLayout.prefWidthProperty().bind(popupStage.widthProperty());
    popupLayout.prefHeightProperty().bind(popupStage.heightProperty());
    popupStage.setTitle("Edit Min and Max");
    List<TextField> textFields = textFieldsController.maxAndMinCoordsTextFieldsList();
    minValues.getChildren().addAll(textFields.get(0), textFields.get(1));
    maxValues.getChildren().addAll(textFields.get(2), textFields.get(3));
    Button registerButton = new Button("Register");
    forButtons.getChildren().add(registerButton);

    popupLayout.getChildren().addAll(minValues, maxValues, spacer, forButtons);


    Scene popuScene = new Scene(popupLayout, 300, 300);
    popupStage.setScene(popuScene);
    popupStage.show();
    registerButton.setOnAction(e -> {
          textFieldsController.registerCoordinates(textFields);
          popupStage.close();
        }
    );
  }

  /**
   * Method that creates a popup for editing the affine transformations.
   */
  public void displayAffine() {
    //create a new stage for the popup
    Stage popupStage = new Stage();
    VBox popupLayout = new VBox();
    //stores two vboxes - matrix and vector values
    HBox values = new HBox();

    //stores two horizontal boxes - one for the top row and the other for the bottom row
    VBox matrixValues = new VBox();
    HBox matrixRow1 = new HBox();
    HBox matrixRow2 = new HBox();
    matrixValues.getChildren().addAll(matrixRow1, matrixRow2);

    //add the textfields for the matrix values
    List<TextField> textFields = textFieldsController.affineTransformationTextFieldsList();
    matrixRow1.getChildren().addAll(textFields.get(0), textFields.get(1));
    matrixRow2.getChildren().addAll(textFields.get(2), textFields.get(3));

    //a spacer between the matrix and the vector
    VBox spacerBetweenMatrixAndVector = new VBox();
    spacerBetweenMatrixAndVector.setPrefWidth(20);
    //stores two horizontal boxes - one for the top row and the other for the bottom row
    VBox vectorValues = new VBox();
    HBox vectorRow1 = new HBox();
    HBox vectorRow2 = new HBox();
    vectorValues.getChildren().addAll(vectorRow1, vectorRow2);
    //add the textfields for the vector values
    vectorRow1.getChildren().addAll(textFields.get(4));
    vectorRow2.getChildren().addAll(textFields.get(5));


    //store the matrix values, spacer and vector values in a single hbox
    values.getChildren().addAll(matrixValues, spacerBetweenMatrixAndVector, vectorValues);


    HBox spacer = new HBox();
    spacer.setPrefHeight(20);
    HBox forButtons = new HBox();
    Button registerButton = new Button("Register");
    forButtons.getChildren().add(registerButton);

    popupLayout.getChildren().addAll(values, spacer, forButtons);
    popupLayout.prefWidthProperty().bind(popupStage.widthProperty());
    popupLayout.prefHeightProperty().bind(popupStage.heightProperty());
    popupStage.setTitle("Edit affine transformations");


    Scene popuScene = new Scene(popupLayout, 300, 300);
    popupStage.setScene(popuScene);
    popupStage.show();
    registerButton.setOnAction(e -> {
          // popupStage.close();
          //clear the text fields
          textFieldsController.registerAffineTransformations(textFields);
          textFieldsController.clearTextFields(textFields);
        }
    );
  }
}
