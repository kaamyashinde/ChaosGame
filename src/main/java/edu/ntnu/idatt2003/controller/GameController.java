package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.factory.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.observer.ChaosGameObserver;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.view.UserFeedback;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class is the controller for the game.
 * It is responsible for handling all of the game logic.
 * This means that the model classes are used only in this controller to perform the game functions.
 * Thereafter, the methods are only called up on in the view package.
 *
 * @version 0.8
 * @see edu.ntnu.idatt2003.view.DisplayScene
 * @since 0.3.5
 */
public class GameController {
//TODO fix model validators catching the exception
  /**
   * The instance of the GameController class that is used to implement the Singleton pattern.
   */
  private static final GameController instance = new GameController();
  private final FileController fileController;
  private final List<ChaosGameDescription> listOfDescriptions;
  private Stage primaryStage;
  private ChaosGame chaosGame;
  private boolean persistenceIsNull;

  /**
   * Constructor that initialises the necessary fields and loads the chaos game presets.
   */
  private GameController() {
    fileController = new FileController();
    listOfDescriptions = new ArrayList<>();
    loadChaosGamePresets();
  }

  /**
   * A static method that always returns the same object of the GameController class. This helps implement Singleton pattern.
   */
  public static synchronized GameController getInstance() {
    return instance;
  }




