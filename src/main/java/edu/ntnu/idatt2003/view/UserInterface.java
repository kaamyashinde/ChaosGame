package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    /**
     * Constant corresponding to the quite action.
     */
    private static final int QUIT = 0;
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
     * Constant corresponding to the default configuration.
     */
    private static final int DEFAULT_CONFIG = 5;
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
     * It initiates an instance of the chaosGameDescription class using dummy data from a file.
     * This is to ensure that the iterations can be run without the user being forced to manually run the readFromFile method.
     */
    private static void init() {
        chaosGameDescription = ChaosGameFileHandler.readFromFile("src/main/java/edu/ntnu/idatt2003/resources/testAffine.txt");
    }

    /**
     * Method to start the UserInterface.
     * <p>
     * It prints a welcome message and runs the triggerChoice method in a loop until the user decides to quit.
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
            case QUIT -> quit();
            case READ_FROM_FILE -> readFromFile();
            case WRITE_TO_FILE -> writeToFile();
            case RUN_ITERATIONS -> runIterations();
            case PRINT_FRACTAL -> printFractal();
            case 9 -> printFern();
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
     * <p>
     * It reads the name of the file from the user and uses the ChaosGameFileHandler class to read the description from the file. This is then printed to the console for the user to view.
     * </p>
     */
    private static void readFromFile() {
        input.nextLine();

        System.out.println("Enter the name of the file you want to read from: ");
        String fileName = input.nextLine();

        String filePath = "src/main/java/edu/ntnu/idatt2003/resources/" + fileName;
        chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath);

        System.out.println("Here is the description of the chaos game read from the file:");
        System.out.println(chaosGameDescription);
    }

    /**
     * Method to write to a file.
     * <p>
     * It reads the name of the file from the user and uses the ChaosGameFileHandler class to write the description to the file.
     * The user can choose between generating an affine or a julia transformation.
     * The user can also choose the number of transformations to generate.
     * The generated chaos game description is then written to the file.
     * Since the data generated is saved in the chosGameDescription object, it can be used to run iterations and print the fractal without having to run the readFromFile method after writing toFile.
     * </p>
     */
    private static void writeToFile() {
        System.out.println("Enter the name of the file you want to write to: ");
        String fileName = input.next();

        String filePath = "src/main/java/edu/ntnu/idatt2003/resources/" + fileName;

        System.out.println("Which kind of transformation do you want to generate? 1: Affine, 2: Julia");
        int choice = input.nextInt();
        if (choice == 1) {
            chaosGameDescription = ChaosGameFileHandler.initiateTransformationsAffine(filePath, 3);
        } else if (choice == 2) {
            chaosGameDescription = ChaosGameFileHandler.initiateTransformationsJulia(filePath, 3);
        } else {
            System.out.println("Invalid input, please try again.");
        }
        ChaosGameFileHandler.writeToFile(chaosGameDescription, filePath);

    }

    /**
     * Method to run a specific number of iterations
     * <p>
     *    The user can decide between entering values for the width and height of the canvas or use the default configuration.
     *    Then the user is prompted to enter the number of iterations to run. A message is printed after the iterations are run.
     * </p>
     */
    private static void runIterations() {
        if (chaosGameDescription == null) {
            System.out.println("You need to read a chaos game description from a file first.");
            return;
        }
        System.out.println("Would u like to enter the size of the canvas or use the default configuration? 1: Enter size, 2: Default configuration");
        int choice = input.nextInt();
        int inputWidth;
        int inputHeight;
        if (choice == 1) {
            System.out.println("Enter the width of the canvas: ");
            inputWidth = input.nextInt();
            System.out.println("Enter the height of the canvas: ");
            inputHeight = input.nextInt();
        } else {
            inputWidth = 60;
            inputHeight = 20;
        }
        chaosGame = new ChaosGame(chaosGameDescription, inputWidth, inputHeight);

        System.out.println("Enter the number of iterations you want to run: ");
        int iterations = input.nextInt();
        chaosGame.runSteps(iterations);
        System.out.println("Successfully ran " + iterations + " iterations.");
    }

    /**
     * Method to print the ASCII-fractal to the console.
     * <p>
     *     If the chaosGame object is empty, the user is prompted to run the chaos game first.
     *     The chaosGame object might be empty if the user has not run the runIterations method.
     * </p>
     */
    private static void printFractal() {

        if (chaosGame == null) {
            System.out.println("You need to run the chaos game first.");
            return;
        }
        ChaosCanvas canvas = chaosGame.getCanvas();
        int[][] canvasArray = canvas.getCanvasArray();
        for (int i = 0; i < canvasArray.length; i++) {
            for (int j = 0; j < canvasArray[i].length; j++) {
                System.out.print(canvasArray[i][j] == 0 ? " " : "*");
            }
            System.out.println(" ");
        }


    }
    /**
     * Method that is uesd to test whether the Bransley fern is printed correctly.
     */
    private static void printFern(){
        Vector2D minCoords = new Vector2D(-2.65, 0);
        Vector2D maxCoords = new Vector2D(2.65, 10);
        Transform2D transform1 = new AffineTransform2D(
                new Matrix2x2(0,0,0,0.16),
                new Vector2D(0,0)
        );
        Transform2D transform2 = new AffineTransform2D(
                new Matrix2x2(0.85,0.04,-0.04,0.85),
                new Vector2D(0,1.6)
        );
        Transform2D transform3 = new AffineTransform2D(
                new Matrix2x2(0.2,-0.26,0.23,0.22),
                new Vector2D(0,1.6)
        );
        Transform2D transform4 = new AffineTransform2D(
                new Matrix2x2(-0.15,0.28,0.26,0.24),
                new Vector2D(0,0.44)
        );
        ChaosGameDescription chaosGameDescription = new ChaosGameDescription(
                minCoords,
                maxCoords, List.of(transform1, transform2, transform3, transform4)
        );
        chaosGame = new ChaosGame(chaosGameDescription, 200, 100);
        System.out.println("Enter the number of iterations you want to run: ");
        int n = input.nextInt();
        chaosGame.runSteps(n);
        printFractal();
    }
}
