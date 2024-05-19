package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.controller.*;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import javafx.geometry.Insets;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates the scene for the Chaos Game. The scene consists of a navigation row, a title row, a body row and a footer row.
 * The body row is further divided into three columns.
 * The left column is responsible for the actions related to saving the configurations.
 * The middle column is where the canvas is placed.
 * The right column is responsible for the actions related to changing values of the configurations, along with the option to choose from a predefined set of fractals.
 * The footer row is responsible for the actions related to running the game and clearing the canvas.
 *
 * @author Kaamya Shinde
 * @version 0.7
 * @since 0.0.3
 */
public class DisplayScene implements ChaosGameObserver {
  AnchorPane layout;
  GameController gameController;
  ObserverActionController observerActionController;
  FileController fileController;
  EmptyFractalController emptyFractalController;
  DescriptionValuesController descriptionValuesController;
  Button runIterations;
  VBox leftBodyRow;
  VBox displayEditOptions;
  VBox rightBodyRow;
  List<Button> buttons;
  GraphicsContext graphicsContext;
  TextField numberOfTransformations;
  EditValuesPopUp editValuesPopUp;
  Button editCButton;
  Button editMaxAndMinButton;
  Button editAffineTransformationsButton;
  TextField fileName;

  public DisplayScene() {
    gameController = new GameController();
    observerActionController = new ObserverActionController();
    fileController = new FileController();
    emptyFractalController = new EmptyFractalController();
    descriptionValuesController = new DescriptionValuesController();
    editValuesPopUp = new EditValuesPopUp(gameController);
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
    //root.getChildren().addAll(navigationHBox());
    root.getChildren().addAll(titleHBox(), bodyHBox(), footerHBox());
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
    navigationRow.getStyleClass().add("border");

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
    titleRow.getStyleClass().add("border");

    TextField sceneHeading = new TextField(("Chaos Game"));
    sceneHeading.setText("Chaos Game");
    sceneHeading.prefWidthProperty().bind(layout.widthProperty());
    sceneHeading.getStyleClass().add("title");
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
    bodyRow.getStyleClass().add("border");

    leftBodyRow = new VBox();
    leftBodyRow.getStyleClass().add("inner-border");

    leftBodyRow.getChildren().addAll(createEmptyFractals(), dropDownMenu(), editMenuButtons(), saveCurrentDescToFile());
    leftBodyRow.prefWidthProperty().bind(bodyRow.widthProperty());

    graphicsContext = null;
    Pair<StackPane, GraphicsContext> pairContainingPaneAndContext = gameController.createGamePaneCanvas(500, 500, this);
    StackPane chaosGamePane = pairContainingPaneAndContext.getKey();
    chaosGamePane.getStyleClass().add("inner-border");
    chaosGamePane.prefWidthProperty().bind(bodyRow.widthProperty());
    graphicsContext = pairContainingPaneAndContext.getValue();
    rightBodyRow = new VBox();
    rightBodyRow.getStyleClass().add("inner-border");
    rightBodyRow.prefWidthProperty().bind(bodyRow.widthProperty());
    displayEditOptions = new VBox();


    rightBodyRow.getChildren().addAll(displayPresetsOptions(), displayEditOptions);

    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }

  /**
   * Method that creates the empty fractals.
   */

  private VBox createEmptyFractals() {
    VBox emptyFractal = new VBox();
    VBox inputFields = new VBox();
    fileName = new TextField();
    fileName.setPromptText("Enter file name");
    numberOfTransformations = new TextField();
    numberOfTransformations.setPromptText("Enter number of transformations for empty fractal");
    Button registerFileButton = new Button("Create empty File");

    Button switchButton = new Button("Switch to Affine");
    switchButton.setOnAction(e -> emptyFractalController.switchFractalToBeCreated(switchButton, inputFields, numberOfTransformations));
    registerFileButton.setOnAction(e -> {
      emptyFractalController.toggleBetweenTheCreationOfTransformations(fileName, numberOfTransformations);
      fileController.updateFileDropDown();

    });
    HBox emptyFractalsDisplayHBox = styleTextFields(new TextField("Create an empty fractal:"));
    //leftBodyRow.getChildren().addAll(emptyFractalsDisplay,fileName, registerFileButton, switchButton);
    inputFields.getChildren().addAll(emptyFractalsDisplayHBox, fileName);
    emptyFractal.getChildren().addAll(inputFields, registerFileButton, switchButton);
    emptyFractal.setAlignment(Pos.CENTER);
    VBox.setMargin(emptyFractal, new Insets(20));

    return emptyFractal;
  }

