package edu.ntnu.idatt2003.model.observer;

/**
 * An interface for the ChaosGameObserver class.
 */
public interface ChaosGameObserver {
  void updateAddPixel(double x0, double x1);

  void updateRemovePixel(double x0, double x1);
}
