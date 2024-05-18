package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.FileController;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserFeedback {
  FileController fileController;
  public UserFeedback(){
    fileController = new FileController();
  }
  public void displayError(String message){
    System.out.println(message);
  }
  public void startMessage(){
    Stage popupStage = createPopupStage("Welcome back!");
    VBox popupLayout = createPopupLayout(popupStage);
    TextField textField = new TextField("Welcome back to the Chaos Game!");
    TextField textField1 = new TextField("You can start where u left off!");
    VBox container = new VBox();
    container.getChildren().addAll(textField, textField1);
    popupLayout.getChildren().add(container);
    showPopupStage(popupStage, popupLayout);
    popupStage.toFront();
  }

  /**
   * Method that initialises the pop-up stage.
   *
   * @param title The title of the pop-up stage.
   * @return popupStage The stage to be used.
   */

  private Stage createPopupStage(String title) {
    Stage popupStage = new Stage();
    popupStage.setTitle(title);
    return popupStage;
  }

  /**
   * Method that creates the layout of the pop-up window.
   *
   * @param popupStage The stage to be used.
   * @return popupLayout The layout of the pop-up window.
   */
  private VBox createPopupLayout(Stage popupStage) {
    VBox popupLayout = new VBox();
    popupLayout.prefWidthProperty().bind(popupStage.widthProperty());
    popupLayout.prefHeightProperty().bind(popupStage.heightProperty());
    return popupLayout;
  }
  /**
   * Method that shows the pop-up stage.
   *
   * @param popupStage  The stage to be used.
   * @param popupLayout The layout of the pop-up window.
   */

  private void showPopupStage(Stage popupStage, VBox popupLayout) {
    Scene popuScene = new Scene(popupLayout, 300, 300);
    popupStage.setScene(popuScene);
    popupStage.show();
  }
}
