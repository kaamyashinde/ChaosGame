package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.view.ChaosGameScene;
import edu.ntnu.idatt2003.view.JuliaScene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Controller {
  private Stage stage;
  public Controller(Stage stage) {
    this.stage = stage;
  }

  public Button createPresetFractalButton(String fractalName){
    Button button = new Button(fractalName);
    button.setOnAction(e ->{
      switch (fractalName){
        case "Julia":
          // update chaos game to julia fractal
          break;
        case "Barnsley":
          // update chaos game to barnsley fractal
          break;
        case "Sierpinski":
          // update chaos game to sierpinski fractal
          break;
        default:
          break;
      }
    });
    return button;
  }

}
