package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.FileController;
import edu.ntnu.idatt2003.controller.GameController;
import edu.ntnu.idatt2003.model.ChaosGameObserver;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static edu.ntnu.idatt2003.view.PopupScene.*;

/**
 * This class is responsible for creating the pop-up windows for the user feedback.
 * The class contains methods that display error messages and welcome messages.
 *
 * @author Kaamya Shinde
 * @version 0.4
 * @since 0.3.2
 */
public class UserFeedback {
  private static final GameController gameController = GameController.getInstance();

  /**
   * Method that styles the text area by adding the content style to it.
   *
   * @param inputMessage The message that is to be displayed.
   * @return textArea The text area that is to be displayed.
   */

  private static TextArea getStyledTextArea(String inputMessage) {
    TextArea textArea = new TextArea(inputMessage);
    textArea.getStyleClass().add("content");
    textArea.setEditable(false);
    textArea.setWrapText(true);
    return textArea;
  }

  /**
   * Method that displays an error message to the user.
   *
   * @param message   The message that is to be displayed.
   * @param errorDesc The description of the error along with description on how to avoid it.
   */
  public static void displayError(String message, String errorDesc) {
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
   * Method that displays a welcome back message to the user.
   * This one is displayed when the user restarts the game.
   * The user is provided with the option to start where they left off.
   */
  public static void welcomeBackMessage(Stage primaryStage, ChaosGameObserver observer) {


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
    container.getChildren().addAll(headingOne, headingTwo, TextArea1, buttons);
    popupLayout.getChildren().add(container);
    dimBackground(gameController.getPrimaryStage(), popupStage);


    showPopupStage(popupStage, popupLayout, 500, 300);
    popupStage.toFront();
  }

  /**
   * Method that displays a welcome message to the user.
   * This one is displayed when the user starts the game for the first time.
   * The user is provided with the option to start a new game.
   * The user is also informed about how the chaos game works, and how to play it.
   */
  public static void welcomeMessage(Stage primaryStage, ChaosGameObserver observer) {
    Stage popupStage = createPopupStage("Welcome to the Chaos Game!", primaryStage);
    VBox popupLayout = createPopupLayout(popupStage);
    TextField headingOne = new TextField("Welcome to the ");
    headingOne.setFocusTraversable(false);
    headingOne.getStyleClass().add("heading");
    headingOne.setAlignment(javafx.geometry.Pos.CENTER);
    TextField headingTwo = new TextField("Chaos Game!");
    headingTwo.setFocusTraversable(false);
    headingTwo.getStyleClass().add("heading");
    headingTwo.setAlignment(javafx.geometry.Pos.CENTER);
    TextArea TextArea1 = getStyledTextArea("The Chaos Game is a mathematical game that creates fractals using a set of rules. The game is played by selecting a point on the canvas and then clicking on the run iterations button. The game will then run the iterations and create a fractal. ");
    Button startNewGame = new Button("Start a new game");
    startNewGame.setAlignment(javafx.geometry.Pos.CENTER);
    startNewGame.setOnAction(e -> {
      popupStage.close();
    });
    Button whatCanIDo = new Button("What can I do?");
    whatCanIDo.setAlignment(javafx.geometry.Pos.CENTER);
    whatCanIDo.setOnAction(e->{
      popupStage.close();
      displayUserManual();
    });
    HBox buttons = new HBox();
    buttons.setAlignment(javafx.geometry.Pos.CENTER);
    buttons.getChildren().addAll(startNewGame, whatCanIDo);
    VBox container = new VBox();
    container.getChildren().addAll(headingOne, headingTwo, TextArea1, buttons);
    popupLayout.getChildren().add(container);
    dimBackground(gameController.getPrimaryStage(), popupStage);

    showPopupStage(popupStage, popupLayout, 500, 400);
    popupStage.toFront();
  }

  /**
   * Method that informs the user about continuing the game.
   * This method is called when the user chooses to continue the game.
   * The user is informed about how to continue the game.
   */
  private static void informUserAboutContinuingGame() {
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

  /**
   * Provide the user with an user manual when the user clicks on the "What can I do?" button.
   * This includes of the following options:
   * <ol>
   *   <li> Create a new empty file of either Julia or Affine type. The default file creation is set to julia. Clicking on the "Switch to Affine" button gives the user an option to enter the number of transformations.</li>
   *   <li>Read description from a file by selecting one from the drop down list. The user has to click on the "update chaos game" button in order to register the new configuration.</li>
   * <li>Choose a preset fractal. The  user can choose between three different fractals that have been loaded at application start. This is run by adjusting the number of iterations the user wants and running them by clicking on the "run iterations" button.</li>
   * <li>Edit the value of the configuration. There are two ways to do this :
   *      <ol>
   *        <li>Edit Current Game Description. The user updates the value of the configuration that is currently being used.</li>
   *        <li>Edit Selected Description. The user can select a file to read from the drop down list and then edit the selected file. The user does not need to click on the "update chaos game" button in order for the "edit selected description" button to work. If the user chooses to update the chaos game after reading from the file, they can also use the "edit current description" button to update the configuration.</li>
   *      </ol>
   *      After doing so, the user has to enter the name of the file they want to save the description to. It is possible to create a new file, by writing a new name in the input, or the user can replace the existing file by writing the name of the file.
   *      In order to save the file, the user has to click on the "save description" button. Otherwise, all of the changes will be lost.
   * </li>
   * <li>The run steps iterations runs the game for the given number of steps. It is possible to clear out the canvas by clicking on the "clear canvas" button.</li>
   * </ol>
   */
  public static void displayUserManual() {
    Stage popupStage = createPopupStage("User Manual", gameController.getPrimaryStage());
    popupStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.ENTER) {
        Node focusOwner = popupStage.getScene().getFocusOwner();
        if (focusOwner instanceof Button) {
          ((Button) focusOwner).fire();
          event.consume();
        }
      }
    });
    VBox popupLayout = createPopupLayout(popupStage);

