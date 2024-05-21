package edu.ntnu.idatt2003.controller;

/**
 * This class is responsible for validating input from the user.
 *
 * @author 10041
 * @version 0.1
 * @since 0.3.6
 */
public class ValidationController {
  /**
   * Method that validates that the input can be parsed to a double.
   *
   * @param input The input to be validated.
   * @throws IllegalArgumentException if the input cannot be parsed to a double.
   */
  protected static void validateDouble(String input) throws IllegalArgumentException {
    try {
      Double.parseDouble(input);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Input must be a double");
    }
  }

  /**
   * Method that validates that the input is not null or empty.
   *
   * @param input The input to be validated.
   * @throws IllegalArgumentException if the input is null or empty.
   */
  public static void validateFileName(String input) throws IllegalArgumentException {
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be empty");
    }
  }

  /**
   * Method that validates that the input can be parsed to an integer and that the integer is positive.
   *
   * @param input The input to be validated.
   * @throws IllegalArgumentException if the input cannot be parsed to an integer or if the integer is negative.
   */
  public static void validateInteger(String input) throws IllegalArgumentException {
    try {
      int value = Integer.parseInt(input);
      if (value < 0) {
        throw new IllegalArgumentException("Input must be a positive integer");
      }
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Input must be an integer");
    }
  }
}
