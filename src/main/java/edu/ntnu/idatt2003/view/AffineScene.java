package edu.ntnu.idatt2003.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class AffineScene extends Application {
  Button switchToJulia;
  Button getHelp;
  TextField sceneTitle;
  StackPane chaosGamePane;
  AnchorPane layout = new AnchorPane();
  public static void main(String[] args) {
   launch(args);
  }
  @Override
  public void start(Stage primaryStage) {
    setSwitchToJuliaButton();
    setSceneTitle();
    setGetHelpButton();
    setScene(primaryStage);
  }

  /**
   * Method that sets the switch to Julia button.
   * This involves creating the button, along with the positioning and padding.
   */
  private void setSwitchToJuliaButton(){
    switchToJulia = new Button("Switch to Julia");
    switchToJulia.setOnAction(e -> System.out.println("Switching to Julia..."));
    layout.getChildren().add(switchToJulia);
    AnchorPane.setTopAnchor(switchToJulia, 10.0);
    AnchorPane.setLeftAnchor(switchToJulia, 10.0);
  }
  /**
   * Method that sets the get help button.
   * This involves creating the button, along with the positioning and padding.
   */
  private void setGetHelpButton(){
    getHelp = new Button("Get Help");
    getHelp.setOnAction(e -> System.out.println("Getting help..."));
    layout.getChildren().add(getHelp);
    AnchorPane.setTopAnchor(getHelp, 10.0);
    AnchorPane.setRightAnchor(getHelp, 10.0);
  }

  /**
   * Method that sets the scene title.
   * This involves creating the text field, along with the positioning and padding.
   */
  private void setSceneTitle(){
    sceneTitle = new TextField("Affine Transformations");
    sceneTitle.setEditable(false);
    sceneTitle.setAlignment(Pos.CENTER);
    layout.getChildren().add(sceneTitle);
    AnchorPane.setTopAnchor(sceneTitle, 50.0);
    AnchorPane.setLeftAnchor(sceneTitle, 10.0);
    AnchorPane.setRightAnchor(sceneTitle, 10.0);
  }

  /**
   * Method to set the scene of the stage.
   * @param primaryStage the stage to set the scene for
   */
  private void setScene(Stage primaryStage){
    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
