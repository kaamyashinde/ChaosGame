package edu.ntnu.idatt2003.view;

import edu.ntnu.idatt2003.model.basicLinalg.Complex;
import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.InitateTransformations;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;

import java.util.ArrayList;
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
     * A Scanner object to read input from the console.
     */
    private static final Scanner input = new Scanner(System.in);
    /**
     * A string containing the error message for invalid input.
     */
    private static final String INVALID_INPUT = "-> Invalid input, please try again.";
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
     * Constructor for the UserInterface class.
     */
    private UserInterface() {
    }

    /**
     * Method to launch the UserInterface.
     * It runs the init and start methods.
     */
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
        chaosGameDescription = ChaosGameFileHandler.readFromFile("src/main/java/edu/ntnu/idatt2003/resources/Default.txt");
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
     * <ol>
     *     <li> The user is able to read a description from a file. This description is printed out and the configurations are saved in the variable {@code chaosGameDescription}.</li>
     *     <li> The user is able to write a description to a file. If the file does not exist, a new one will be created. The description can either be generated automatically or entered manually.</li>
     *     <li> The user can run a given number of iterations for a specific chaosGame description.</li>
     *     <li> The result from running the ChaosGame is printed out to the console along with which description it belongs to.</li>
     *     <li> The user is provided with a list of presets whose description can be read and written to a file that the user chooses. </li>
     * </ol>
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
                        4. Print the fractal to the console
                        --------------------------
                        What would u like to do? (Enter a number between 0 and 5).
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
            default -> System.out.println(INVALID_INPUT);
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

        String filePath = "src/main/java/edu/ntnu/idatt2003/resources/" + fileName + ".txt";

        chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath);
        if (chaosGameDescription != null) {
            System.out.println("-> Here is the description of the chaos game read from the file:");
            System.out.println(chaosGameDescription);
        }
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

        String filePath = "src/main/java/edu/ntnu/idatt2003/resources/" + fileName + ".txt";
        System.out.println("Do u want to generate your own transformation or use a preset? 1: ");
        System.out.println("""
                1. Own
                2. Preset
                """);
        int ownOrPreset = input.nextInt();
        if (ownOrPreset == 1) {
            System.out.println("Which kind of transformation do you want to generate? 1: Affine, 2: Julia");
            int choice = input.nextInt();
            if (choice == 1) {
                chaosGameDescription = initiateTransformationAffine();
            } else if (choice == 2) {
                chaosGameDescription = initiateTransformationJulia();
            } else {
                System.out.println(INVALID_INPUT);
            }
        } else if (ownOrPreset == 2) {
            runPreset();

        } else {
            System.out.println(INVALID_INPUT);
        }
        System.out.println("-> Your choice has been registered.");
        System.out.println("""
                -> If you want to see the description, please choose to the option to read from a file.
                -> If you want to run the iterations, please choose the option to run the iterations.
                """);
        ChaosGameFileHandler.writeToFile(chaosGameDescription, filePath);

    }

    /**
     * Method to initiate the transformation for the Affine set.
     * <p>
     * The user is prompted to enter the coordinates for the transformations.
     * Then the user is asked whether they want to create the transformations manually or automatically.
     * <ol>
     *     <li>
     *     If the user chooses to create the transformations manually, they are prompted to enter the values for the complex number for each transformation.
     *     </li>
     *     <li>
     *         If the user chooses to create the transformations automatically, they are prompted to enter the number of transformations to generate.
     *         </li>
     * </ol>
     *
     * @return an object of the ChaosGameDescription class containing the coordinates and transformations.
     */

    private static ChaosGameDescription initiateTransformationAffine() {
        Vector2D[] coords = userInputForCoordsOfTransformations();

        System.out.println("How do you want to generate the affine transformations? 1: Manually, 2: Automatically");
        int choice = input.nextInt();

        List<Transform2D> transforms = new ArrayList<>();

        if (choice == 1) {
            System.out.println("How many transformations do you want to generate?");
            int numberOfTransformations = input.nextInt();

            for (int i = 0; i < numberOfTransformations; i++) {
                System.out.println("Enter the values for the matrix for transformation " + (i + 1) + ". Separate the values with a space. For example: 1 0 0 1");
                double matrix00 = input.nextDouble();
                double matrix01 = input.nextDouble();
                double matrix10 = input.nextDouble();
                double matrix11 = input.nextDouble();
                Matrix2x2 matrix = new Matrix2x2(matrix00, matrix01, matrix10, matrix11);

                System.out.println("Enter the values for the vector for transformation " + (i + 1) + ". Separate the values with a space. For example: 0 0");
                double vector0 = input.nextDouble();
                double vector1 = input.nextDouble();
                Vector2D vector = new Vector2D(vector0, vector1);

                transforms.add(new AffineTransform2D(matrix, vector));
            }
        } else if (choice == 2) {
            System.out.println("How many transformations do you want to generate?");
            int numberOfTransformations = input.nextInt();
            System.out.println("Enter the values for the matrix. Separate the values with a space. For example: 1 0 0 1");
            double matrix00 = input.nextDouble();
            double matrix01 = input.nextDouble();
            double matrix10 = input.nextDouble();
            double matrix11 = input.nextDouble();
            transforms = InitateTransformations.listOfTransformationsAffineGeneration(numberOfTransformations, matrix00, matrix01, matrix10, matrix11);
        } else {
            System.out.println(INVALID_INPUT);
        }
        return new ChaosGameDescription(coords[0], coords[1], transforms);
    }

    /**
     * Method to initiate the transformation for the Julia set.
     * <p>
     * The user is prompted to enter the coordinates for the transformations.
     * Then the user is asked whether they want to create the transformations manually or automatically.
     * <ol>
     *     <li>
     *     If the user chooses to create the transformations manually, they are prompted to enter the values for the complex number for each transformation.
     *     </li>
     *     <li>
     *         If the user chooses to create the transformations automatically, they are prompted to enter the number of transformations to generate.
     *         </li>
     * </ol>
     *
     * @return an object of the ChaosGameDescription class containing the coordinates and transformations.
     */
    private static ChaosGameDescription initiateTransformationJulia() {
        Vector2D[] coords = userInputForCoordsOfTransformations();

        System.out.println("How do you want to generate the Julia transformations? 1: Manually, 2: Automatically");
        int choice = input.nextInt();

        List<Transform2D> transforms = new ArrayList<>();

        if (choice == 1) {
            System.out.println("How many transformations do you want to add");
            int numberOfTransformations = input.nextInt();

            for (int i = 0; i < numberOfTransformations; i++) {
                System.out.println("Enter the values for the complex number for transformation " + (i + 1) + ". Separate the values with a space. For example: 0 1");
                double realPart = input.nextDouble();
                double imaginaryPart = input.nextDouble();
                System.out.println("What is the sign of the complex number? 1: Positive, 2: Negative");
                int sign = input.nextInt();
                Complex point = new Complex(realPart, imaginaryPart);
                transforms.add(new JuliaTransform(point, sign));
            }
        } else if (choice == 2) {
            System.out.println("How many transformations do you want to generate?");
            int numberOfTransformations = input.nextInt();
            transforms = InitateTransformations.listOfTransformationsJuliaGeneration(numberOfTransformations);
        } else {
            System.out.println(INVALID_INPUT);
        }
        return new ChaosGameDescription(coords[0], coords[1], transforms);
    }

    /**
     * Method that prompts the user for the minimum and maximum coordinates of a transformation.
     * This is then used to create an array of Vector2D objects that is returned.
     *
     * @return an array of Vector2D objects containing the min and max coordinates.
     */
    private static Vector2D[] userInputForCoordsOfTransformations() {
        System.out.println("What are the minimum coordinates for the canvas? Separate the x and y values with a space. For example: 0 0");
        double minCoordsX0 = input.nextDouble();
        double minCoordsX1 = input.nextDouble();
        System.out.println("What are the maximum coordinates for the canvas? Separate the x and y values with a space. For example: 1 1");
        double maxCoordsX0 = input.nextDouble();
        double maxCoordsX1 = input.nextDouble();
        return InitateTransformations.coordsForTransformation(minCoordsX0, minCoordsX1, maxCoordsX0, maxCoordsX1);
    }

    /**
     * Method to run a specific number of iterations
     * <p>
     * The user can decide between entering values for the width and height of the canvas or use the default configuration.
     * Then the user is prompted to enter the number of iterations to run. A message is printed after the iterations are run.
     * </p>
     */
    private static void runIterations() {
        if (chaosGameDescription == null) {
            System.out.println("-> You need to read a chaos game description from a file first.");
            return;
        }
        System.out.println("Would u like to enter the size of the canvas or use the default configuration? 1: Enter size, 2: Default configuration");
        System.out.println("The default configuration is 60x20");
        int choice = input.nextInt();
        int inputWidth = 60;
        int inputHeight = 20;
        if (choice == 1) {
            System.out.println("Enter the width of the canvas: ");
            inputWidth = input.nextInt();
            System.out.println("Enter the height of the canvas: ");
            inputHeight = input.nextInt();
        }
        chaosGame = new ChaosGame(chaosGameDescription, inputWidth, inputHeight);

        System.out.println("Enter the number of iterations you want to run: ");
        int iterations = input.nextInt();
        try {
            chaosGame.runSteps(iterations);
            System.out.println("Successfully ran " + iterations + " iterations.");
        } catch (Exception e) {
            System.out.println("-> The configurations provided cannot be run as a chaos game. Please write a new configuration or try running one of our default configurations.");
            chaosGame = null;
        }

    }

    /**
     * Method to print the ASCII-fractal to the console.
     * <p>
     * If the chaosGame object is empty, the user is prompted to run the chaos game first.
     * The chaosGame object might be empty if the user has not run the runIterations method.
     * </p>
     */
    private static void printFractal() {
        if (chaosGame == null) {
            System.out.println("You need to run the chaos game first.");
            return;
        }
        System.out.println("-> Printing out the fractal now:");
        System.out.println("--------------------------------------------------------");
        ChaosCanvas canvas = chaosGame.getCanvas();
        int[][] canvasArray = canvas.getCanvasArray();
        for (int[] yValues : canvasArray) {
            for (int xValues : yValues) {
                System.out.print(xValues == 0 ? " " : "*");
            }
            System.out.println(" ");
        }
    }

    /**
     * A case switch that handles the printing of the desired preset.
     */
    private static void runPreset() {
        int choice = initiatePreset();
        switch (choice) {
            case 1 -> chaosGameDescription = defaultSierpinskiTriangle();
            case 2 -> chaosGameDescription = defaultBarnsleyFern();
            case 3 -> chaosGameDescription = defaultJuliaSet();
            default -> System.out.println(INVALID_INPUT);
        }
    }

    /**
     * Method that prompts the user to choose a preset from the following list:
     * <ol>
     *     <li>Sierpinski triangle</li>
     *     <li>Barnsley fern</li>
     *     <li>Julia set</li>
     * </ol>
     *
     * @return Returns an integer corresponding to the preset chosen.
     */

    private static int initiatePreset() {
        System.out.println("Which preset would you like to use? 1:");
        System.out.println("""
                1. Sierpinski triangle
                2. Barnsley fern
                3. Julia set
                """);
        return input.nextInt();
    }


    /**
     * The method initiates the Sierpinski triangle preset.
     *
     * @return an object of the ChaosGameDescription class containing the coordinates and transformations for the Sierpinski triangle.
     */
    private static ChaosGameDescription defaultSierpinskiTriangle() {
        Vector2D[] coords = InitateTransformations.coordsForTransformation(0, 0, 1, 1);
        List<Transform2D> transforms = InitateTransformations.listOfTransformationsAffineGeneration(3, 0.5, 0, 0, 0.5);
        return new ChaosGameDescription(coords[0], coords[1], transforms);
    }

    /**
     * The method initiates the Barnsley fern preset.
     *
     * @return an object of the ChaosGameDescription class containing the coordinates and transformations for the Barnsley fern.
     */
    private static ChaosGameDescription defaultBarnsleyFern() {
        Vector2D[] coords = InitateTransformations.coordsForTransformation(-2.65, 0, 2.65, 10);
        Transform2D transform1 = new AffineTransform2D(
                new Matrix2x2(0, 0, 0, 0.16),
                new Vector2D(0, 0)
        );
        Transform2D transform2 = new AffineTransform2D(
                new Matrix2x2(0.85, 0.04, -0.04, 0.85),
                new Vector2D(0, 1.6)
        );
        Transform2D transform3 = new AffineTransform2D(
                new Matrix2x2(0.2, -0.26, 0.23, 0.22),
                new Vector2D(0, 1.6)
        );
        Transform2D transform4 = new AffineTransform2D(
                new Matrix2x2(-0.15, 0.28, 0.26, 0.24),
                new Vector2D(0, 0.44)
        );
        List<Transform2D> transforms = List.of(transform1, transform2, transform3, transform4);
        return new ChaosGameDescription(coords[0], coords[1], transforms);
    }

    /**
     * The method initiates the Julia set preset.
     *
     * @return an object of the ChaosGameDescription class containing the coordinates and transformations for the Julia set.
     */
    private static ChaosGameDescription defaultJuliaSet() {
        Vector2D[] coords = InitateTransformations.coordsForTransformation(-1.6, -1.0, 1.6, 1.0);
        List<Transform2D> transforms = InitateTransformations.listOfTransformationsJuliaGeneration(10);
        return new ChaosGameDescription(coords[0], coords[1], transforms);
    }
}
