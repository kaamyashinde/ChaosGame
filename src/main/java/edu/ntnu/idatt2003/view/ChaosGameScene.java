package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.Controller;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ChaosGameScene extends Application implements ChaosGameObserver {
  AnchorPane layout = new AnchorPane();
  Button addThousandPixelsButton;
  Button getHelpButton;
  Button clearCanvasButton;
  Controller controller;
  GraphicsContext gc;
  HBox bodyRow;
  HBox footerRow;
  HBox navigationRow;
  HBox titleRow;
  StackPane chaosGamePane;
  TextField sceneHeading;
  VBox leftBodyRow;
  VBox rightBodyRow;
  VBox root;
  private boolean isAffine;
  private Canvas canvas;
  private ChaosCanvas chaosGameCanvas;
  private ChaosGame chaosGame;
  private static final String FILE_PATH = "src/main/resources/";

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    setLayout();
    startGame();
    chaosGame.addObserver(this);
    chaosGameCanvas.addObserver(this);
    setScene(primaryStage);
    controller = new Controller(primaryStage);
    addThousandPixelsButton.setOnAction(e -> runThousandSteps());
    clearCanvasButton.setOnAction(e -> clearCanvas());
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
   * Method that runs the chaos game for 10000 steps.
   * Also runs the action that notifies the observers about the changes.
   */
  private void runThousandSteps() {
    chaosGame.runSteps(10000);
    chaosGame.actionToNotifyObserversAbout();
  }

  /**
   * Method that clears the canvas.
   */
  private void clearCanvas(){
    chaosGameCanvas.clear();
  }
  /**
   * Method that creates a preset button.
   */
  private Button createPresetButton(String presetName) {
    Button presetButton = new Button(presetName);
    presetButton.setOnAction(e -> {
      String filePath = presetName + ".txt";
      ChaosGameDescription chaosGameDescription = readChaosGameDescriptionFromFile(filePath);
      assert chaosGameDescription != null;
      isAffine = (chaosGameDescription.getTransformType() == AffineTransform2D.class);
      chaosGame = new ChaosGame(chaosGameDescription, 500, 500);
      chaosGameCanvas = chaosGame.getCanvas();
      canvas = createCanvas(500, 500);
      chaosGamePane.getChildren().add(canvas);
      gc = canvas.getGraphicsContext2D();
    });
    return presetButton;
  }

  /**
   * Set the vbox for the layout.
   */
  private void setLayout() {
    root = new VBox();
    root.prefWidthProperty().bind(layout.widthProperty());
    layout.getChildren().add(root);

    setNavigationBar();
    setTitleRow();
    setBodyRow();
    setFooterRow();
    root.getChildren().addAll(navigationRow, titleRow, bodyRow, footerRow);
  }
  /**
   * Method that retrieves the chaos game description from a file.
   * The {@link ChaosGame} object is initialised with the retrieved description, which in turn initialises a {@link ChaosCanvas} object.
   * This initialises a {@link Canvas} object, which is added to the layout. The canvas is where the fractal is drawn.
   * The {@link GraphicsContext} object is retrieved from the canvas and used to draw the fractal.
   */
  private void startGame(){
    ChaosGameDescription chaosGameDescription = readChaosGameDescriptionFromFile("Affine.txt");
    assert chaosGameDescription != null;
    isAffine = (chaosGameDescription.getTransformType() == AffineTransform2D.class);
    chaosGame = new ChaosGame(chaosGameDescription, 500, 500);
    chaosGameCanvas = chaosGame.getCanvas();
    canvas = createCanvas(500, 500);
    chaosGamePane.getChildren().add(canvas);
    gc = canvas.getGraphicsContext2D();

  }

  /**
   * Method that reads the chaosGameDescription object from a file.
   */
  private ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH + fileName);
  }
  private Canvas createCanvas(double width, double height) {
    Canvas canvas = new Canvas(width, height);
    return canvas;
  }
  /**
   * Method to set the scene of the stage.
   *
   * @param primaryStage the stage to set the scene for
   */
  private void setScene(Stage primaryStage) {
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }




  /**
   * Method that adds the navigation bar to the layout and adds the buttons to the navigation bar.
   */
  private void setNavigationBar() {
    getHelpButton = new Button("User Manual");
    navigationRow = new HBox();
    navigationRow.prefWidthProperty().bind(root.widthProperty());
    navigationRow.getChildren().addAll(getHelpButton);
    navigationRow.setAlignment(Pos.CENTER);
  }

  /**
   * Method that sets the scene title.
   * This involves creating the text field, along with the positioning and padding.
   */
  private void setTitleRow() {
    titleRow = new HBox();
    layout.getChildren().add(titleRow);
    sceneHeading = new TextField( ("Chaos Game"));
    sceneHeading.setEditable(false);
    sceneHeading.setAlignment(Pos.CENTER);
    titleRow.getChildren().add(sceneHeading);
    titleRow.setAlignment(Pos.CENTER);
  }

  /**
   * Method that sets the body row.
   */
  private void setBodyRow() {
    bodyRow = new HBox();
    bodyRow.prefWidthProperty().bind(root.widthProperty());
    leftBodyRow = new VBox();
    chaosGamePane = new StackPane();
    rightBodyRow = new VBox();
    startGame();
    rightBodyRow.getChildren().add(createPresetButton("Affine"));
    rightBodyRow.getChildren().add(createPresetButton("Barnsley"));
    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
  }
  /**
   * Method that sets the footer row.
   */
  private void setFooterRow() {
    footerRow = new HBox();
    footerRow.prefWidthProperty().bind(root.widthProperty());
    addThousandPixelsButton = new Button("Add 10000");
    clearCanvasButton = new Button("Clear Canvas");
    footerRow.getChildren().addAll(addThousandPixelsButton, clearCanvasButton);
    footerRow.setAlignment(Pos.CENTER);
  }
}
