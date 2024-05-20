package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private GameController gameController = GameController.getInstance();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
      DisplayScene displayScene = new DisplayScene();
      Scene scene = displayScene.getDisplay(primaryStage);
      String css = getClass().getClassLoader().getResource("styles.css").toExternalForm();
      scene.getStylesheets().add(css);

      gameController.loadLastGame(displayScene);

      primaryStage.setScene(scene);
      primaryStage.setScene(scene);
        primaryStage.setTitle("Chaos Game");
        primaryStage.toBack();
        primaryStage.show();

      if (!gameController.getPersistenceIsNull()){
        UserFeedback userFeedback = new UserFeedback();
       userFeedback.startMessage();
      }
    }

    @Override
    public void stop() {
        System.out.println("Saving current game state before exit");
       // gameController.saveCurrentGame();
    }
}
