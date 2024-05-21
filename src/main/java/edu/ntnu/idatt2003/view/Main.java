package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.controller.KeyActionPolicyController;
import javafx.application.Application;
import javafx.scene.Scene;

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
  private GameController gameController = GameController.getInstance();

  public static void main(String[] args) {
    launch(args);
  }


  @Override
  public void start(javafx.stage.Stage primaryStage) throws Exception {
    try {
      displayScene = new DisplayScene();
      Scene scene = displayScene.getDisplay(primaryStage);
      KeyActionPolicyController.applyEnterKeyActionPolicy(primaryStage);
      String css = getClass().getClassLoader().getResource("stylesheets/styles.css").toExternalForm();
      scene.getStylesheets().add(css);

      gameController.loadLastGame(displayScene);

      primaryStage.setScene(scene);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Chaos Game");
      primaryStage.toBack();
      primaryStage.show();
      primaryStage.setMinWidth(990);
      primaryStage.setMaxWidth(1200);
      primaryStage.setMinHeight(730);
      primaryStage.setMaxHeight(1200);


      if (!gameController.getPersistenceIsNull()) {
        UserFeedback.welcomeBackMessage(primaryStage, displayScene);
      } else {
        UserFeedback.welcomeMessage(primaryStage, displayScene);
      }
    } catch (Exception e) {
      System.out.println("cautch exception of type" + e.getClass().getName());
      e.printStackTrace();

    }
  }

  @Override
  public void stop() {
    System.out.println("Saving current game state before exit");
    // gameController.saveCurrentGame();
    gameController.returnChaosGame().getCanvas().removeObserver(displayScene);

  }
}