    VBox container = new VBox();

    TextField heading1 = new TextField("1. Create a new empty file");
    heading1.setEditable(false);
    heading1.getStyleClass().add("user-manual-title");
    TextArea instructions1 = getStyledTextArea("Create a new empty file of either Julia or Affine type. The default file creation is set to julia. Clicking on the \"Switch to Affine\" button gives the user an option to enter the number of transformations.");

    TextField heading2 = new TextField("2. Read description from a file");
    heading2.setEditable(false);
    heading2.getStyleClass().add("user-manual-title");
    TextArea instructions2 = getStyledTextArea("Read description from a file by selecting one from the drop down list. The user has to click on the \"update chaos game\" button in order to register the new configuration.");

    TextField heading3 = new TextField("3. Choose a preset fractal");
    heading3.setEditable(false);
    heading3.getStyleClass().add("user-manual-title");
    TextArea instructions3 = getStyledTextArea("The user can choose between three different fractals that have been loaded at application start. This is run by adjusting the number of iterations the user wants and running them by clicking on the \"run iterations\" button.");

    TextField heading4 = new TextField("4. Edit the value of the configuration");
    heading4.setEditable(false);
    heading4.getStyleClass().add("user-manual-title");
    TextArea instructions4 = getStyledTextArea("There are two ways to do this :\n" +
        "    a. Edit Current Game Description. The user updates the value of the configuration that is currently being used.\n" +
        "    b. Edit Selected Description. The user can select a file to read from the drop down list and then edit the selected file. The user does not need to click on the \"update chaos game\" button in order for the \"edit selected description\" button to work. If the user chooses to update the chaos game after reading from the file, they can also use the \"edit current description\" button to update the configuration.\n" +
        "    After doing so, the user has to enter the name of the file they want to save the description to. It is possible to create a new file, by writing a new name in the input, or the user can replace the existing file by writing the name of the file.\n" +
        "    In order to save the file, the user has to click on the \"save description\" button. Otherwise, all of the changes will be lost.");

    TextField heading5 = new TextField("5. The run steps iterations");
    heading5.setEditable(false);
    heading5.getStyleClass().add("user-manual-title");
    TextArea instructions5 = getStyledTextArea("The run steps iterations runs the game for the given number of steps. It is possible to clear out the canvas by clicking on the \"clear canvas\" button.");

    container.getChildren().addAll(heading1, instructions1, heading2, instructions2, heading3, instructions3, heading4, instructions4, heading5, instructions5);

    Button okayButton = new Button("Understood");
    container.setAlignment(javafx.geometry.Pos.CENTER);
    okayButton.setOnAction(e -> {
      popupStage.close();
    });

    PopupScene.dimBackground(gameController.getPrimaryStage(), popupStage);
    container.getChildren().add(okayButton);
    popupLayout.getChildren().add(container);
    showPopupStage(popupStage, popupLayout, 600, 600);
    popupStage.toFront();
  }


}
