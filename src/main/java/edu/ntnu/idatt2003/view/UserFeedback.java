package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.FileController;
import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import static edu.ntnu.idatt2003.view.PopupScene.*;

/**
 * This class is responsible for creating the pop-up windows for the user feedback.
 * The class contains methods that display error messages and welcome messages.
 * @author Kaamya Shinde
 * @version 0.3
 * @since 0.3.2
 */
public class UserFeedback {
  private static final GameController gameController = GameController.getInstance();
  /**
   * Method that styles the text area by adding the content style to it.
   * @param inputMessage The message that is to be displayed.
   * @return textArea The text area that is to be displayed.
   */

  private static TextArea getStyledTextArea(String inputMessage){
    TextArea textArea = new TextArea(inputMessage);
    textArea.getStyleClass().add("content");
    textArea.setWrapText(true);
    return textArea;
  }

  /**
   * Method that displays an error message to the user.
   * @param message The message that is to be displayed.
   * @param errorDesc The description of the error along with description on how to avoid it.
   */
  public static void displayError(String message, String errorDesc){
    System.out.println(message);
    Stage popupStage = createPopupStage("Error", null);
    VBox popupLayout = createPopupLayout(popupStage);
    TextArea TextArea = getStyledTextArea(message);
    TextArea TextArea1 = getStyledTextArea(errorDesc);
    VBox container = new VBox();
    container.getChildren().addAll(TextArea, TextArea1);
    popupLayout.getChildren().add(container);
    dimBackground(gameController.getPrimaryStage(), popupStage);

    showPopupStage(popupStage, popupLayout, 400, 200);
    popupStage.toFront();

  }

  /**
   * Method that displays a welcome message to the user.
   * This one is displayed when the user restarts the game.
   * The user is provided with the option to start where they left off.
   */
  public static void startMessage(Stage primaryStage, ChaosGameObserver observer){


    Stage popupStage = createPopupStage("Welcome back!", primaryStage);
    VBox popupLayout = createPopupLayout(popupStage);
    TextField headingOne = new TextField("Welcome back to the ");
    headingOne.getStyleClass().add("heading");
    headingOne.setAlignment(javafx.geometry.Pos.CENTER);
    TextField headingTwo = new TextField("Chaos Game!");
    headingTwo.getStyleClass().add("heading");
    headingTwo.setAlignment(javafx.geometry.Pos.CENTER);
    TextArea TextArea1 = getStyledTextArea("You can start where u left off or start a new game.");
    Button startNewGame = new Button("Start a new game");
    startNewGame.setAlignment(javafx.geometry.Pos.CENTER);
    startNewGame.setOnAction(e -> {
      FileController.clearFileContent();
      GameController gameController = GameController.getInstance();
      gameController.initializeDefaultGame(observer);
      popupStage.close();

      if (observer instanceof DisplayScene displayScene) {
        displayScene.getButtons().get(0).getStyleClass().add("button-selected");
      }
    });
    Button continueGame = new Button("Continue game");
    continueGame.setAlignment(javafx.geometry.Pos.CENTER);
    continueGame.setOnAction(e -> {
      popupStage.close();
      informUserAboutContinuingGame();

    });
    HBox buttons = new HBox();
    buttons.setAlignment(javafx.geometry.Pos.CENTER);
    buttons.getChildren().addAll(startNewGame, continueGame);
    VBox container = new VBox();
    container.getChildren().addAll(headingOne,headingTwo, TextArea1,buttons);
    popupLayout.getChildren().add(container);
    dimBackground(gameController.getPrimaryStage(), popupStage);


    showPopupStage(popupStage, popupLayout, 500, 300);
    popupStage.toFront();
  }


  private static void informUserAboutContinuingGame(){
    Stage popupStage = createPopupStage("Continuing the game", gameController.getPrimaryStage());
    VBox popupLayout = createPopupLayout(popupStage);

    VBox container = new VBox();
    TextArea instructions = getStyledTextArea("You can resume the game by clicking on the run iterations button.");
    container.getChildren().addAll(instructions);

    Button okayButton = new Button("Okay");
    container.setAlignment(javafx.geometry.Pos.CENTER);
    okayButton.setOnAction(e -> {
      popupStage.close();
    });

    PopupScene.dimBackground(gameController.getPrimaryStage(), popupStage);
    container.getChildren().add(okayButton);
    popupLayout.getChildren().add(container);
    showPopupStage(popupStage, popupLayout, 400, 180);
    popupStage.toFront();


  }



}
