package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.FileController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

public class UserFeedback {
  public UserFeedback(){
  }
  private static TextArea getStyledTextArea(String inputMessage){
    TextArea textArea = new TextArea(inputMessage);
    textArea.getStyleClass().add("content");
    textArea.setWrapText(true);
    return textArea;
  }
  public static void displayError(String message, String errorDesc){
    System.out.println(message);
    Stage popupStage = createPopupStage("Error");
    VBox popupLayout = createPopupLayout(popupStage);
    TextArea TextArea = getStyledTextArea(message);
    TextArea TextArea1 = getStyledTextArea(errorDesc);
    VBox container = new VBox();
    container.getChildren().addAll(TextArea, TextArea1);
    popupLayout.getChildren().add(container);
    showPopupStage(popupStage, popupLayout, 300, 140);
    popupStage.toFront();
  }
  public static void startMessage(){
    Stage popupStage = createPopupStage("Welcome back!");
    VBox popupLayout = createPopupLayout(popupStage);
    TextField headingOne = new TextField("Welcome back to the ");
    headingOne.getStyleClass().add("heading");
    headingOne.setAlignment(javafx.geometry.Pos.CENTER);
    TextField headingTwo = new TextField("Chaos Game!");
    headingTwo.getStyleClass().add("heading");
    headingTwo.setAlignment(javafx.geometry.Pos.CENTER);
    TextArea TextArea1 = getStyledTextArea("You can start where u left off!");
    VBox container = new VBox();
    container.getChildren().addAll(headingOne,headingTwo, TextArea1);
    popupLayout.getChildren().add(container);
    showPopupStage(popupStage, popupLayout, 500, 300);
    popupStage.toFront();
  }

  /**
   * Method that initialises the pop-up stage.
   *
   * @param title The title of the pop-up stage.
   * @return popupStage The stage to be used.
   */

  private static Stage createPopupStage(String title) {
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
  private static VBox createPopupLayout(Stage popupStage) {
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

  private static void showPopupStage(Stage popupStage, VBox popupLayout, int width, int height) {
    Scene popuScene = new Scene(popupLayout, width, height);
    String css = UserFeedback.class.getResource("/userFeedback.css").toExternalForm();
    popuScene.getStylesheets().add(css);
    popupStage.setScene(popuScene);
    popupStage.show();
  }
}
