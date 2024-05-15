package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.controller.FileController;
import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.controller.ObserverActionController;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * Class that creates the scene for the Chaos Game. The scene consists of a navigation row, a title row, a body row and a footer row.
 * The body row is further divided into three columns.
 * The left column is responsible for the actions related to saving the configurations.
 * The middle column is where the canvas is placed.
 * The right column is responsible for the actions related to changing values of the configurations, along with the option to choose from a predefined set of fractals.
 * The footer row is responsible for the actions related to running the game and clearing the canvas.
 *
 * @author Kaamya Shinde
 * @version 0.3
 * @since 3.0.0
 */
public class DisplayScene implements ChaosGameObserver {
  AnchorPane layout;
  GameController gameController;
  ObserverActionController observerActionController;
  FileController fileController;
  Button addThousandPixelsButton;
  VBox rightBodyRow;
  GraphicsContext graphicsContext;

  public DisplayScene() {
    gameController = new GameController();
    observerActionController = new ObserverActionController();
    fileController = new FileController();
  }

  /**
   * Method that creates the scene for the Chaos Game. The scene consists of a navigation row, a title row, a body row and a footer row.
   * The controllers are initialized and the scene is returned.
   * This scene is used to display the Chaos Game in the main method.
   *
   * @param primaryStage The stage where the scene is displayed.
   * @return The scene for the Chaos Game.
   */
  public Scene getDisplay(Stage primaryStage) {
    layout = new AnchorPane();
    layout.prefWidthProperty().bind(primaryStage.widthProperty());

    VBox root = new VBox();
    setUpLayoutAndAddComponents(root);

    primaryStage.setTitle("Chaos Game");

    return new Scene(layout, 1000, 700);
  }

  /**
   * Method that sets up the layout and adds the components to the layout.
   * The layout is bound to the width of the stage.
   *
   * @param root The root of the layout.
   */
  private void setUpLayoutAndAddComponents(VBox root) {
    root.prefWidthProperty().bind(layout.widthProperty());
    layout.getChildren().add(root);
    root.getChildren().addAll(navigationHBox(), titleHBox(), bodyHBox(), footerHBox());
    root.getChildren().stream().filter(node -> node instanceof HBox).forEach(node -> ((HBox) node).prefWidthProperty().bind(root.widthProperty()));
  }

  /**
   * Method that updates the canvas by adding a colored pixel at the given coordinates. This method is inherited from the ChaosGameObserver interface.
   *
   * @param X0 The x-coordinate of the pixel.
   * @param X1 The y-coordinate of the pixel.
   */
  @Override
  public void updateAddPixel(double X0, double X1) {
    observerActionController.addGradientColor(X0, X1, graphicsContext);
  }

  /**
   * Method that updates the canvas by removing a colored pixel at the given coordinates. This method is inherited from the ChaosGameObserver interface.
   *
   * @param X0 The x-coordinate of the pixel.
   * @param X1 The y-coordinate of the pixel.
   */
  @Override
  public void updateRemovePixel(double X0, double X1) {
    observerActionController.removeColor(X0, X1, graphicsContext);
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
    ComboBox<String> fileDropDown = fileController.getFileDropDown();
    Button registerButton = new Button("Register");
  //registerButton.setOnAction(e-> gameController.updateGameFromFile(this));
    leftBodyRow.getChildren().addAll(fileDropDown, registerButton);
    graphicsContext = null;
    Pair<StackPane, GraphicsContext> pairContainingPaneAndContext = gameController.createGamePaneCanvas(500, 500);
    StackPane chaosGamePane = pairContainingPaneAndContext.getKey();
    graphicsContext = pairContainingPaneAndContext.getValue();

    rightBodyRow = new VBox();

    rightBodyRow.getChildren().add(createPresetFractalButton("Julia"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Barnsley"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Sierpinski"));

    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }

/*
Three different buttons for editing.
1. Edit max and min
2. Edit transformations affine
  - must take into account the number of transformations
3. Edit transformations julia

There will be the register buttons too
1. Register max and min
2. Register transformations affine (own popup)
3. Register transformations julia (own popup)


so when creating new fractal, we'll first create a file with 0s, asking the user for the number of transformations for affine there.
Then based on that the user wil be able to edit the file and use it to run the application.
 */

  /**
   * Method that returns the footer row.
   */
  private HBox footerHBox() {
    HBox footerRow = new HBox();
    addThousandPixelsButton = new Button("Add 10000");
    addThousandPixelsButton.setOnAction(e -> gameController.runGame(10000)
    );

    Button clearCanvasButton = new Button("Clear Canvas");
    clearCanvasButton.setOnAction(e -> gameController.clearCanvas());
    footerRow.getChildren().addAll(addThousandPixelsButton, clearCanvasButton);
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
        case "Sierpinski" -> gameController.choosePreset(0, this);
        case "Barnsley" -> gameController.choosePreset(1, this);
        case "Julia" -> gameController.choosePreset(2, this);
      }
    });
    return button;
  }

}
