package edu.ntnu.idatt2003.controller;

public class ValidationController {
  public static void validateInteger(String input) throws IllegalArgumentException {
    try {
      int value = Integer.parseInt(input);
      if (value < 0) {
        throw new IllegalArgumentException("Input must be a positive integer");
      }
      //System.out.println("valid integer");
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Input must be an integer");
    }
  }
  public static void  validateFileName(String input) throws  IllegalArgumentException{
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be empty");
    }
  }
  public static void validateDouble(String input) throws IllegalArgumentException {
    try {
      Double.parseDouble(input);
      //System.out.println("valid double");
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Input must be a double");
    }
  }
}
