package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.controller.*;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.observer.ChaosGameObserver;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;



/**
 * Class that creates the scene for the Chaos Game.
 * The scene consists of a navigation row, a title row, a body row and a footer row.
 * The body row is further divided into three columns.
 * The left column is responsible for the actions related to saving the configurations.
 * The middle column is where the canvas is placed.
 * The right column is responsible for the actions related to changing values of the configurations,
 * along with the option to choose from a predefined set of fractals.
 * The footer row is responsible for the actions related to running
 * the game and clearing the canvas.
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
  VBox root;
  VBox leftBodyRow;
  VBox displayEditOptions;
  VBox rightBodyRow;
  List<Button> buttons;
  GraphicsContext graphicsContext;
  TextField numberOfTransformations;
  EditValuesPopUp editValuesPopUp;
  Button editCbutton;
  Button editMaxAndMinButton;
  Button editAffineTransformationsButton;
  TextField fileName;
  ImageView chaosGameImageView;

  /**
   * Constructor for the DisplayScene class.
   */
  public DisplayScene() {
    gameController = GameController.getInstance();
    observerActionController = new ObserverActionController();
    fileController = new FileController();
    emptyFractalController = new EmptyFractalController();
    descriptionValuesController = new DescriptionValuesController();
    editValuesPopUp = new EditValuesPopUp();
  }

  /**
   * Method that updates the canvas by adding a colored pixel at the given coordinates.
   * This method is inherited from the ChaosGameObserver interface.
   *
   * @param x0 The x-coordinate of the pixel.
   * @param x1 The y-coordinate of the pixel.
   */
  @Override
  public void updateAddPixel(double x0, double x1) {
    if (gameController.getUseGradient()) {
      observerActionController.addGradientColor(x0, x1, graphicsContext);
    } else {
      observerActionController.addColorBasedOnCount(x0, x1, graphicsContext);
    }
  }

  /**
   * Method that updates the canvas by removing a colored pixel at the given coordinates.
   * This method is inherited from the ChaosGameObserver interface.
   *
   * @param x0 The x-coordinate of the pixel.
   * @param x1 The y-coordinate of the pixel.
   */
  @Override
  public void updateRemovePixel(double x0, double x1) {
    observerActionController.removeColor(x0, x1, graphicsContext);
  }

  /**
   * Method that creates the buttons for the different fractals and returns them.
   *
   * @param fractalName The name of the fractal.
   * @return The button for the fractal.
   */

  private Button createPresetFractalButton(String fractalName) {
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

  /**
   * Method that creates the top row of the footer.
   *
   * @return The BorderPane with the buttons for running the iterations and clearing the canvas, along with the user manual button.
   */
  private BorderPane footerTopRow() {
    BorderPane footerTopRow = new BorderPane();
    centeredContentInFooterTopRow(footerTopRow);
    rightContentToFooterTopRow(footerTopRow);
    return footerTopRow;
  }

  /**
   * Method that creates the body row by creating three VBoxes and a StackPane.
   * The StackPane is where the canvas is placed and is at the centre of the body row.
   */
  private HBox bodyHbox() {
    HBox bodyRow = new HBox();
    bodyRow.getChildren().addAll(leftBodyRow(), getChaosGamePane(), rightBodyRow());
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }

  /**
   * Method that creates the bottom row of the footer.
   *
   * @return The HBox with the buttons for changing the color mode.
   */
  private HBox footerBottomRow() {
    HBox footerBottomRow = new HBox();
    gameController.setUseGradient(true);
    Button gradientColorMode = new Button("Gradient Color");
    HBox gradientColorModeHBox = getStyledButtonInHBox(gradientColorMode);
    gradientColorMode.setOnAction(e -> gameController.setUseGradient(true));
    Button countColorMode = new Button("Count Color");
    HBox countColorModeHBox = getStyledButtonInHBox(countColorMode);
    changeColorMode(true, gradientColorMode, countColorMode);
    countColorMode.setOnAction(e -> changeColorMode(false, countColorMode, gradientColorMode));
    gradientColorMode.setOnAction(e -> changeColorMode(true, gradientColorMode, countColorMode));
    footerBottomRow.getChildren().addAll(gradientColorModeHBox, countColorModeHBox);
    footerBottomRow.prefHeight(100);
    footerBottomRow.setAlignment(Pos.CENTER);
    return footerBottomRow;
  }


  /**
   * Method that creates the combo box for the files and places it in an HBox.
   *
   * @param dropDownMenu The VBox where the drop down menu is placed.
   * @return The HBox with the drop down menu.
   */
  private HBox getDropDownInHbox(VBox dropDownMenu) {
    HBox fileDropDownHbox = new HBox();
    ComboBox<String> fileDropDown = fileController.getFileDropDown();
    fileDropDownHbox.getChildren().add(fileDropDown);
    HBox.setMargin(fileDropDown, new Insets(10));
    fileDropDownHbox.setAlignment(Pos.CENTER);
    fileDropDown.getStyleClass().add("my-combo-box");
    fileDropDown.setPromptText("Select a file");
    dropDownMenu.setAlignment(Pos.CENTER);
    return fileDropDownHbox;
  }

  /**
   * Method that helps create the padding for the buttons.
   *
   * @param button The button that is being styled.
   * @return The HBox with the button.
   */
  private HBox getStyledButtonInHBox(Button button) {
    HBox styledButtonBox = new HBox();
    styledButtonBox.getChildren().add(button);
    styledButtonBox.setAlignment(Pos.CENTER);
    styledButtonBox.setPadding(new Insets(5));
    return styledButtonBox;
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
    inputTextField.setFocusTraversable(false);
    inputTextField.setEditable(false);
    inputTextField.prefWidthProperty().bind(styledTextFiled.widthProperty());
    inputTextField.setAlignment(Pos.CENTER);
    inputTextField.setAlignment(Pos.CENTER);
    inputTextField.getStyleClass().add("section-heading");
    return styledTextFiled;
  }

  /**
   * Method that sets the title of the scene to "Chaos Game" and returns the HBox with the title.
   *
   * @return The HBox with the title.
   */
  private HBox titleHbox() {
    HBox titleRow = new HBox();
    titleRow.prefWidthProperty().bind(layout.widthProperty());
    titleRow.setPrefHeight(90);
    titleRow.setMinHeight(100);
    titleRow.setMaxHeight(200);

    TextField sceneHeading = gameTitle();
    sceneHeading.prefWidthProperty().bind(titleRow.widthProperty());
    titleRow.getChildren().add(sceneHeading);
    titleRow.setAlignment(Pos.CENTER);
    return titleRow;
  }

  /**
   * Method that creates the canvas for the Chaos Game
   * and returns the StackPane containing the canvas.
   *
   * @return The StackPane containing the canvas.
   */
  private ImageView getChaosGamePane() {
    graphicsContext = null;
    Pair<ImageView, GraphicsContext> pairContainingPaneAndContext = gameController
        .createGamePaneCanvas(500, 500, this);
    chaosGameImageView = pairContainingPaneAndContext.getKey();
    chaosGameImageView.fitWidthProperty()
        .bind(Bindings.min(layout.widthProperty().divide(2), 500));
    chaosGameImageView.fitHeightProperty()
        .bind(Bindings.min(layout.heightProperty().divide(2), 500));
    chaosGameImageView.setPreserveRatio(true);
    chaosGameImageView.setSmooth(true);
    chaosGameImageView.setCache(true);
    graphicsContext = pairContainingPaneAndContext.getValue();
    return chaosGameImageView;
  }

  /**
   * Method that sets the title of the scene to "Chaos Game"
   * and returns the TextField with the title.
   *
   * @return The TextField with the title.
   */
  private TextField gameTitle() {
    TextField sceneHeading = new TextField(("Chaos Game"));
    sceneHeading.setFocusTraversable(false);
    sceneHeading.setText("Chaos Game");
    sceneHeading.prefWidthProperty().bind(layout.widthProperty());
    sceneHeading.getStyleClass().add("title");
    sceneHeading.setEditable(false);
    sceneHeading.setAlignment(Pos.CENTER);
    return sceneHeading;
  }

  /**
   * Method that is responsible for creating the empty fractals.
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

    HBox emptyFractalsDisplayHbox = styleTextFields(new TextField("Empty Fractal"));

    inputFields.getChildren().addAll(emptyFractalsDisplayHbox, fileName);
    emptyFractal.getChildren().addAll(inputFields, registerFileButton, switchButton);
    emptyFractal.setAlignment(Pos.CENTER);
    VBox.setMargin(emptyFractal, new Insets(10, 20, 10, 20));

    switchButton.setOnAction(e -> emptyFractalController
        .switchFractalToBeCreated(switchButton, inputFields, numberOfTransformations));
    registerFileButton.setOnAction(e -> createEmptyFractalFileAction());

    return emptyFractal;
  }

  /**
   * Method that displays the options for the presets.
   *
   * @return The VBox with the preset options.
   */
  private VBox displayPresetsOptions() {
    VBox displayPresetsOptions = new VBox();
    HBox presetsDisplayHbox = styleTextFields(new TextField("Choose a fractal"));
    VBox presetsButtons = new VBox();

    initiateButtonsListAndAddPresetButtons();

    buttons.forEach(button -> presetsButtons.getChildren().add(button));
    displayPresetsOptions.getChildren().addAll(presetsDisplayHbox, presetsButtons);
    displayPresetsOptions.setAlignment(Pos.CENTER);
    VBox.setMargin(displayPresetsOptions, new Insets(20));
    return displayPresetsOptions;
  }

  /**
   * Method that creates the drop down menu for the files.
   *
   * @return The VBox with the drop down menu.
   */
  private VBox dropDownMenu() {
    TextField dropDownMenuDisplay = new TextField("Read file:");
    HBox dropDownMenuDisplayHbox = styleTextFields(dropDownMenuDisplay);

    VBox dropDownMenu = new VBox();

    Button updateChaosGameButton = new Button("Update Game");
    updateChaosGameButton.setOnAction(e -> dropDownMenuUpdateChaosGameAction());
    dropDownMenu.getChildren().addAll(dropDownMenuDisplayHBox, getDropDownInHBox(dropDownMenu), updateChaosGameButton);
    VBox.setMargin(dropDownMenu, new Insets(10, 20, 10, 20));
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
    HBox sectionHeadingEdit = styleTextFields(new TextField("Edit Configuration"));
    editMenu.getChildren().add(sectionHeadingEdit);
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
   * Method that returns the footer row.
   */
  private VBox footerHBox() {
    VBox footerRow = new VBox();
    footerRow.getChildren().addAll(footerTopRow(), footerBottomRow());
    footerRow.prefHeightProperty().bind(root.heightProperty());
    return footerRow;
  }

  /**
   * Method that creates the left column of the body row.
   *
   * @return The VBox with the left column.
   */
  private VBox leftBodyRow() {
    leftBodyRow = new VBox();
    leftBodyRow.getStyleClass().add("inner-border");
    leftBodyRow.getChildren().addAll(createEmptyFractals(), dropDownMenu(), saveCurrentDescToFile());
    leftBodyRow.prefWidthProperty().bind(root.widthProperty());
    leftBodyRow.prefHeightProperty().bind(root.heightProperty());
    return leftBodyRow;
  }

  /**
   * Method that creates the right column of the body row.
   *
   * @return The VBox with the right column.
   */
  private VBox rightBodyRow() {
    rightBodyRow = new VBox();
    rightBodyRow.getStyleClass().add("inner-border");
    rightBodyRow.prefWidthProperty().bind(root.widthProperty());
    rightBodyRow.prefHeightProperty().bind(root.heightProperty());
    displayEditOptions = new VBox();
    rightBodyRow.getChildren().addAll(displayPresetsOptions(),
        editMenuButtons(), displayEditOptions);
    return rightBodyRow;
  }

  /**
   * Method that saves the current description to a file.
   */
  private VBox saveCurrentDescToFile() {

    VBox saveCurrentDesc = new VBox();

    HBox saveToFileDisplayHbox = styleTextFields(new TextField("Save to file:"));
    TextField saveToFile = new TextField();
    saveToFile.setPromptText("Enter file name");

    Button saveCurrentDescToFile = new Button("Save Current Config");
    saveCurrentDescToFile.setAlignment(Pos.CENTER);
    saveCurrentDescToFile.setOnAction(e ->
        saveCurrentDescToFileAction(saveToFile)
    );
    saveCurrentDesc.getChildren().addAll(saveToFileDisplayHBox, saveToFile, saveCurrentDescToFile);
    saveCurrentDesc.setAlignment(Pos.CENTER);
    VBox.setMargin(saveCurrentDesc, new Insets(10, 20, 10, 20));
    return saveCurrentDesc;
  }

  /**
   * Method that sets the root VBox to the layout and adds the components to the root.
   *
   * @return The root VBox.
   */
  private VBox setUpLayoutAndAddComponents() {
    root = new VBox();
    root.prefWidthProperty().bind(layout.widthProperty());
    layout.getChildren().add(root);
    root.getChildren().addAll(titleHBox(), bodyHBox(), footerHBox());
    return root;
  }

  /**
   * Method that centers the content in the footer top row.
   * The content consists of a text field for the number of iterations, a button for running the iterations and a button for clearing the canvas.
   *
   * @param footerTopRow The BorderPane where the content is placed.
   */
  private void centeredContentInFooterTopRow(BorderPane footerTopRow) {
    TextField iterations = new TextField();
    iterations.setPromptText("Enter number of iterations");
    iterations.setText("10000");
    iterations.getStyleClass().add("number-of-iterations");
    iterations.setPrefWidth(100);
    runIterations = new Button("Run iterations");
    HBox runIterationsButtonHBox = getStyledButtonInHBox(runIterations);
    runIterations.setOnAction(e -> runIterationsAction(iterations));
    Button clearCanvasButton = new Button("Clear Canvas");
    HBox clearCanvasButtonHBox = getStyledButtonInHBox(clearCanvasButton);
    clearCanvasButton.setOnAction(e -> gameController.clearCanvas(chaosGameImageView));
    HBox centerContent = new HBox(iterations, runIterationsButtonHBox, clearCanvasButtonHBox);

    centerContent.setPadding(new Insets(0, 0, 0, 150));
    centerContent.setAlignment(Pos.CENTER);

    footerTopRow.setCenter(centerContent);
  }

  /**
   * Method that changes the color mode based on the button that is clicked.
   *
   * @param useGradient   The boolean value that determines if the gradient color mode is used.
   * @param toStyle       The button that is clicked.
   * @param toRemoveStyle The button that is not clicked.
   */
  private void changeColorMode(boolean useGradient, Button toStyle, Button toRemoveStyle) {
    gameController.setUseGradient(useGradient);
    toRemoveStyle.getStyleClass().remove("button-selected");
    toStyle.getStyleClass().add("button-selected");
  }

  /**
   * Method that is called when the user registers a file for the creation of an empty fractal
   */
  private void createEmptyFractalFileAction() {
    try {
      emptyFractalController
          .toggleBetweenTheCreationOfTransformations(fileName, numberOfTransformations);
      fileController.updateFileDropDown();
      fileName.clear();
      numberOfTransformations.clear();
    } catch (Exception exception) {
      if (fileName.getText().isEmpty() || fileName.getText().isBlank()) {
        UserFeedback.displayError("No file name was given.",
            "Please enter a file name and try again.");
      } else {
        UserFeedback.displayError("No number of transformations was given.",
            "Please enter the number of transformations and try again.");
      }
    }
  }


  /**
   * Method that displays the edit options for the values of the Chaos Game.
   *
   * @param displayEditOptions The VBox where the edit options are displayed.
   */
  private void displayEditOptions(VBox displayEditOptions) {

    displayEditOptions.getChildren()
        .removeAll(editMaxAndMinButton, editCbutton, editAffineTransformationsButton);

    editMaxAndMinButton = new Button("Edit Max and Min");
    editMaxAndMinButton.setOnAction(e -> editValuesPopUp.createMaxAndMinPopup());
    displayEditOptions.getChildren().add(editMaxAndMinButton);

    editCbutton = new Button("Edit C");
    editCbutton.setOnAction(e -> editValuesPopUp
        .createConstantCpopup());

    editAffineTransformationsButton = new Button("Edit Affine Transformations");
    editAffineTransformationsButton.setOnAction(e -> editValuesPopUp
        .createAffineTransformationPopup());

  }

  /**
   * Method that handles the set on action for the update chaos game button in the drop down menu.
   */
  private void dropDownMenuUpdateChaosGameAction() {
    buttons.forEach(button -> button.getStyleClass().remove("button-selected"));
    try {
      updateChaosGameFromSelectedFile();
    } catch (Exception exception) {
      UserFeedback.displayError("No file was chosen.",
          "Please choose a file from the drop down and try again.");
    }
  }

  private void editCurrentDescription() {
    editValuesPopUp.setCurrentChaosGameDescription();
    handleEditOption(1);
  }

  /**
   * Method that creates the buttons for editing the selected description.
   */
  private void editSelectedDescription() {
    String selectedFile = fileController.getFileDropDown()
        .getSelectionModel().getSelectedItem();
    ChaosGameDescription description = fileController
        .readChaosGameDescriptionFromAppFiles(selectedFile);
    editValuesPopUp.setInputChaosGameDescription(description);

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
      displayEditOptions.getChildren().add(editCbutton);
    }

  }

  /**
   * Method that initiates the buttons list and adds the preset buttons to the list.
   * If the persistence is null, the first button is selected as a default.
   */
  private void initiateButtonsListAndAddPresetButtons() {
    buttons = new ArrayList<>();

    buttons.add(createPresetFractalButton("Julia"));
    if (gameController.getPersistenceIsNull()) {
      buttons.get(0).getStyleClass().add("button-selected");
    }
    buttons.add(createPresetFractalButton("Barnsley"));
    buttons.add(createPresetFractalButton("Sierpinski"));
    if (gameController.getPersistenceIsNull()) {
      buttons.get(0).getStyleClass().add("button-selected");
    }
  }

  /**
   * Method that creates the right content for the footer top row.
   * The right content consists of a button for the user manual.
   *
   * @param footerTopRow The BorderPane where the right content is placed.
   */
  private void rightContentToFooterTopRow(BorderPane footerTopRow) {
    Button userManual = new Button("User Manual");
    HBox newButtonHBox = getStyledButtonInHBox(userManual);
    HBox.setMargin(newButtonHBox, new Insets(5));
    Region spacer = new Region();
    spacer.setMinWidth(40);
    spacer.setPrefWidth(40);
    spacer.setMaxWidth(40);
    newButtonHBox.getChildren().add(spacer);
    userManual.setOnAction(e -> UserFeedback.displayUserManual());
    footerTopRow.setRight(newButtonHBox);
  }

  /**
   * Method that handles the action of running the iterations.
   *
   * @param iterations The text field where the number of iterations is entered.
   */
  private void runIterationsAction(TextField iterations) {
    try {
      ValidationController.validateInteger(iterations.getText());
      int steps = Integer.parseInt(iterations.getText());
      gameController.runGame(steps, chaosGameImageView);
    } catch (Exception exception) {
      UserFeedback.displayError("Number of iterations has to be a positive integer.",
          "Please enter a positive integer to run the application.");
    }
  }

  /**
   * Method that handles the action of saving the current description to a file.
   *
   * @param saveToFile The text field where the file name is entered.
   */
  private void saveCurrentDescToFileAction(TextField saveToFile) {
    try {
      ChaosGameDescription chaosGameDescription = gameController.getCurrentChaosGameDescription();
      ValidationController.validateFileName(saveToFile.getText());
      fileController
          .writeChaosGameDescriptionToAppFiles(chaosGameDescription, saveToFile.getText());
      fileController.updateFileDropDown();
      saveToFile.clear();
    } catch (Exception exception) {
      UserFeedback.displayError("No file name was given.",
          "Please enter a file name and try again.");
    }
  }

  /**
   * Method that updates the Chaos Game based on the chosen preset.
   *
   * @param caseNum  The number of the preset.
   * @param observer The observer that is observing the Chaos Game.
   */
  private void updateBasedOnChosenPreset(int caseNum, ChaosGameObserver observer) {
    ChaosGameDescription originalPreset = gameController.returnPresetDescription(caseNum);
    ChaosGameDescription newPreset = new ChaosGameDescription(originalPreset);
    gameController.updateChaosGame(new ChaosGame(newPreset, 500, 500), observer);
    gameController.updateButtonStyle(caseNum, buttons);
    updateEditButtons();
  }
  /**
   * Method that updates the edit buttons based on the current Chaos Game.
   */
  public void updateEditButtons() {
    displayEditOptions.getChildren().clear();
    HBox editCurrentDescHeading = styleTextFields(new TextField("Edit Current Description:"));
    displayEditOptions.getChildren().add(editCurrentDescHeading);
    displayEditOptions(displayEditOptions);
    displayEditOptions.setPadding(new Insets(20));

    if (gameController.isAffine()) {
      displayEditOptions.getChildren().add(editAffineTransformationsButton);
    } else {
      displayEditOptions.getChildren().add(editCButton);
    }
  }

  /**
   * Method that updates the Chaos Game from the selected file.
   */
  private void updateChaosGameFromSelectedFile() {
    String selectedFile = fileController.getFileDropDown().getSelectionModel().getSelectedItem();
    buttons.forEach(button -> button.getStyleClass().remove("button-selected"));
    ChaosGameDescription description = fileController.readChaosGameDescriptionFromAppFiles(selectedFile);
    ValidationController.validateFileName(selectedFile);
    gameController.updateChaosGame(new ChaosGame(description, 500, 500), this);
    updateEditButtons();
  }

  /**
   * Method that returns the list of the buttons that are used to manage the presets.
   *
   * @return The list of buttons.
   */
  public List<Button> getButtons() {
    return buttons;
  }

  /**
   * Method that creates the scene for the Chaos Game.
   * The scene consists of a navigation row, a title row, a body row and a footer row.
   * The controllers are initialized and the scene is returned.
   * This scene is used to display the Chaos Game in the main method.
   *
   * @param primaryStage The stage where the scene is displayed.
   * @return The scene for the Chaos Game.
   */
  public Scene getDisplay(Stage primaryStage) {
    gameController.setPrimaryStage(primaryStage);
    layout = new AnchorPane();
    layout.prefWidthProperty().bind(primaryStage.widthProperty());
    layout.prefHeightProperty().bind(primaryStage.heightProperty());
    VBox root = setUpLayoutAndAddComponents();


    AnchorPane.setTopAnchor(root, 0.0);
    AnchorPane.setBottomAnchor(root, 0.0);
    AnchorPane.setLeftAnchor(root, 0.0);
    AnchorPane.setRightAnchor(root, 0.0);

    primaryStage.setTitle("Chaos Game");
    return new Scene(layout);
  }
}
