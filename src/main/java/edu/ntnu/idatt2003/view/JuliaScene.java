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

import java.util.List;

public class JuliaScene extends Application implements ChaosGameObserver {
  private static final String FILE_PATH = "src/main/resources/";
  AnchorPane layout;
  GraphicsContext gc;
  Controller controller;
  ChaosGame game;
  Button addThousandPixelsButton;
  ChaosGameDescription chaosGameDescription;
  TextField minCoordsX0;
  TextField minCoordsX1;
  TextField maxCoordsX0;
  TextField maxCoordsX1;
  TextField transformationNumber;
  TextField constantC;
  TextField matrixA00;
  TextField matrixA01;
  TextField matrixA10;
  TextField matrixA11;
  TextField vectorB0;
  TextField vectorB1;
  Button nextTransformation;
  Button previousTransformation;
  int transformNum;
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
    gc.setFill(Color.BLACK);
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
          System.out.println("Julia");
          displayConstantC();
          break;
        case "Barnsley":
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Barnsley.txt");
          updateChaosGameObject(chaosGameDescription);
          displayTransformationMatrices();
          game.getCanvas().clear();
          System.out.println("Barnsley");
          break;
        case "Sierpinski":
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Affine.txt");
          updateChaosGameObject(chaosGameDescription);
          displayTransformationMatrices();
          game.getCanvas().clear();
          System.out.println("Sierpinski");
          break;
        default:
          chaosGameDescription = controller.readChaosGameDescriptionFromFile("Default.txt");
          displayConfigInfo();
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

    minCoordsX0 = new TextField("Min Coords");
    minCoordsX1 = new TextField("Min Coords");
    maxCoordsX0 = new TextField("Max Coords");
    maxCoordsX1 = new TextField("Max Coords");
    matrixA00 = new TextField("Matrix A00");
    matrixA01 = new TextField("Matrix A01");
    matrixA10 = new TextField("Matrix A10");
    matrixA11 = new TextField("Matrix A11");
    vectorB0 = new TextField("Vector B0");
    vectorB1 = new TextField("Vector B1");

    constantC = new TextField("Constant C");


    previousTransformation = new Button("Previous Transformation");
    nextTransformation = new Button("Next Transformation");

    transformationNumber = new TextField("1");
    transformationNumber.setEditable(false);


    rightBodyRow.getChildren().addAll(minCoordsX0, minCoordsX1, maxCoordsX0, maxCoordsX1);

    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }
  private void displayTransformationMatrices(){
    rightBodyRow.getChildren().remove(constantC);
    if (!rightBodyRow.getChildren().contains(transformationNumber)){
      rightBodyRow.getChildren().addAll(matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1,previousTransformation, nextTransformation, transformationNumber);
    }
    displayConfigInfo();
  }
  private void displayConstantC(){
    rightBodyRow.getChildren().removeAll(matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1,previousTransformation, nextTransformation, transformationNumber);
    if (!rightBodyRow.getChildren().contains(constantC)){
      rightBodyRow.getChildren().add(constantC);
    }
  }
  /**
   * Method that shows the current chaos game description in the field.
   */
  private void displayConfigInfo(){
    System.out.println("Displaying config info");
    ChaosGameDescription desc = chaosGameDescription;
    List<Transform2D> transforms = desc.getTransforms();
    transformNum = 0;
    controller.displayCanvasCoordinates(
        0, desc, matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1);
    previousTransformation.setOnAction(e -> {
      transformNum--;
      if (transformNum < 0) {
        transformNum = transforms.size() - 1;
      }
      controller.displayCanvasCoordinates(
          transformNum, desc, matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1)
      ;
      transformationNumber.setText(String.valueOf(transformNum+1));

    });
    nextTransformation.setOnAction(e -> {
      transformNum++;
      if (transformNum==transforms.size()){
        transformNum = 0;
      }
      controller.displayCanvasCoordinates(
          transformNum, desc, matrixA00, matrixA01, matrixA10, matrixA11, vectorB0, vectorB1)
      ;
      transformationNumber.setText(String.valueOf(transformNum+1));

    });
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

  /**
   * Method to initiate the user adjustments for the chaos game.
   */
  private void startGameMenu() {
    matrixA00 = new TextField("Matrix A00");
    matrixA01 = new TextField("Matrix A01");
    matrixA10 = new TextField("Matrix A10");
    matrixA11 = new TextField("Matrix A11");
    vectorB0 = new TextField("Vector B0");
    vectorB1 = new TextField("Vector B1");
  }


}