  /**
   * Method that creates the drop down menu for the files.
   *
   * @return The VBox with the drop down menu.
   */
  private VBox dropDownMenu() {
    TextField dropDownMenuDisplay = new TextField("Read description from a file:");
    HBox dropDownMenuDisplayHBox = styleTextFields(dropDownMenuDisplay);


    VBox dropDownMenu = new VBox();
    ComboBox<String> fileDropDown = fileController.getFileDropDown();
    fileDropDown.setPromptText("Select a file");
    dropDownMenu.setAlignment(Pos.CENTER);

    Button updateChaosGameButton = new Button("Update Chaos Game");
    updateChaosGameButton.setOnAction(e -> updateChaosGameFromSelectedFile());
    dropDownMenu.getChildren().addAll(dropDownMenuDisplayHBox, fileDropDown, updateChaosGameButton);
    VBox.setMargin(dropDownMenu, new Insets(20));
    return dropDownMenu;
  }

  /**
   * Method that creates the buttons for editing the values of the Chaos Game.
   * There are two types of buttons:
   * <ol>
   *   <li>Edit Current Description: Targets the current description and edits it.</li>
   *   <li>Edit Selected Description: User can choose a file from the drop down and edit it.</li>
   * </ol>
   */
  private VBox editMenuButtons() {
    VBox editMenu = new VBox();
    Button editCurrentDescription = new Button("Edit Current Description");
    Button editSelectedDescription = new Button("Edit Selected Description");
    editMenu.getChildren().addAll(editCurrentDescription, editSelectedDescription);
    editCurrentDescription.setOnAction(e -> editCurrentDescription());
    editSelectedDescription.setOnAction(e -> editSelectedDescription());
    editMenu.setAlignment(Pos.CENTER);
    VBox.setMargin(editMenu, new Insets(20));
    return editMenu;
  }

  /**
   * Method that saves the current description to a file.
   */
  private VBox saveCurrentDescToFile() {
    VBox saveCurrentDesc = new VBox();


    HBox saveToFileDisplayHBox = styleTextFields(new TextField("Save to file:"));
    TextField saveToFile = new TextField();
    saveToFile.setPromptText("Enter file name");

    Button saveCurrentDescToFile = new Button("Save Current Description to File");
    saveCurrentDescToFile.setAlignment(Pos.CENTER);
    saveCurrentDescToFile.setOnAction(e -> {
      ChaosGameDescription chaosGameDescription = gameController.getCurrentChaosGameDescription();
      System.out.println(saveToFile.getText());
      fileController.writeChaosGameDescriptionToFile(chaosGameDescription, saveToFile.getText());
      fileController.updateFileDropDown();
    });
    saveCurrentDesc.getChildren().addAll(saveToFileDisplayHBox, saveToFile, saveCurrentDescToFile);
    saveCurrentDesc.setAlignment(Pos.CENTER);
    VBox.setMargin(saveCurrentDesc, new Insets(20));
    return saveCurrentDesc;
  }

  /**
   * Method that creates the design for the text fields.
   *
   * @param inputTextField The text field that is being styled.
   * @return the styled text field inside an HBox.
   */
  private HBox styleTextFields(TextField inputTextField) {
    HBox styledTextFiled = new HBox();
    styledTextFiled.getChildren().add(inputTextField);
    styledTextFiled.setPadding(new Insets(10));
    styledTextFiled.setStyle("-fx-background-color: #b97d6d");
    inputTextField.prefWidthProperty().bind(styledTextFiled.widthProperty());
    inputTextField.setAlignment(Pos.CENTER);
    inputTextField.setAlignment(Pos.CENTER);
    inputTextField.getStyleClass().add("section-heading");
    return styledTextFiled;
  }

  private VBox displayPresetsOptions() {
    VBox displayPresetsOptions = new VBox();
    HBox presetsDisplayHBox = styleTextFields(new TextField("Choose a preset fractal:"));
    VBox presetsButtons = new VBox();

    buttons = new ArrayList<>();

    buttons.add(createPresetFractalButton("Julia"));
    if (gameController.getPersistenceIsNull()) {
      buttons.get(0).getStyleClass().add("selected-preset");
    }
    buttons.add(createPresetFractalButton("Barnsley"));
    buttons.add(createPresetFractalButton("Sierpinski"));

    buttons.forEach(button -> presetsButtons.getChildren().add(button));
    displayPresetsOptions.getChildren().addAll(presetsDisplayHBox, presetsButtons);
    displayPresetsOptions.setAlignment(Pos.CENTER);
    VBox.setMargin(displayPresetsOptions, new Insets(20));
    return displayPresetsOptions;
  }

