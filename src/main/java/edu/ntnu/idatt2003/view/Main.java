package edu.ntnu.idatt2003.view;

import javafx.application.Application;
import javafx.scene.Scene;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception {
      DisplayScene displayScene = new DisplayScene();
      Scene scene = displayScene.getDisplay(primaryStage);
      primaryStage.setScene(scene);
        primaryStage.setTitle("Chaos Game");
        primaryStage.show();
    }
}
