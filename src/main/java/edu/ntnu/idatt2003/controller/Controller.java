package edu.ntnu.idatt2003.controller;

import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
  private static final String FILE_PATH = "src/main/resources/";
  ArrayList<ChaosGameDescription> chaosGameDescriptions;
  ChaosGameDescription currentDescription;
   Stage stage;
   ChaosGame chaosGame;

  public Controller(Stage stage) {
    this.stage = stage;
    chaosGameDescriptions = new ArrayList<>();
    loadChaosGamePresets();
  }
  /**
   * Method that loads all the presets for the different fractals.
   */
  private void loadChaosGamePresets() {
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Julia.txt"));
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Barnsley.txt"));
    chaosGameDescriptions.add(readChaosGameDescriptionFromFile("Affine.txt"));
  }
  /**
   * Method that sets the game.
   */
  public ChaosGame setUpGame(int descriptionIndex){
    return new ChaosGame(chaosGameDescriptions.get(descriptionIndex), 800, 800);
  }

  /**
   * Method that displays the current chaosGameDescription's maximum and minimum coordinates.
   */
  public void displayMaxAndMinCoords(ChaosGameDescription inputDescription, List<TextField> inputTextFields) {
    inputTextFields.get(0).setText(String.valueOf(inputDescription.getMinCoords().getX0()));
    inputTextFields.get(1).setText(String.valueOf(inputDescription.getMinCoords().getX1()));
    inputTextFields.get(2).setText(String.valueOf(inputDescription.getMaxCoords().getX0()));
    inputTextFields.get(3).setText(String.valueOf(inputDescription.getMaxCoords().getX1()));
  }
  /**
   * Method that displays the current chaosGameDescriptions's Affine transformations.
   */
  public void displayAffineTransformations(int index, ChaosGameDescription inputDesc, List<TextField> inputTextFields){
    AffineTransform2D affine = (AffineTransform2D) inputDesc.getTransforms().get(index);
    inputTextFields.get(0).setText(String.valueOf(affine.getMatrix().getA00()));
    inputTextFields.get(1).setText(String.valueOf(affine.getMatrix().getA01()));
    inputTextFields.get(2).setText(String.valueOf(affine.getMatrix().getA10()));
    inputTextFields.get(3).setText(String.valueOf(affine.getMatrix().getA11()));
    inputTextFields.get(4).setText(String.valueOf(affine.getVector().getX0()));
    inputTextFields.get(5).setText(String.valueOf(affine.getVector().getX1()));
  }




  /**
   * Method that reads the chaosGameDescription object from a file.
   */
  public ChaosGameDescription readChaosGameDescriptionFromFile(String fileName) {
    return ChaosGameFileHandler.readFromFile(FILE_PATH + fileName);
  }



}
