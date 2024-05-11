package edu.ntnu.idatt2003.view;


import edu.ntnu.idatt2003.model.ChaosGameObserver;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JuliaScene extends Application implements ChaosGameObserver {
  private static final String FILE_PATH = "src/main/resources/";
  AnchorPane layout;
  GraphicsContext gc;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    layout = new AnchorPane();
    VBox root = new VBox();
    layout.prefWidthProperty().bind(primaryStage.widthProperty());
    root.prefWidthProperty().bind(layout.widthProperty());
    layout.getChildren().add(root);
    root.getChildren().addAll(navigationHBox(), titleHBox(), bodyHBox(), footerHBox());
    root.getChildren().stream().filter(node -> node instanceof HBox).forEach(node -> ((HBox) node).prefWidthProperty().bind(root.widthProperty()));

    Scene scene = new Scene(layout, 1000, 700);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void updateAddPixel(double X0, double X1) {
    gc.setFill(Color.BLACK);
    gc.fillRect(X0, X1, 1, 1);
  }

  @Override
  public void updateRemovePixel(double X0, double X1) {
    gc.clearRect(X0, X1, 1, 1);
  }

  /**
   * Method that creates a HBox with a button for the user manual. This HBox is returned.
   *
   * @return The HBox with the button for the user manual.
   */
  private HBox navigationHBox() {
    Button getHelpButton = new Button("User Manual");
    HBox navigationRow = new HBox();
    navigationRow.getChildren().addAll(getHelpButton);
    navigationRow.setAlignment(Pos.CENTER);
    return navigationRow;
  }

  /**
   * Method that sets the title of the scene to "Chaos Game" and returns the HBox with the title.
   *
   * @return The HBox with the title.
   */
  private HBox titleHBox() {
    HBox titleRow = new HBox();
    TextField sceneHeading = new TextField(("Chaos Game"));
    sceneHeading.setEditable(false);
    sceneHeading.setAlignment(Pos.CENTER);
    titleRow.getChildren().add(sceneHeading);
    titleRow.setAlignment(Pos.CENTER);
    return titleRow;
  }

  /**
   * Method that creates the body row by creating three VBoxes and a StackPane.
   * The StackPane is where the canvas is placed and is at the centre of the body row.
   */
  private HBox bodyHBox() {
    HBox bodyRow = new HBox();
    VBox leftBodyRow = new VBox();
    StackPane chaosGamePane = new StackPane();
    VBox rightBodyRow = new VBox();
    //startGame();
    //rightBodyRow.getChildren().add(createPresetButton("Affine"));
    //rightBodyRow.getChildren().add(createPresetButton("Barnsley"));
    bodyRow.getChildren().addAll(leftBodyRow, chaosGamePane, rightBodyRow);
    bodyRow.setAlignment(Pos.CENTER);
    return bodyRow;
  }

  /**
   * Method that returns the footer row.
   */
  private HBox footerHBox() {
    HBox footerRow = new HBox();
    Button addThousandPixelsButton = new Button("Add 10000");
    Button clearCanvasButton = new Button("Clear Canvas");
    footerRow.getChildren().addAll(addThousandPixelsButton, clearCanvasButton);
    footerRow.setAlignment(Pos.CENTER);
    return footerRow;
  }

}
