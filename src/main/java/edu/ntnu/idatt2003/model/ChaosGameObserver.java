package edu.ntnu.idatt2003.model;

public interface ChaosGameObserver {
  void updateAddPixel(double X0, double X1);
  void updateRemovePixel(double X0, double X1);
}
