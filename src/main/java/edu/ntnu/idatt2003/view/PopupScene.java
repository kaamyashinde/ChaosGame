package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.controller.KeyActionPolicyController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * This class is responsible for providing stage creation for the popup windows, for both the user feedback and the edit values pop-up.
 *
 * @author Kaamya Shinde
 * @version 0.1
 * @since 0.3.6
 */
public class PopupScene {

  // Private constructor to prevent instantiation
  private PopupScene() {}

  /**
   * Method that initialises the pop-up stage.
   *
   * @param title The title of the pop-up stage.
   * @return popupStage The stage to be used.
   */
  public static Stage createPopupStage(String title, Stage primaryStage) {
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.initOwner(primaryStage);
    popupStage.setTitle(title);
    return popupStage;
  }

  /**
   * Method that creates the layout of the pop-up window.
   *
   * @param popupStage The stage to be used.
   * @return popupLayout The layout of the pop-up window.
   */
  public static VBox createPopupLayout(Stage popupStage) {
    VBox popupLayout = new VBox();
    popupLayout.prefWidthProperty().bind(popupStage.widthProperty());
    popupLayout.prefHeightProperty().bind(popupStage.heightProperty());
    return popupLayout;
  }

  /**
   * Method that dims the background when the pop-up window is displayed.
   */
  public static void dimBackground(Stage primaryStage, Stage popupStage) {
    Region overlay = new Region();
    overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");
    overlay.setVisible(false);

    ((Pane) primaryStage.getScene().getRoot()).getChildren().add(overlay);
    overlay.prefWidthProperty().bind(primaryStage.widthProperty());
    overlay.prefHeightProperty().bind(primaryStage.heightProperty());

    popupStage.showingProperty().addListener((observable, oldValue, newValue) -> overlay.setVisible(newValue));
  }

  /**
   * Method that shows the pop-up stage.
   *
   * @param popupStage  The stage to be used.
   * @param popupLayout The layout of the pop-up window.
   */
  public static void showPopupStage(Stage popupStage, VBox popupLayout, int width, int height) {
    Scene popuScene = new Scene(popupLayout, width, height);
    KeyActionPolicyController.applyEnterKeyActionPolicy(popupStage);

    String popupStyles = Objects.requireNonNull(UserFeedback.class.getResource("/stylesheets/popupStyles.css")).toExternalForm();
    String buttonStyles = Objects.requireNonNull(UserFeedback.class.getClassLoader().getResource("stylesheets/buttonStyles.css")).toExternalForm();

    popuScene.getStylesheets().addAll(popupStyles, buttonStyles);
    popupStage.setScene(popuScene);
    popupStage.show();
  }

}
