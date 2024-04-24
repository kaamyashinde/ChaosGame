package edu.ntnu.idatt2003.model;

import javafx.scene.paint.Color;

public interface Observer {
  void updateAddPixel(double X0, double X1);
  void updateRemovePixel(double X0, double X1);
}
