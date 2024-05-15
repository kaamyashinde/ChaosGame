package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.ChaosGameObserver;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.Canvas;


import java.util.ArrayList;
import java.util.List;

/**
 * This class is the controller for the game.
 * It is responsible for handling all of the game logic.
 * This means that the model classes are used only in this controller to perform the game functions.
 * Thereafter, the methods are only called up on i the view package.
 * @see edu.ntnu.idatt2003.view.DisplayScene
 * @author Kaamya Shinde
 * @version 0.5
 * @since 3.3.0
 */
public class GameController {
  private static final String FILE_PATH = "src/main/resources/";

  List<ChaosGameDescription> listOfDescriptions;
ChaosGame chaosGame;
  /**
   * Constructor that initialises the array lists and adds the game presets.
   */

  public GameController() {
    listOfDescriptions = new ArrayList<>();
    loadChaosGamePresets();
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
    listOfDescriptions.add(readChaosGagmeDescriptionFromFile("Affine.txt"));
    listOfDescriptions.add(readChaosGagmeDescriptionFromFile("Barnsley.txt"));
    listOfDescriptions.add(readChaosGagmeDescriptionFromFile("Julia.txt"));
  }

  /**
   * Method that reads the chaos game description from a file.
   */
  public ChaosGameDescription readChaosGagmeDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH + fileName);
  }
  /**
   * Method that creates and appropriate stack pane canvas to display the fractal on.
   */
  public StackPane createGamePaneCanvas(GraphicsContext gc, int width, int height){
    chaosGame = new ChaosGame(listOfDescriptions.get(0), width, height);
    Canvas canvas = new Canvas(width, height);
    gc = canvas.getGraphicsContext2D();
    StackPane chaosGamePane = new StackPane();
    chaosGamePane.getChildren().add(canvas);
    return chaosGamePane;
  }
  /**
   * Method that adds an observer to the Chaos game object.
   * This was a suggestion by GitHub Copilot's AI.
   */
  public void addObserverToGame(ChaosGameObserver observer) {
    if (chaosGame != null) {
        chaosGame.getCanvas().addObserver(observer);
    }
}
}
