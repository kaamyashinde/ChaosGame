package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.controller.Controller;
import edu.ntnu.idatt2003.controller.TextFieldsController;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;

public class DisplayScene implements ChaosGameObserver {
  private static final String FILE_PATH = "src/main/resources/";
  AnchorPane layout;
  GraphicsContext gc;
  Controller controller;
  ChaosGame game;
  Button addThousandPixelsButton;
  ChaosGameDescription chaosGameDescription;
  TextField transformationNumber = new TextField();
  TextField constantC;
  TextFieldsController textFieldsController;
  List<TextField> transformationTextFields;
  List<TextField> maxAndMinCoordsTextFields;
  List<Button> affineTransformationButtons;
  VBox rightBodyRow;
  int numOfTransforms;

  public DisplayScene(){
    textFieldsController = new TextFieldsController();
    maxAndMinCoordsTextFields = textFieldsController.maxAndMinCoordsTextFieldsList();
    affineTransformationButtons = textFieldsController.affineTransformationButtonsList();
    transformationTextFields = textFieldsController.affineTransformationTextFieldsList();

  }

  //@Override
  public Scene start(Stage primaryStage) {
    controller = new Controller();
    layout = new AnchorPane();
    VBox root = new VBox();
    layout.prefWidthProperty().bind(primaryStage.widthProperty());
    root.prefWidthProperty().bind(layout.widthProperty());
    layout.getChildren().add(root);
    root.getChildren().addAll(navigationHBox(), titleHBox(), bodyHBox(), footerHBox());
    root.getChildren().stream().filter(node -> node instanceof HBox).forEach(node -> ((HBox) node).prefWidthProperty().bind(root.widthProperty()));

    primaryStage.setTitle("Chaos Game");

    Scene scene = new Scene(layout, 1000, 700);
    return scene;
  }

  @Override
  public void updateAddPixel(double X0, double X1) {
    controller.addGradientColor(X0, X1);
  }

  @Override
  public void updateRemovePixel(double X0, double X1) {
    controller.removeGradientColor(X0, X1);
  }

  /**
   * Method that sets the constant c.
   */
  private void setConstantC() {
    constantC = new TextField();
    constantC.setPromptText("Constant C");
  }

  /**
   * Update the chaosGame object.
   */
  private void updateChaosGameObject(ChaosGameDescription input) {
    game = new ChaosGame(input, 500, 500);
    game.getCanvas().addObserver(this);
  }

  /**
   * Method that creates a HBox with a button for the user manual. This HBox is returned.
   *
   * @return The HBox with the button for the user manual.
   */
  private HBox navigationHBox() {
    Button getHelpButton = new Button("User Manual");
    HBox navigationRow = new HBox();
    navigationRow.getChildren().addAll(getHelpButton);
    navigationRow.setAlignment(Pos.CENTER);
    return navigationRow;
  }

  /**
   * Method that sets the title of the scene to "Chaos Game" and returns the HBox with the title.
   *
   * @return The HBox with the title.
   */
  private HBox titleHBox() {
    HBox titleRow = new HBox();
    TextField sceneHeading = new TextField(("Chaos Game"));
    sceneHeading.setEditable(false);
    sceneHeading.setAlignment(Pos.CENTER);
    titleRow.getChildren().add(sceneHeading);
    titleRow.setAlignment(Pos.CENTER);
    return titleRow;
  }

  /**
   * Method that creates the body row by creating three VBoxes and a StackPane.
   * The StackPane is where the canvas is placed and is at the centre of the body row.
   */
  private HBox bodyHBox() {
    HBox bodyRow = new HBox();
    VBox leftBodyRow = new VBox();
    StackPane chaosGamePane = controller.createGamePaneCanvas();

    rightBodyRow = new VBox();


    //startGame();
    rightBodyRow.getChildren().add(createPresetFractalButton("Julia"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Barnsley"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Sierpinski"));

    setConstantC();

    rightBodyRow.getChildren().addAll(
        handleEditGameConfigButtons().get(0),
        maxAndMinCoordsTextFields.get(0),
        maxAndMinCoordsTextFields.get(1),
        maxAndMinCoordsTextFields.get(2),
        maxAndMinCoordsTextFields.get(3),
        handleEditGameConfigButtons().get(1),
        handleEditGameConfigButtons().get(2));

    controller.setEditable(maxAndMinCoordsTextFields, false);
    controller.setEditable(transformationTextFields, false);
    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }

