package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.controller.*;
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
 * @version 0.5
 * @since 3.0.0
 */
public class DisplayScene implements ChaosGameObserver {
  AnchorPane layout;
  GameController gameController;
  ObserverActionController observerActionController;
  FileController fileController;
  EmptyFractalController emptyFractalController;
  TextFieldsController textFieldsController;
  Button runIterations;
  VBox leftBodyRow;
  VBox rightBodyRow;
  GraphicsContext graphicsContext;
  TextField numberOfTransformations;
  EditValuesPopUp editValuesPopUp;
  Button editCButton;
  Button editMaxAndMinButton;
  Button editAffineTransformationsButton;

  public DisplayScene() {
    gameController = new GameController();
    observerActionController = new ObserverActionController();
    fileController = new FileController();
    emptyFractalController = new EmptyFractalController();
    textFieldsController = new TextFieldsController();
    editValuesPopUp = new EditValuesPopUp();
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
    leftBodyRow = new VBox();

    ComboBox<String> fileDropDown = fileController.getFileDropDown();

    //registerButton.setOnAction(e-> gameController.updateGameFromFile(this));
   /* registerButton.setOnAction(e -> {
      // Create a new file when the register button is clicked
      gameController.createEmptyFractal(false, 0); // false for Julia, true for Affine

      // Update the dropdown list
    });*/
    leftBodyRow.getChildren().addAll(fileDropDown);


    createEmptyFractals();
    graphicsContext = null;
    Pair<StackPane, GraphicsContext> pairContainingPaneAndContext = gameController.createGamePaneCanvas(500, 500, this);
    StackPane chaosGamePane = pairContainingPaneAndContext.getKey();
    graphicsContext = pairContainingPaneAndContext.getValue();

    rightBodyRow = new VBox();
    editMenuButtons();

    rightBodyRow.getChildren().add(createPresetFractalButton("Julia"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Barnsley"));
    rightBodyRow.getChildren().add(createPresetFractalButton("Sierpinski"));


    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }


  private void editMenuButtons() {
    Button editCurrentDescription = new Button("Edit Current Description");
    Button editSelectedDescription = new Button("Edit Selected Description");
    leftBodyRow.getChildren().addAll(editCurrentDescription, editSelectedDescription);
    editCurrentDescription.setOnAction(e -> editCurrentDescription());
    editSelectedDescription.setOnAction(e -> editSelectedDescription());
  }

  private void editCurrentDescription() {
     editMaxAndMinButton = new Button("Edit Max and Min");
    editMaxAndMinButton.setOnAction(e -> editValuesPopUp.createEditMaxAndMinPopup());


     editCButton = new Button("Edit C");
    editCButton.setOnAction(e -> editValuesPopUp.createConstantCPopup());

     editAffineTransformationsButton = new Button("Edit Affine Transformations");
    editAffineTransformationsButton.setOnAction(e -> editValuesPopUp.displayAffine());
    if (!rightBodyRow.getChildren().contains(editMaxAndMinButton)) {
      rightBodyRow.getChildren().addAll(editMaxAndMinButton, editCButton, editAffineTransformationsButton);
    }
  }
  private void editSelectedDescription() {
    rightBodyRow.getChildren().removeAll(editMaxAndMinButton, editCButton, editAffineTransformationsButton);
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
   * Method that creates the empty fractals.
   */

  private void createEmptyFractals() {
    TextField fileName = new TextField();
    fileName.setPromptText("Enter file name");
    numberOfTransformations = new TextField();
    numberOfTransformations.setPromptText("Enter number of transformations");
    Button registerFileButton = new Button("Register File");

    Button switchButton = new Button("Switch to " + "Julia");
    switchButton.setOnAction(e -> emptyFractalController.switchFractalToBeCreated(switchButton, leftBodyRow, numberOfTransformations));
    registerFileButton.setOnAction(e -> {
      emptyFractalController.toggleBetweenTheCreationOfTransformations(fileName, numberOfTransformations);
      fileController.updateFileDropDown();

    });

    leftBodyRow.getChildren().addAll(fileName, registerFileButton, switchButton);

  }

  /**
   * Method that returns the footer row.
   */
  private HBox footerHBox() {
    HBox footerRow = new HBox();
    TextField iterations = new TextField();
    iterations.setPromptText("Enter number of iterations");
    runIterations = new Button("Run iterations");
    runIterations.setOnAction(e -> {
          int steps = Integer.parseInt(iterations.getText());
          gameController.runGame(steps);
        }
    );

    Button clearCanvasButton = new Button("Clear Canvas");
    clearCanvasButton.setOnAction(e -> gameController.clearCanvas());
    footerRow.getChildren().addAll(iterations, runIterations, clearCanvasButton);
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
