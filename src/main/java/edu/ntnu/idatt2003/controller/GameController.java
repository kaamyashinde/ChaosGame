package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
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
 * Thereafter, the methods are only called up on i the view package.
 *
 * @author Kaamya Shinde
 * @version 0.4
 * @see edu.ntnu.idatt2003.view.DisplayScene
 * @since 0.3.3
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
   * This was a suggestion by GitHub Copilot's AI.
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
   * The following is the index of the presets in the list:
   * <ol>
   *   <l>Sierpinski Triangle: The default config of an affine transformation.</l>
   *   <li>Barnsley Fern: An affine transformation</li>
   *   <li>Julia Transformation</li>
   * </ol>
   */
  private void loadChaosGamePresets() {
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromFile("presets/Affine.txt"));
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromFile("presets/Barnsley.txt"));
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromFile("presets/Julia.txt"));
  }

  /**
   * Method that creates and appropriate stack pane canvas to display the fractal on.
   * The suggestion to use a Pair to store both the stack pane and the graphics context was given by GitHub Copilot's AI.
   * This is so that the graphics context is not null when the observerActionController tries performing actions on the graphics context in DisplayScene.
   *
   * @param width  The width of the canvas.
   * @param height The height of the canvas.
   * @return A pair of the stack pane and the graphics context.
   */
  public Pair<StackPane, GraphicsContext> createGamePaneCanvas(int width, int height) {
    chaosGame = new ChaosGame(listOfDescriptions.get(0), width, height);
    Canvas canvas = new Canvas(width, height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    StackPane chaosGamePane = new StackPane();
    chaosGamePane.getChildren().add(canvas);
    return new Pair<>(chaosGamePane, gc);
  }

  /**
   * Method that initialises the game with a certain preset and adds an observer to the canvas.
   * The observer is added to the canvas so that the observer can be notified when the game is run.
   * These are the cases for the presets:
   * <ol>
   *   <li>Sierpinski Triangle</li>
   *   <li>Barnsley Fern</li>
   *   <li>Julia Transformation</li>
   * </ol>
   *
   * @param caseNum  The case number of the preset.
   * @param observer The observer that is added to the canvas.
   */
  public void choosePreset(int caseNum, ChaosGameObserver observer) {
    updateChaosGame(new ChaosGame(listOfDescriptions.get(caseNum), 500, 500), observer);
    System.out.println("Preset button was clicked for case " + caseNum);
  }

  /**
   * Method that updates the game with a new chaos game and adds an observer to the canvas.
   * @param inputGame The new chaos game that is to be updated.
   * @param observer The observer that is added to the canvas.
   */

  public void updateChaosGame(ChaosGame inputGame, ChaosGameObserver observer) {
    chaosGame.getCanvas().clear();
    chaosGame = inputGame;
    addObserverToGame(observer);
    System.out.println("Game was updated");
  }


  /**
   * Method that clears the canvas.
   */
  public void clearCanvas() {
    chaosGame.getCanvas().clear();
  }

  /**
   * Method that runs the game for a certain number of steps.
   * @param steps The number of steps that the game is run for.
   */
  public void runGame(int steps) {
    chaosGame.runSteps(steps);
  }

  /**
   * Create an empty fractal depending on the type of fractal.
   * The empty fractal is just file with 0s for all values.
   *
   * @param isAffine           True if the fractal is affine, false if the fractal is Julia.
   * @param numTransformations The number of transformations for the affine fractal.
   * @param fileName           The name of the file that is to be created.
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
  }
}
