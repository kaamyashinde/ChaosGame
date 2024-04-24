package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.Controller;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
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

public class AffineScene extends Application implements ChaosGameObserver {
  AnchorPane layout = new AnchorPane();
  Button addThousandPixelsButton;
  Button getHelpButton;
  Button clearCanvasButton;
  Button switchToJuliaButton;
  Controller controller;
  GraphicsContext gc;
  HBox bodyRow;
  HBox footerRow;
  HBox navigationRow;
  HBox titleRow;
  StackPane chaosGamePane;
  TextField sceneTitle;
  VBox leftBodyRow;
  VBox rightBodyRow;
  VBox root;
  private Canvas canvas;
  private ChaosCanvas chaosGameCanvas;
  private ChaosGame chaosGame;

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
    switchToJuliaButton.setOnAction(e -> controller.switchToJulia());
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
    String filePath = "src/main/java/edu/ntnu/idatt2003/resources/";
    ChaosGameDescription chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath + "Default.txt");
    assert chaosGameDescription != null;
    chaosGame = new ChaosGame(chaosGameDescription, 500, 500);
    chaosGameCanvas = chaosGame.getCanvas();
    canvas = new Canvas(chaosGameCanvas.getWidth(), chaosGameCanvas.getHeight());
    chaosGamePane.getChildren().add(canvas);
    gc = canvas.getGraphicsContext2D();

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
    switchToJuliaButton = new Button("Switch to Julia");
    getHelpButton = new Button("Get Help");
    navigationRow = new HBox();
    navigationRow.prefWidthProperty().bind(root.widthProperty());
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    navigationRow.getChildren().addAll(switchToJuliaButton, spacer, getHelpButton);
    navigationRow.setAlignment(Pos.CENTER);
  }

  /**
   * Method that sets the scene title.
   * This involves creating the text field, along with the positioning and padding.
   */
  private void setTitleRow() {
    titleRow = new HBox();
    layout.getChildren().add(titleRow);
    sceneTitle = new TextField("Affine Transformation");
    sceneTitle.setEditable(false);
    sceneTitle.setAlignment(Pos.CENTER);
    titleRow.getChildren().add(sceneTitle);
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

    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
  }
  /**
   * Method that sets the footer row.
   */
  private void setFooterRow() {
    footerRow = new HBox();
    footerRow.prefWidthProperty().bind(root.widthProperty());
    addThousandPixelsButton = new Button("Add 10");
    clearCanvasButton = new Button("Clear Canvas");
    footerRow.getChildren().addAll(addThousandPixelsButton, clearCanvasButton);
    footerRow.setAlignment(Pos.CENTER);
  }
}
