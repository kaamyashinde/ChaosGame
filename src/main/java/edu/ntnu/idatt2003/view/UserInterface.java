package edu.ntnu.idatt2003.view;

import java.util.Scanner;

public class UserInterface {
    /**
     * Constant corresponding to the read from file action.
     */
    private static final int READ_FROM_FILE = 1;
    /**
     * Constant corresponding to the write to file action.
     */
    private static final int WRITE_TO_FILE = 2;
    /**
     * Constant corresponding to the run iterations action.
     */
    private static final int RUN_ITERATIONS = 3;
    /**
     * Constant corresponding to the print fractal action.
     */
    private static final int PRINT_FRACTAL = 4;
    /**
     * A Scanner object to read input from the console.
     */
    private static final Scanner input = new Scanner(System.in);
    /**
     * A boolean to keep track of whether the application is running or not.
     */
    private static boolean running = true;

    /**
     * Constructor for the UserInterface class.
     */
    private UserInterface() {
    }

    public static void launch() {
        init();
        start();
    }

    /**
     * Method to initialize the UserInterface.
     */
    private static void init() {
    }

    /**
     * Method to start the UserInterface.
     */
    private static void start() {
        System.out.println(
                """
                        ===================CHAOS GAME v.01===================
                                
                        Hello and Welcome to the Chaos Game!
                        """);
        while (running) {
            triggerChoice();
        }

    }

    /**
     * Method to show the menu and take the user input.
     *
     * @return the integer corresponding the action the user wants to perform.
     */
    private static int showMenu() {
        System.out.println(
                """
                        ------------MENU----------
                        0. Exit the application
                        1. Read description from a file
                        2. Write description to a file
                        3. Run a given number of iterations
                        4. Print the ASCII-fractal to the console
                        --------------------------
                        What would u like to do? (Enter a number between 0 and 4).
                        """);
        return input.nextInt();
    }

    /**
     * Uses the user input from the showMenu method to trigger the corresponding action.
     */
    private static void triggerChoice() {
        int choice = showMenu();
        switch (choice) {
            case 0 -> System.exit(0);
            case READ_FROM_FILE -> readFromFile();
            case WRITE_TO_FILE -> writeToFile();
            case RUN_ITERATIONS -> runIterations();
            case PRINT_FRACTAL -> printFractal();
            default -> System.out.println("Invalid input, please try again.");
        }
    }

    /**
     * Method to read a chaos game description from a file.
     */
    private static void readFromFile() {
    }

    /**
     * Method to write to a file.
     */
    private static void writeToFile() {
    }

    /**
     * Method to run a specific number of iterations
     */
    private static void runIterations() {
    }

    /**
     * Method to print the ASCII-fractal to the console.
     */
    private static void printFractal() {
    }
}
