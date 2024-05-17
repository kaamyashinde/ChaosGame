package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.GameController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private GameController gameController = new GameController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameController.loadLastGame();
        if (gameController.getCurrentChaosGameDescription() == null) {
            gameController.choosePreset(0, null); // Initialiserer med et standard preset hvis ingen lagret tilstand finnes
        }
        DisplayScene displayScene = new DisplayScene();
        Scene scene = displayScene.getDisplay(primaryStage);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Chaos Game");
        primaryStage.show();
    }

    @Override
    public void stop() {
        System.out.println("Saving current game state before exit");
        gameController.saveCurrentGame();
    }
}
