package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.view.AffineScene;
import edu.ntnu.idatt2003.view.JuliaScene;
import javafx.stage.Stage;

public class Controller {
  private Stage stage;
  public Controller(Stage stage) {
    this.stage = stage;
  }
  public void switchToAffine(){
    AffineScene affineScene = new AffineScene();
    try {
      affineScene.start(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  public void switchToJulia(){
    JuliaScene juliaScene = new JuliaScene();
    try {
      juliaScene.start(stage);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
