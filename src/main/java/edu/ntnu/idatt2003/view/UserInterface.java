package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;

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
     * An object of the ChaosGameDescription class that will be used to store the data read from the file.
     */
    private static ChaosGameDescription chaosGameDescription;
    /**
     * An object of the ChaosGame class that will run the chaos game.
     */
    private static ChaosGame chaosGame;
    /**
     * An object of the ChaosCanvas class that would be used to print the fractal.
     */
    private static ChaosCanvas chaosCanvas;

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
     * <p>
     * It initates an instance of the chaosGameDescription class usign dummmy data from a file.
     */
    private static void init() {
        chaosGameDescription = ChaosGameFileHandler.readFromFile("src/main/java/edu/ntnu/idatt2003/model/engine/testAffine.txt");
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
            case 0 -> quit();
            case READ_FROM_FILE -> readFromFile();
            case WRITE_TO_FILE -> writeToFile();
            case RUN_ITERATIONS -> runIterations();
            case PRINT_FRACTAL -> printFractal();
            default -> System.out.println("Invalid input, please try again.");
        }
    }

    /**
     * Method to quit the application.
     */
    private static void quit() {
        running = false;

        System.out.println("Thank you for using the Chaos Game!");
        System.out.println("Successfully exited the application.");

        System.exit(0);
    }

    /**
     * Method to read a chaos game description from a file.
     */
    private static void readFromFile() {
        input.nextLine();

        System.out.println("Enter the name of the file you want to read from: ");
        String fileName = input.nextLine();

        String filePath = "src/main/java/edu/ntnu/idatt2003/model/engine/" + fileName;
        chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath);

        System.out.println("Here is the description of the chaos game read from the file:");
        System.out.println(chaosGameDescription);
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
        System.out.println("Enter the width of the canvas: ");
        int inputWidth = input.nextInt();
        System.out.println("Enter the height of the canvas: ");
        int inputHeight = input.nextInt();
        chaosGame = new ChaosGame(chaosGameDescription, inputWidth, inputHeight);

        System.out.println("Enter the number of iterations you want to run: ");
        int iterations = input.nextInt();
        chaosGame.runSteps(iterations);
        System.out.println("Successfully ran " + iterations + " iterations.");
    }

    /**
     * Method to print the ASCII-fractal to the console.
     */
    private static void printFractal() {

        if (chaosGame == null) {
            System.out.println("You need to run the chaos game first.");
        } else {
            chaosCanvas = chaosGame.getCanvas();
            int[][] theArray = chaosCanvas.getCanvasArray();
            for (int i = 0; i < theArray.length; i++) {
                for (int j = 0; j < theArray[i].length; j++) {
                    System.out.println(theArray[i][j] == 1 ? "X" : "-");
                }
                System.out.println();
            }
        }
    }
}
