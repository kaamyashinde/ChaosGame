package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.GameController;
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

    private GameController gameController = GameController.getInstance();

  DisplayScene displayScene;
  public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
     try  {
        displayScene = new DisplayScene();
        Scene scene = displayScene.getDisplay(primaryStage);
        String css = getClass().getClassLoader().getResource("stylesheets/styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        gameController.loadLastGame(displayScene);

        primaryStage.setScene(scene);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chaos Game");
        primaryStage.toBack();
        primaryStage.show();

        if (!gameController.getPersistenceIsNull()) {
          UserFeedback userFeedback = new UserFeedback();
          userFeedback.startMessage(primaryStage, displayScene);
        }
      } catch (Exception e) {
       System.out.println("cautch exception of type"  + e.getClass().getName());
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
