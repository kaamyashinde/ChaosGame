package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.view.ChaosGameScene;
import edu.ntnu.idatt2003.view.JuliaScene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {
  private Stage stage;
  public Controller(Stage stage) {
    this.stage = stage;
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Julia.txt"));
  }
  ArrayList<ChaosGameDescription> chaosGameDescriptions = new ArrayList<>();
  ChaosGameDescription chaosGameDescription;
  private static final String FILE_PATH = "src/main/resources/";


  /**
   * Method that creates the buttons for the different fractals and returns them.
   * @param fractalName The name of the fractal.
   * @return The button for the fractal.
   */

  public Button createPresetFractalButton(String fractalName){
    Button button = new Button(fractalName);
    button.setOnAction(e ->{
      switch (fractalName){
        case "Julia":
          chaosGameDescription = readChaosGameDescriptionFromFile("Julia.txt");
          chaosGameDescriptions.add(chaosGameDescription);
          break;
        case "Barnsley":
          chaosGameDescription = readChaosGameDescriptionFromFile("Barnsley.txt");
          chaosGameDescriptions.add(chaosGameDescription);
          break;
        case "Sierpinski":
          chaosGameDescription = readChaosGameDescriptionFromFile("Affine.txt");
          chaosGameDescriptions.add(chaosGameDescription);
          break;
        default:
          break;
      }
    });
    return button;
  }

  public ChaosGame returnChaosGame(){
    ChaosGameDescription desc = getChaosGameDescription();
    return new ChaosGame(desc, 1000, 1000);
  }

  /**
   * Method that reads the chaosGameDescription object from a file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH + fileName);
  }
  public ChaosGameDescription getChaosGameDescription() {
    return chaosGameDescriptions.get(chaosGameDescriptions.size() - 1);
  }

}