  /**
   * Method that returns the default preset description that is used to initialise the game.
   */
  private ChaosGameDescription getDefaultPresetDescription() {
    return listOfDescriptions.get(0);
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
   * Method that handles the initialisation of chaos game at application start based on the status of the persistence file.
   * It sets the value for the persistenceIsNull field and initialises the game accordingly.
   *
   * @param observer The observer that is added to the canvas.
   * @param width    The width of the canvas.
   * @param height   The height of the canvas.
   */
  private void initialiseGame(ChaosGameObserver observer, int width, int height) {
    ChaosGameDescription prevDesc = fileController.loadLastGame();
    if (prevDesc == null) {
      chaosGame = new ChaosGame(getDefaultPresetDescription(), width, height);
      persistenceIsNull = true;
    } else {
      chaosGame = new ChaosGame(prevDesc, width, height);
      persistenceIsNull = false;
    }
    addObserverToGame(observer);
  }

  /**
   * Method that uses the ChaosGameDescriptionFactory to create the default presets and write them to a file.
   * This is then used to load the presets and add them to the list of descriptions.
   */
  private void loadChaosGamePresets() {
    fileController.writeChaosGameDescriptionToPresets(ChaosGameDescriptionFactory.defaultJuliaSet(), "Julia");
    fileController.writeChaosGameDescriptionToPresets(ChaosGameDescriptionFactory.defaultBarnsleyFern(), "Barnsley");
    fileController.writeChaosGameDescriptionToPresets(ChaosGameDescriptionFactory.defaultSierpinskiTriangle(), "Triangle");
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromPresets("Julia"));
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromPresets("Barnsley"));
    listOfDescriptions.add(fileController.readChaosGameDescriptionFromPresets("Triangle"));
  }


  /**
   * A getter to return whether or not the persistence is null
   */
  public boolean getPersistenceIsNull() {
    return persistenceIsNull;
  }

  /**
   * Method that checks the type of the current chaos game.
   *
   * @return True if the chaos game is affine, false if the chaos game is Julia.
   */
  public boolean isAffine() {
    return chaosGame != null && chaosGame.getDescription().getTransformType() == AffineTransform2D.class;
  }

  /**
   * Method that returns the chaos game object.
   *
   * @return The chaos game object.
   */
  public ChaosGame returnChaosGame() {
    return chaosGame;
  }

  /**
   * Method that returns the current chaos game description.
   *
   * @return The current chaos game description.
   */
  public ChaosGameDescription getCurrentChaosGameDescription() {
    if (chaosGame == null) {
      return null;
    }
    return chaosGame.getDescription();
  }

  /**
   * Update the chaos game description with a new chaos game description.
   */
  public void setCurrentChaosGameDescription(ChaosGameDescription description) {
    if (chaosGame != null) {
      chaosGame.setDescription(description);
      saveCurrentGame();
    }
  }

  /**
   * Method that returns a value from the list of descriptions.
   */
  public ChaosGameDescription returnPresetDescription(int index) {
    return listOfDescriptions.get(index);
  }

  /**
   * Method that creates an appropriate stack pane canvas to display the fractal on. It also saves the the current game to the file.
   *
   * @param width    The width of the canvas.
   * @param height   The height of the canvas.
   * @param observer The observer that is added to the canvas.
   * @return A pair of the stack pane and the graphics context.
   */
  public Pair<StackPane, GraphicsContext> createGamePaneCanvas(int width, int height, ChaosGameObserver observer) {
    initialiseGame(observer, width, height);
    Canvas canvas = new Canvas(width, height);
    GraphicsContext gc = canvas.getGraphicsContext2D();
    StackPane chaosGamePane = new StackPane();
    chaosGamePane.getChildren().add(canvas);
    saveCurrentGame();
    return new Pair<>(chaosGamePane, gc);
  }

  /**
   * Method that returns the primary Stage.
   */
  public Stage getPrimaryStage() {
    return primaryStage;
  }

  /**
   * Method that sets the primary Stage.
   *
   * @param inputStage The primary stage that is to be set.
   */
  public void setPrimaryStage(Stage inputStage) {
    this.primaryStage = inputStage;
  }

  /**
   * Method that clears the canvas.
   */
  public void clearCanvas() {
    if (chaosGame != null) {
      chaosGame.getCanvas().clear();
      saveCurrentGame();
    }
  }

  /**
   * Create an empty fractal depending on the type of fractal using {@link ChaosGameDescriptionFactory}.
   *
   * @param isAffine           True if the fractal is affine, false if the fractal is Julia.
   * @param numTransformations The number of transformations for the affine fractal.
   * @param fileName           The name of the file that is to be created.
   */
  public void createEmptyFractal(boolean isAffine, int numTransformations, String fileName) {
    ChaosGameDescription description = isAffine ? ChaosGameDescriptionFactory.emptyAffineSet(numTransformations) : ChaosGameDescriptionFactory.emptyJuliaSet();
    fileController.writeChaosGameDescriptionToAppFiles(description, fileName);
    saveCurrentGame();
  }

  /**
   * Method to initialize the game with a default preset.
   *
   * @param observer The observer that is added to the canvas.
   */
  public void initializeDefaultGame(ChaosGameObserver observer) {
    chaosGame = new ChaosGame(getDefaultPresetDescription(), 500, 500);
    addObserverToGame(observer);
    saveCurrentGame();
  }

  /**
   * Method that loads the last game from the persistence file. This is then used to initialise the game.
   *
   * @param observer The observer that is added to the canvas.
   */
  public void loadLastGame(ChaosGameObserver observer) {
    ChaosGameDescription lastGame = fileController.loadLastGame();
    if (lastGame != null) {
      chaosGame = new ChaosGame(lastGame, 500, 500);
      addObserverToGame(observer);
      System.out.println(lastGame);
    }
  }

  /**
   * Method that runs the game for a certain number of steps. If an error is caught from the {@link edu.ntnu.idatt2003.model.utils.ModelValidators}, an error message is displayed using {@link UserFeedback}.
   *
   * @param steps The number of steps that the game is run for.
   */
  public void runGame(int steps) {
    try {
      if (chaosGame != null) {
        chaosGame.runSteps(steps);
        saveCurrentGame();
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      UserFeedback.displayError("There was an error running the game. The given configuration cannot be run.", " Please try again.");
    }
  }

  /**
   * Method that saves the current game to the persistence file.
   */
  public void saveCurrentGame() {
    ChaosGameDescription desc = getCurrentChaosGameDescription();
    if (desc != null) {
      fileController.saveLastGame(desc);
    }
  }

  /**
   * Method that updates the style of the buttons based on the case number.
   *
   * @param caseNum The case number of the preset.
   * @param buttons The list of buttons that are to be updated.
   */
  public void updateButtonStyle(int caseNum, List<Button> buttons) {
    String className = "button-selected";
    IntStream.range(0, buttons.size()).forEach(i -> {
      List<String> styleClass = buttons.get(i).getStyleClass();
      styleClass.remove(className);
      if (i == caseNum) {
        styleClass.add(className);
      }
    });
  }

  /**
   * Method that updates the game with a new chaos game and adds an observer to the canvas.
   *
   * @param inputGame The new chaos game that is to be updated.
   * @param observer  The observer that is added to the canvas.
   */
  public void updateChaosGame(ChaosGame inputGame, ChaosGameObserver observer) {
    if (chaosGame != null) {
      chaosGame.getCanvas().clear();
    }
    chaosGame = inputGame;
    addObserverToGame(observer);
    saveCurrentGame();
  }

}

