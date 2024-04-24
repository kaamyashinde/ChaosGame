package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.Controller;
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

public class AffineScene extends Application {
  AnchorPane layout = new AnchorPane();
  Button addTen;
  Button getHelp;
  Button startGame;

  Button switchToJulia;
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

    setScene(primaryStage);
    controller = new Controller(primaryStage);
    switchToJulia.setOnAction(e -> controller.switchToJulia());
    addTen.setOnAction(e -> runTenSteps());
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
   * A chaos game is initialised with the chaos game description.
   */
  private void startGame(){
    String filePath = "src/main/java/edu/ntnu/idatt2003/resources/";
   ChaosGameDescription chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath + "Default.txt");
    assert chaosGameDescription != null;
    chaosGame = new ChaosGame(chaosGameDescription, 500, 500);
    chaosGameCanvas = chaosGame.getCanvas();
    canvas = new Canvas(chaosGameCanvas.getWidth(), chaosGameCanvas.getHeight());
  }

  /**
   * Method that runs the chaos game for ten steps and stores the canvas.
   */
  private void runTenSteps() {
    chaosGame.runSteps(1000);
    drawFractal();
  }
  /**
   * Method that uses the chaos game canvas to draw the fractal.
   */
  private void drawFractal(){
    int[][] canvasArray = chaosGame.getCanvas().getCanvasArray();
    chaosGamePane.getChildren().add(canvas);

    gc = canvas.getGraphicsContext2D();
    for (int y = 0; y < canvasArray.length; y++) {
      for (int x = 0; x < canvasArray[y].length; x++) {
        if (canvasArray[y][x] != 0) {
          gc.setFill(Color.BLACK);
          gc.fillRect(x, y, 1, 1);
        }
      }
    }
    StackPane.setAlignment(bodyRow, Pos.CENTER);
  }



  /**
   * Method that adds the navigation bar to the layout and adds the buttons to the navigation bar.
   */
  private void setNavigationBar() {
    switchToJulia = new Button("Switch to Julia");
    getHelp = new Button("Get Help");
    navigationRow = new HBox();
    navigationRow.prefWidthProperty().bind(root.widthProperty());
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    navigationRow.getChildren().addAll(switchToJulia, spacer, getHelp);
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
    addTen = new Button("Add 10");
    footerRow.getChildren().add(addTen);
    footerRow.setAlignment(Pos.CENTER);


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
}
