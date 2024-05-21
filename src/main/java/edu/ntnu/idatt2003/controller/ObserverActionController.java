package edu.ntnu.idatt2003.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is responsible for controlling the actions to the application when the observer is notified in the model.
 *
 * @author 10041
 * @version 0.1
 * @since 3.3.0
 */

public class ObserverActionController {
  /**
   * Method that is responsible for adding the gradient color to the canvas.
   *
   * @param X0 The x-coordinate of the pixel.
   * @param X1 The y-coordinate of the pixel.
   * @param gc The graphics context of the canvas.
   */
  public void addGradientColor(double X0, double X1, GraphicsContext gc) {
    Color startColor = Color.rgb(230, 183, 184);
    Color endColor = Color.rgb(215, 8, 8);
    double fraction = X0 / 500;
    Color gradientColor = startColor.interpolate(endColor, fraction);
    gc.setFill(gradientColor);
    gc.fillRect(X0, X1, 1, 1);
  }

  /**
   * Method that is responsible for removing any sort of color from the canvas.
   */
  public void removeColor(double X0, double X1, GraphicsContext gc) {
    gc.clearRect(X0, X1, 1, 1);
  }

}
