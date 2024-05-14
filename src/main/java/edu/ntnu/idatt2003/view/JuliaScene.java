package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.controller.Controller;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.transformations.Transform2D;
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

public class JuliaScene extends Application implements ChaosGameObserver {
  private static final String FILE_PATH = "src/main/resources/";
  AnchorPane layout;
  GraphicsContext gc;
  Controller controller;
  ChaosGame game;
  Button addThousandPixelsButton;
  ChaosGameDescription chaosGameDescription;
  TextField transformationNumber;
  TextField constantC;
  List<TextField> transformationTextFields = affineTransformationTextFieldsList();
  List<TextField> maxAndMinCoordsTextFields = maxAndMinCoordsTextFieldsList();
  List<Button> affineTransformationButtons = affineTransformationButtonsList();
  VBox rightBodyRow;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    controller = new Controller(primaryStage);
    layout = new AnchorPane();
    VBox root = new VBox();
    layout.prefWidthProperty().bind(primaryStage.widthProperty());
    root.prefWidthProperty().bind(layout.widthProperty());
    layout.getChildren().add(root);
    root.getChildren().addAll(navigationHBox(), titleHBox(), bodyHBox(), footerHBox());
    root.getChildren().stream().filter(node -> node instanceof HBox).forEach(node -> ((HBox) node).prefWidthProperty().bind(root.widthProperty()));


    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void updateAddPixel(double X0, double X1) {
    // Define the start and end colors for the gradient
    Color startColor = Color.rgb(230, 183, 183); // bright red
    Color endColor = Color.rgb(215, 8, 8); // dark red

    // Calculate the fraction based on the y-coordinate
    double fraction = X1 / 500; // assuming the height of the canvas is 500

    // Interpolate between the start and end colors based on the fraction
    Color gradientColor = startColor.interpolate(endColor, fraction);

    // Set the fill color to the gradient color
    gc.setFill(gradientColor);

    // Draw the pixel
    gc.fillRect(X0, X1, 1, 1);
  }

  @Override
  public void updateRemovePixel(double X0, double X1) {
    gc.clearRect(X0, X1, 1, 1);
  }

  /**
   * Method that uses the controller to gain access to the chaos game.
   */
  private StackPane gamePaneCanvas() {
    updateChaosGameObject(controller.readChaosGameDescriptionFromFile("Affine.txt"));
    Canvas canvas = new Canvas(500, 500);
    gc = canvas.getGraphicsContext2D();
    StackPane chaosGamePane = new StackPane();
    chaosGamePane.getChildren().add(canvas);


    return chaosGamePane;
  }

  /**
   * Method that creates a list containing the max and min coordinates text fields.
   */
  private List<TextField> maxAndMinCoordsTextFieldsList() {

    TextField minCoordsX0 = new TextField();
    TextField minCoordsX1 = new TextField();
    TextField maxCoordsX0 = new TextField();
    TextField maxCoordsX1 = new TextField();
    return List.of(minCoordsX0, minCoordsX1, maxCoordsX0, maxCoordsX1);
  }

  /**
   * Method that creates a list containing the buttons used to control the viewing of the Affine transformations.
   */
  private List<Button> affineTransformationButtonsList() {
    Button previousTransformation = new Button("Previous Transformation");
    Button nextTransformation = new Button("Next Transformation");
    return List.of(previousTransformation, nextTransformation);
  }

  /**
   * Method that creates a list containing the matrix and vector for a specific affine transformation.
   */
  private List<TextField> affineTransformationTextFieldsList() {
    TextField matrixA00 = new TextField();
    TextField matrixA01 = new TextField();
    TextField matrixA10 = new TextField();
    TextField matrixA11 = new TextField();
    TextField vectorB0 = new TextField();
    TextField vectorB1 = new TextField();

    constantC = new TextField();
    return List.of(matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1);
  }

  /**
   * Method that creates a map containing the matrix and vector for a specific affine transformation.
   *
   * @return The map containing the matrix and vector for a specific affine transformation.
   */

