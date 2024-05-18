package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.ChaosGameDescriptionFactory;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is the controller for the game.
 * It is responsible for handling all of the game logic.
 * This means that the model classes are used only in this controller to perform the game functions.
 * Thereafter, the methods are only called up on in the view package.
 *
 * @version 0.5
 * @see edu.ntnu.idatt2003.view.DisplayScene
 * @since 0.3.5
 */
public class GameController {
  private final FileController fileController;
  private final List<ChaosGameDescription> listOfDescriptions;
  private ChaosGame chaosGame;

  /**
   * Constructor that initialises the array lists and adds the game presets.
   */
  public GameController() {
    fileController = new FileController();
    listOfDescriptions = new ArrayList<>();
    loadChaosGamePresets();
  }

  /**
   * Method that adds an observer to the Chaos game object.
   *
   * @param observer The observer that is added to the canvas.
   */
  private void addObserverToGame(ChaosGameObserver observer) {
    if (chaosGame != null) {
      chaosGame.getCanvas().addObserver(observer);
    }
  }

  /**
   * Method that loads all the different fractal presets from the resources folder.
   * This is added to the list of descriptions.
   */
  private void loadChaosGamePresets() {

    listOfDescriptions.add(fileController.readChaosGameDescriptionFromFile("presets/Julia.txt"));
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromFile("presets/Barnsley.txt"));
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromFile("presets/Affine.txt"));
    //writePresetsToFile();
  }
  /**
   * Create the fractals using the write to file methods
   */
  private void writePresetsToFile(){
   /* ChaosGameDescription triangle = ChaosGameDescriptionFactory.createAffineChaosGameDescription(3, 0.5, 0, 0, 0.5, 0, 0, 1, 1);
    ChaosGameDescription julia = ChaosGameDescriptionFactory.createJuliaChaosGameDescription(1, -0.74543, 0.11301, -1.6, -1.0, 1.6, 1.0);
    ChaosGameDescription fern = ChaosGameDescriptionFactory.createbarnsleyferndescriptionwithstatistics();
    System.out.println(fern);*/

  }

  /**
   * Method that creates an appropriate stack pane canvas to display the fractal on.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   * @return A pair of the stack pane and the graphics context.
   */
  public Pair<StackPane, GraphicsContext> createGamePaneCanvas(int width, int height, ChaosGameObserver observer) {
    ChaosGameDescription prevDesc = fileController.loadLastGame();
    if (prevDesc != null) {
      chaosGame = new ChaosGame(prevDesc, width, height);
    } else {
      chaosGame = new ChaosGame(listOfDescriptions.get(0), width, height);

    }
    addObserverToGame(observer);
    Canvas canvas = new Canvas(width, height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    StackPane chaosGamePane = new StackPane();
    chaosGamePane.getChildren().add(canvas);
    saveCurrentGame(); // Save the initial state of the game
    return new Pair<>(chaosGamePane, gc);
  }

  /**
   * Method that initialises the game with a certain preset and adds an observer to the canvas.
   *
   * @param caseNum  The case number of the preset.
   * @param observer The observer that is added to the canvas.
   */
  public void choosePreset(int caseNum, ChaosGameObserver observer) {
    System.out.println("This is the description: " + listOfDescriptions.get(caseNum));

    updateChaosGame(new ChaosGame(listOfDescriptions.get(caseNum), 500, 500), observer);
    System.out.println("-----after updating chaos game in  choose pr4eset method");
    System.out.println(chaosGame.getDescription());
    System.out.println("Preset button was clicked for case " + caseNum);
    saveCurrentGame(); // Save the state of the game after choosing a preset
  }

  /**
   * Method to initialize the game with a default preset.
   */
  public void initializeDefaultGame() {
    chaosGame = new ChaosGame(listOfDescriptions.get(0), 500, 500);
    System.out.println("Default game initialized");
    saveCurrentGame(); // Save the initial default state of the game
  }

  /**
   * Method that updates the style of the buttons.
   * @param caseNum The case number of the preset.
   * @param buttons The list of buttons that are to be updated.
   */
  public void updateButtonStyle(int caseNum, List<javafx.scene.control.Button> buttons) {
    for (int i = 0; i < buttons.size(); i++) {
      if (i == caseNum) {
        buttons.get(i).setStyle("-fx-background-color: #ff0000");
      } else {
        buttons.get(i).setStyle("-fx-background-color: #ffffff");
      }
    }
  }

  /**
   * Method that updates the game with a new chaos game and adds an observer to the canvas.
   * @param inputGame The new chaos game that is to be updated.
   * @param observer The observer that is added to the canvas.
   */
  public void updateChaosGame(ChaosGame inputGame, ChaosGameObserver observer) {
    if (chaosGame != null) {
      chaosGame.getCanvas().clear();
    }
    chaosGame = inputGame;
    addObserverToGame(observer);
    System.out.println("Game was updated");
    saveCurrentGame(); // Save the state of the game after an update
  }

  /**
   * Method that clears the canvas.
   */
  public void clearCanvas() {
    if (chaosGame != null) {
      chaosGame.getCanvas().clear();
      saveCurrentGame(); // Save the state of the game after clearing the canvas
    }
  }

  /**
   * Method that runs the game for a certain number of steps.
   * @param steps The number of steps that the game is run for.
   */
  public void runGame(int steps) {
    if (chaosGame != null) {
      chaosGame.runSteps(steps);
      saveCurrentGame(); // Save the state of the game after running steps
    }
  }
  /**
   * Method that returns a value from the list of descriptions.
   */
  public ChaosGameDescription returnPresetDescription(int index){
    return listOfDescriptions.get(index);
  }

  /**
   * Create an empty fractal depending on the type of fractal.
   * @param isAffine True if the fractal is affine, false if the fractal is Julia.
   * @param numTransformations The number of transformations for the affine fractal.
   * @param fileName The name of the file that is to be created.
   */
  public void createEmptyFractal(boolean isAffine, int numTransformations, String fileName) {
    ChaosGameDescription description;
    if (isAffine) {
      double[][] matrices = new double[numTransformations][4];
      double[][] vectors = new double[numTransformations][2];
      description = ChaosGameDescriptionFactory.createAffineChaosGameDescriptionManual(numTransformations, matrices, vectors, 0, 0, 0, 0);
    } else {
      description = ChaosGameDescriptionFactory.createJuliaChaosGameDescriptionManual(1, new double[][]{{0, 0}}, new int[]{0}, 0, 0, 0, 0);
    }
    fileController.writeChaosGameDescriptionToFile(description, fileName);
    saveCurrentGame(); // Save the state of the game after creating an empty fractal
  }

  /**
   * Method that checks the type of the current chaos game.
   * @return True if the chaos game is affine, false if the chaos game is Julia.
   */
  public boolean isAffine() {
    return chaosGame != null && chaosGame.getDescription().getTransformType() == AffineTransform2D.class;
  }

  /**
   * Method that returns the current chaos game description.
   * @return The current chaos game description.
   */
  public ChaosGameDescription getCurrentChaosGameDescription() {
    if (chaosGame == null) {
      return null;
    }
    System.out.println("in getCurentchaosGamededsc method in game controller: \n" + chaosGame.getDescription());
    return chaosGame.getDescription();
  }

  /**
   * Update the chaos game description with a new chaos game description.
   */
  public void setCurrentChaosGameDescription(ChaosGameDescription description) {
    if (chaosGame != null) {
      chaosGame.setDescription(description);
      System.out.println(description.toString());
      saveCurrentGame(); // Save the state of the game after updating the description
    }
  }

  // Persistence methods
  public void saveCurrentGame() {
    ChaosGameDescription desc = getCurrentChaosGameDescription();
    if (desc != null) {
      fileController.saveLastGame(desc);
    }
  }

  public void loadLastGame() {
    ChaosGameDescription lastGame = fileController.loadLastGame();
    if (lastGame != null) {
      chaosGame = new ChaosGame(lastGame, 500, 500);
      System.out.println(lastGame);

    }
  }
}

