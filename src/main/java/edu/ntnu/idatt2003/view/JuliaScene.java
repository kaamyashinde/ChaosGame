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

public class JuliaScene extends Application {
  Button switchToAffine;
  Button getHelp;
  Controller controller;
  HBox navigationBar;
  TextField sceneTitle;
  StackPane chaosGamePane;
  AnchorPane layout = new AnchorPane();
  private ChaosGameDescription chaosGameDescription;
  public static void main(String[] args) {
    launch(args);
  }
  @Override
  public void start(Stage primaryStage) {
    setNavigationBar();
    setSceneTitle();
    setChaosGamePane();
    drawFractal(setChaosGame());
    setScene(primaryStage);

    controller = new Controller(primaryStage);
    switchToAffine.setOnAction(e -> controller.switchToAffine());

  }
  public void drawFractal(ChaosGame chaosGame) {
    chaosGame.runSteps(1000000);
    ChaosCanvas chaosCanvas = chaosGame.getCanvas();
    int[][] canvasArray = chaosCanvas.getCanvasArray();

    // Create a new Canvas and add it to the layout
    Canvas canvas = new Canvas(chaosCanvas.getWidth(), chaosCanvas.getHeight());
    layout.getChildren().add(canvas);

    // Position the Canvas in the center
    AnchorPane.setTopAnchor(canvas, 100.0);
    AnchorPane.setBottomAnchor(canvas, 100.0);
    AnchorPane.setLeftAnchor(canvas, 200.0);
    AnchorPane.setRightAnchor(canvas, 100.0);

    // Retrieve the GraphicsContext
    GraphicsContext gc = canvas.getGraphicsContext2D();

    // Iterate over the canvasArray and draw a rectangle for each value that is not 0
    for (int y = 0; y < canvasArray.length; y++) {
      for (int x = 0; x < canvasArray[y].length; x++) {
        if (canvasArray[y][x] != 0) {
          gc.setFill(Color.BLACK);
          gc.fillRect(x, y, 1, 1);
        }
      }
    }
    StackPane.setAlignment(layout, Pos.CENTER);
  }

  private ChaosGameDescription retrieveChaosGameDescription(){
    String filePath = "src/main/java/resources/";
    chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath + "Default.txt");
    return chaosGameDescription;
  }
  private ChaosGame setChaosGame() {
    return new ChaosGame(retrieveChaosGameDescription(), 500, 500);
  }

  /**
   * Method that adds the navigation bar to the layout and adds the buttons to the navigation bar.
   */
  private void setNavigationBar() {
    initiateNavBarButtons();
    navigationBar = new HBox();
    layout.getChildren().add(navigationBar);
    AnchorPane.setTopAnchor(navigationBar, 10.0);
    AnchorPane.setLeftAnchor(navigationBar, 10.0);
    AnchorPane.setRightAnchor(navigationBar, 10.0);

    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    navigationBar.getChildren().addAll(switchToAffine, spacer, getHelp);
  }

  /**
   * Initiate the buttons to add to the navigation bar.
   */
  private void initiateNavBarButtons() {
    switchToAffine = new Button("Switch to Julia");
    getHelp = new Button("Get Help");
  }


  /**
   * Method that sets the scene title.
   * This involves creating the text field, along with the positioning and padding.
   */
  private void setSceneTitle(){
    sceneTitle = new TextField("Julia Transformation");
    sceneTitle.setEditable(false);
    sceneTitle.setAlignment(Pos.CENTER);
    layout.getChildren().add(sceneTitle);
    AnchorPane.setTopAnchor(sceneTitle, 50.0);
    AnchorPane.setLeftAnchor(sceneTitle, 10.0);
    AnchorPane.setRightAnchor(sceneTitle, 10.0);
  }
  private void setChaosGamePane(){
    chaosGamePane = new StackPane();
    layout.getChildren().add(chaosGamePane);
    AnchorPane.setTopAnchor(chaosGamePane, 100.0);
    AnchorPane.setLeftAnchor(chaosGamePane, 10.0);
    AnchorPane.setRightAnchor(chaosGamePane, 10.0);
    AnchorPane.setBottomAnchor(chaosGamePane, 10.0);
  }

  /**
   * Method to set the scene of the stage.
   * @param primaryStage the stage to set the scene for
   */
  private void setScene(Stage primaryStage){
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }


}
