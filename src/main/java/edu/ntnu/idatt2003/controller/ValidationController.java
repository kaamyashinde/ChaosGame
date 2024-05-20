package edu.ntnu.idatt2003.controller;

public class ValidationController {
  public static void validateInteger(String input) throws IllegalArgumentException {
    try {
      Integer.parseInt(input);
      System.out.println("valid integer");
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Input must be an integer");
    }
  }
  public static void  validateFileName(String input) throws  IllegalArgumentException{
    if (input == null || input.isEmpty()) {
      throw new IllegalArgumentException("File name cannot be empty");
    }
  }
}
