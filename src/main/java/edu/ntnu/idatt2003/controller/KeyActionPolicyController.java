package edu.ntnu.idatt2003.controller;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * This class is responsible for applying the key action policy to the stage.
 * @version 0.1
 * @since 0.3.7
 * @author Kaamya Shinde
 */
public class KeyActionPolicyController {
  /**
   * Method that applies the enter key action policy to the stage.
   * This means that when the enter key is pressed,
   * the button that is currently focused is fired.
   *
   * @param stage  The stage that the policy is applied to.
   */
  public static void applyEnterKeyActionPolicy(Stage stage) {
    stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
      if (event.getCode() == KeyCode.ENTER) {
        Node focusOwner = stage.getScene().getFocusOwner();
        if (focusOwner instanceof javafx.scene.control.Button) {
          ((Button) focusOwner).fire();
          event.consume();
        }
      }
    });

  }
}