  private HashMap<Integer, List<TextField>> affineTransformationTextFieldsMap() {
    HashMap<Integer, List<TextField>> map = new HashMap<>();
    // map.put(0, List.of(matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1));
    return map;
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
          break;
        case "Sierpinski":
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Affine.txt");
          updateChaosGameObject(chaosGameDescription);
          displayTransformationMatrices();
          game.getCanvas().clear();
          System.out.println("Sierpinski preset button was clicked!");
          controller.displayMaxAndMinCoords(chaosGameDescription, maxAndMinCoordsTextFields);


          break;
        default:
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Default.txt");
          updateChaosGameObject(chaosGameDescription);

          break;
      }
    });
    return button;
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
    StackPane chaosGamePane = gamePaneCanvas();

    rightBodyRow = new VBox();


    //startGame();
    rightBodyRow.getChildren().add(createPresetFractalButton("Julia"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Barnsley"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Sierpinski"));

    //initialise the different text fields
    setPromptText();

    //to allow the user to edit the text fields
    Button editGameConfigButton = new Button("Edit Game Config");
    editGameConfigButton.setOnAction(e -> {
      controller.setEditable(maxAndMinCoordsTextFields, true);
      controller.setEditable(transformationTextFields, true);
    });
    Button registerCoordsButton = new Button("Register Coordinates");
    registerCoordsButton.setOnAction(e -> controller.registerCoordinates(maxAndMinCoordsTextFields));
    Button registerAffineTransformationsButton = new Button("Register Affine Transformations");
    registerAffineTransformationsButton.setOnAction(e -> registerAffineTransformations());


    rightBodyRow.getChildren().addAll(
        editGameConfigButton,
        maxAndMinCoordsTextFields.get(0),
        maxAndMinCoordsTextFields.get(1),
        maxAndMinCoordsTextFields.get(2),
        maxAndMinCoordsTextFields.get(3),
        registerCoordsButton,
        registerAffineTransformationsButton);

    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }

  /**
   * Method that displays the transformation matrices and the current transformation number after removing the constant C.
   */
  private void displayTransformationMatrices() {
    controller.switchBetweenDisplayOfAffineAndJuliaValues(1, rightBodyRow, transformationNumber, transformationTextFields, affineTransformationButtons, constantC);
    transformationNumber.setText("1");
    transformationNumber.setEditable(false);
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

    Button clearCanvasButton = new Button("Clear Canvas");
    clearCanvasButton.setOnAction(e -> game.getCanvas().clear());
    footerRow.getChildren().addAll(addThousandPixelsButton, clearCanvasButton);
    footerRow.setAlignment(Pos.CENTER);
    return footerRow;
  }

  private void setPromptText() {
    System.out.println("Setting prompt text");
    maxAndMinCoordsTextFields.get(0).setPromptText("Min X0");
    maxAndMinCoordsTextFields.get(1).setPromptText("Min X1");
    maxAndMinCoordsTextFields.get(2).setPromptText("Max X0");
    maxAndMinCoordsTextFields.get(3).setPromptText("Max X1");
    transformationTextFields.get(0).setPromptText("Matrix A00");
    transformationTextFields.get(1).setPromptText("Matrix A01");
    transformationTextFields.get(2).setPromptText("Matrix A10");
    transformationTextFields.get(3).setPromptText("Matrix A11");

    transformationTextFields.get(4).setPromptText("Vector B1");
    transformationTextFields.get(5).setPromptText("Vector B0");
    constantC.setPromptText("Constant C");
  }

  /**
   * Method that registers the transformations of the affine type.
   */
  private void registerAffineTransformations() {
    double matrixA00 = Double.parseDouble(this.transformationTextFields.get(0).getText());
    double matrixA01 = Double.parseDouble(this.transformationTextFields.get(1).getText());
    double matrixA10 = Double.parseDouble(this.transformationTextFields.get(2).getText());
    double matrixA11 = Double.parseDouble(this.transformationTextFields.get(3).getText());
    double vectorB0 = Double.parseDouble(this.transformationTextFields.get(4).getText());
    double vectorB1 = Double.parseDouble(this.transformationTextFields.get(5).getText());
    System.out.println(matrixA00 + " " + matrixA01 + " | " + matrixA10 + " " + matrixA11 + " | " + vectorB0 + " " + vectorB1);
  }
}
