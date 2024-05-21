package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.controller.KeyActionPolicyController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * The main class that starts the application.
 * It is responsible for starting the application and loading the last game state.
 * It also displays a message if the game is loaded from a previous state.
 *
 * @version 0.4
 * @since 0.3.5
 */
public class Main extends Application {

  DisplayScene displayScene;
  private final GameController gameController = GameController.getInstance();

  /**
   * Method that sets the details of the scene.
   *
   * @param primaryStage The stage to set the scene on.
   * @param scene        The scene to set the details on.
   */
  private static void setSceneDetails(Stage primaryStage, Scene scene) {
    primaryStage.setScene(scene);
    primaryStage.setScene(scene);
    primaryStage.setTitle("Chaos Game");
    primaryStage.toBack();
    primaryStage.show();
    primaryStage.setMinWidth(990);
    primaryStage.setMinHeight(730);
  }

  @Override
  public void start(javafx.stage.Stage primaryStage) {
    displayScene = new DisplayScene();
    Scene scene = displayScene.getDisplay(primaryStage);
    KeyActionPolicyController.applyEnterKeyActionPolicy(primaryStage);
    String mainStyles = Objects.requireNonNull(getClass().getClassLoader().getResource("stylesheets/mainStyles.css")).toExternalForm();
    String buttonStyles = Objects.requireNonNull(getClass().getClassLoader().getResource("stylesheets/buttonStyles.css")).toExternalForm();
    scene.getStylesheets().addAll(mainStyles, buttonStyles);
    gameController.loadLastGame(displayScene);
    setSceneDetails(primaryStage, scene);

    displayStartUpMessage(primaryStage);

  }

  /**
   * Method that removes the observer from the canvas when the game is stopped.
   */
  @Override
  public void stop() {
    gameController.returnChaosGame().getCanvas().removeObserver(displayScene);
  }

  /**
   * Method that displays a welcome message when the game is started.
   *
   * @param primaryStage The stage to display the message on.
   */
  private void displayStartUpMessage(Stage primaryStage) {
    if (!gameController.getPersistenceIsNull()) {
      UserFeedback.welcomeBackMessage(primaryStage, displayScene);
    } else {
      UserFeedback.welcomeMessage(primaryStage);
    }
  }

}