  /**
   * Method that updates the Chaos Game from the selected file.
   */
  public void updateChaosGameFromSelectedFile() {
    // Retrieve the selected file name from the fileDropDown ComboBox
    String selectedFile = fileController.getFileDropDown().getSelectionModel().getSelectedItem();

    // Use the readChaosGameDescriptionFromFile method to read the ChaosGameDescription from the selected file
    ChaosGameDescription description = fileController.readChaosGameDescriptionFromFile("appFiles/" + selectedFile);

    // Use the setCurrentChaosGameDescription method to update the chaosGame with the new ChaosGameDescription
    gameController.updateChaosGame(new ChaosGame(description, 500, 500), this);
  }

  /**
   * Method that displays the edit options for the values of the Chaos Game.
   *
   * @param displayEditOptions The VBox where the edit options are displayed.
   */
  private void displayEditOptions(VBox displayEditOptions) {

    displayEditOptions.getChildren().removeAll(editMaxAndMinButton, editCButton, editAffineTransformationsButton);

    editMaxAndMinButton = new Button("Edit Max and Min");
    editMaxAndMinButton.setOnAction(e -> editValuesPopUp.createEditMaxAndMinPopup(gameController));
    displayEditOptions.getChildren().add(editMaxAndMinButton);

    editCButton = new Button("Edit C");
    editCButton.setOnAction(e -> editValuesPopUp.createConstantCPopup(gameController));

    editAffineTransformationsButton = new Button("Edit Affine Transformations");
    editAffineTransformationsButton.setOnAction(e -> editValuesPopUp.displayAffine(gameController));
    Button updateButton = new Button("Update");

  }

  /**
   * Method that creates the buttons for editing the current description.
   */

  private void editCurrentDescription() {
    editValuesPopUp.setChaosGameDescription();

    // Clear the displayEditOptions before adding new buttons
    handleEditOption(1);
  }

  /**
   * Method that creates the buttons for editing the selected description.
   */
  private void editSelectedDescription() {
    String selectedFile = fileController.getFileDropDown().getSelectionModel().getSelectedItem();
    ChaosGameDescription description = fileController.readChaosGameDescriptionFromFile("appFiles/" + selectedFile);
    System.out.println("new description is:");
    editValuesPopUp.setChaosGameDescriptionWithInput(description);

    handleEditOption(0);

  }

  /**
   * Method that handles the adding of the correct edit option.
   */
  private void handleEditOption(int caseNum) {
    displayEditOptions.getChildren().clear();
    if (caseNum == 0) {
      HBox editSelectedDescHeading = styleTextFields(new TextField("Edit Selected Description:"));
      displayEditOptions.getChildren().add(editSelectedDescHeading);
    } else {
      HBox editCurrentDescHeading = styleTextFields(new TextField("Edit Current Description:"));
      displayEditOptions.getChildren().add(editCurrentDescHeading);
    }
    displayEditOptions(displayEditOptions);
    displayEditOptions.setPadding(new Insets(20));

    if (gameController.isAffine()) {
      displayEditOptions.getChildren().add(editAffineTransformationsButton);
    } else {
      displayEditOptions.getChildren().add(editCButton);
    }

  }


  /**
   * Method that returns the footer row.
   */
  private HBox footerHBox() {
    HBox footerRow = new HBox();
    footerRow.getStyleClass().add("border");
    TextField iterations = new TextField();
    iterations.setPromptText("Enter number of iterations");
    iterations.setText("10000");
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
        case "Julia" -> updateBasedOnChosenPreset(0, this);
        case "Barnsley" -> updateBasedOnChosenPreset(1, this);
        case "Sierpinski" -> updateBasedOnChosenPreset(2, this);
      }
    });
    return button;
  }

  private void updateBasedOnChosenPreset(int caseNum, ChaosGameObserver observer) {
    ChaosGameDescription originalPreset = gameController.returnPresetDescription(caseNum);
    ChaosGameDescription newPreset = new ChaosGameDescription(originalPreset);

    gameController.updateChaosGame(new ChaosGame(newPreset, 500, 500), observer);
    //gameController.choosePreset(caseNum, observer);
    gameController.updateButtonStyle(caseNum, buttons);
  }

}