  /**
   * Method that handles the buttons for editing the game configuration.
   * The buttons are "Edit Game Config", "Register Coordinates" and "Register Affine Transformations".
   *
   * @return A list containing the buttons.
   */
  private List<Button> handleEditGameConfigButtons() {
    Button editGameConfigButton = new Button("Edit Game Config");
    editGameConfigButton.setOnAction(e -> {
      controller.setEditable(maxAndMinCoordsTextFields, true);
      transformationNumber.setEditable(true);
      //controller.setEditable(transformationTextFields, true);
    });
    Button registerCoordsButton = new Button("Register Coordinates");
    registerCoordsButton.setOnAction(e -> controller.registerCoordinates(maxAndMinCoordsTextFields));

    Button editNumOfTransformsButton = new Button("Edit Number of Transforms");
    editNumOfTransformsButton.setOnAction(e -> {
      numOfTransforms = Integer.parseInt(transformationNumber.getText());
      controller.setEditable(transformationTextFields, true);
      System.out.println("number of transformations to be registered: " + numOfTransforms);
    });
    Button registerAffineTransformationsButton = new Button("Register Affine Transformations");
    registerAffineTransformationsButton.setOnAction(e -> {
      controller.registerAffineTransformation(transformationTextFields, numOfTransforms);
      controller.clearTextFields(transformationTextFields);
      System.out.println("affine transformation registered.");
    });
    return List.of(editGameConfigButton, registerCoordsButton, editNumOfTransformsButton,registerAffineTransformationsButton);
  }

  /**
   * Method that displays the transformation matrices and the current transformation number after removing the constant C.
   */
  private void displayTransformationMatrices() {

    controller.switchBetweenDisplayOfAffineAndJuliaValues(1, rightBodyRow, transformationNumber, transformationTextFields, affineTransformationButtons, constantC);
    controller.displayCorrectAffineTransformation(chaosGameDescription, affineTransformationButtons, transformationTextFields, transformationNumber);

  }

  /**
   * Method that returns the footer row.
   */
  private HBox footerHBox() {
    HBox footerRow = new HBox();
    addThousandPixelsButton = new Button("Add 10000");
    addThousandPixelsButton.setOnAction(e ->
        game.runSteps(10000)
    );
    Button runNewConfigButton = new Button("Run New Config");
    runNewConfigButton.setOnAction(e -> {
      game.getCanvas().clear();
      game = new ChaosGame(controller.getNewestChaosGameDescription(), 500, 500);
      game.getCanvas().addObserver(this);
      game.runSteps(10000);
    });

    Button clearCanvasButton = new Button("Clear Canvas");
    clearCanvasButton.setOnAction(e -> game.getCanvas().clear());
    footerRow.getChildren().addAll(addThousandPixelsButton, clearCanvasButton, runNewConfigButton);
    footerRow.setAlignment(Pos.CENTER);
    return footerRow;
  }

  /**
   * Method that creates the buttons for the different fractals and returns them.
   *
   * @param fractalName The name of the fractal.
   * @return The button for the fractal.
   */

  public Button createPresetFractalButton(String fractalName) {
    Button button = new Button(fractalName);
    button.setOnAction(e -> {
      switch (fractalName) {
        case "Julia":
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Julia.txt");
          updateChaosGameObject(chaosGameDescription);
          game.getCanvas().clear();
          System.out.println("Julia preset button was clicked!");
          controller.switchBetweenDisplayOfAffineAndJuliaValues(0, rightBodyRow, transformationNumber, transformationTextFields, affineTransformationButtons, constantC);
          controller.displayMaxAndMinCoords(chaosGameDescription, maxAndMinCoordsTextFields);
          break;
        case "Barnsley":
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Barnsley.txt");
          updateChaosGameObject(chaosGameDescription);
          displayTransformationMatrices();
          game.getCanvas().clear();
          System.out.println("Barnsley preset button was clicked!");
          controller.displayMaxAndMinCoords(chaosGameDescription, maxAndMinCoordsTextFields);
          rightBodyRow.getChildren().add(handleEditGameConfigButtons().get(3));
          break;
        case "Sierpinski":
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Affine.txt");
          updateChaosGameObject(chaosGameDescription);
          displayTransformationMatrices();
          game.getCanvas().clear();
          System.out.println("Sierpinski preset button was clicked!");
          controller.displayMaxAndMinCoords(chaosGameDescription, maxAndMinCoordsTextFields);
          rightBodyRow.getChildren().add(handleEditGameConfigButtons().get(3));

          break;
        default:
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Default.txt");
          updateChaosGameObject(chaosGameDescription);
          break;
      }
    });
    return button;
  }

}
