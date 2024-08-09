package edu.ntnu.idatt2003.model.observer;

/**
 * An interface for the ChaosGameObserver class.
 *
 * @since 1.0.0
 * @version 1.0.0
 * @author Kaamya Shinde
 */
public interface ChaosGameObserver {
  void updateAddPixel(double x0, double x1);

  void updateRemovePixel(double x0, double x1);
}
