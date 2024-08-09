package edu.ntnu.idatt2003.controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This class is responsible for controlling the actions to the application when the observer is notified in the model.
 *
 * @author Kaamya Shinde
 * @version 0.2
 * @since 3.3.0
 */

public class ObserverActionController {
  private final int[][] pixelCounts = new int[500][500];

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
   * Method that is responsible for adding the color to the canvas based on how many times a point has been visited.
   *
   * @param X0 The x-coordinate of the pixel.
   * @param X1 The y-coordinate of the pixel.
   * @param gc The graphics context of the canvas.
   */
  public void addColorBasedOnCount(double X0, double X1, GraphicsContext gc) {
    int x = (int) X0;
    int y = (int) X1;

    pixelCounts[x][y]++;
    int colorValue = 255 - pixelCounts[x][y];
    if (colorValue < 0) colorValue = 0;
    Color color = Color.rgb(colorValue, colorValue, colorValue);

    gc.setFill(color);
    gc.fillRect(x, y, 1, 1);
  }

  /**
   * Method that is responsible for removing any sort of color from the canvas.
   */
  public void removeColor(double X0, double X1, GraphicsContext gc) {
    gc.clearRect(X0, X1, 1, 1);
  }
}
