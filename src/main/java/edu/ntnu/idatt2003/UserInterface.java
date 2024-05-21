package edu.ntnu.idatt2003;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.engine.ChaosCanvas;
import edu.ntnu.idatt2003.model.engine.ChaosGame;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescription;
import edu.ntnu.idatt2003.model.engine.ChaosGameFileHandler;
import edu.ntnu.idatt2003.model.engine.ChaosGameDescriptionCreator;
import edu.ntnu.idatt2003.model.factory.ChaosGameDescriptionFactory;
import edu.ntnu.idatt2003.model.transformations.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * The UserInterface class is responsible for handling the user input and output.
 * The class provides a menu for the user to choose from.
 * The user can choose to read a description from a file,
 * write a description to a file,
 * run a given number of iterations, print the fractal to the console,
 * clear the canvas, or change the canvas size.
 */
public class UserInterface {

  private static final int QUIT = 0;

  private static final int READ_FROM_FILE = 1;

  private static final int WRITE_TO_FILE = 2;

  private static final int RUN_ITERATIONS = 3;

  private static final int PRINT_FRACTAL = 4;

  private static final int CLEAR_CANVAS = 5;

  private static final int CHANGE_CANVAS_SIZE = 6;

  private static final Scanner input = new Scanner(System.in);

  private static final String INVALID_INPUT = "-> Invalid input, please try again.";

  private static boolean running = true;

  private static ChaosGameDescription chaosGameDescription;

  private static ChaosGame chaosGame;

  private static ChaosGameDescription chaosGameDescriptionDuplicate;

  private static int canvasWidth = 60;

  private static int canvasHeight = 20;

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
   * It initiates an instance of the chaosGameDescription class using dummy data from a file.
   * This is to ensure
   * that the iterations can be run without the user being
   * forced to manually run the readFromFile method.
   */
  private static void init() {
    chaosGameDescription = ChaosGameDescriptionFactory.defaultSierpinskiTriangle();
  }

  /**
   * Method to start the UserInterface.
   * It prints a welcome message and runs the triggerChoice
   * method in a loop until the user decides to quit.
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
   * Shows the menu and takes in user input.
   * <ol>
   *     <li> The user is able to read a description from a file.
   *     This description is printed out and the configurations are
   *     saved in the variable {@code chaosGameDescription}.</li>
   *     <li> The user is able to write a description to a file.
   *     If the file does not exist, a new one will be created.
   *     The description can either be generated automatically or entered
   *     manually.</li>
   *     <li> The user can run a given number of iterations for a specific
   *     chaosGame description.</li>
   *     <li> The result from running the ChaosGame is printed out to the
   *     console along with which description it belongs to.</li>
   *     <li> The user is provided with a list of presets whose description
   *     can be read and written to a file that the user chooses. </li>
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
            5. Clear the canvas
            6. Change the canvas size
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
      case CLEAR_CANVAS -> clearCanvas();
      case CHANGE_CANVAS_SIZE -> changeCanvasSize();
      default -> System.out.println(INVALID_INPUT);
    }
  }

  /**
   * Ends the application.
   */
  private static void quit() {
    running = false;

    System.out.println("Thank you for using the Chaos Game!");
    System.out.println("Successfully exited the application.");

    System.exit(0);
  }

  /**
   * Reads a chaos game description from a file.
   * <p>
   * It reads the name of the file from the user and uses the ChaosGameFileHandler class
   * to read the description from the file.
   * This is then printed to the console for the user to view.
   * </p>
   */
  private static void readFromFile() {
    input.nextLine();

    System.out.println("Enter the name of the file you want to read from: ");
    String fileName = input.nextLine();

    String filePath = "src/main/resources/" + fileName + ".txt";

    chaosGameDescription = ChaosGameFileHandler.readFromFile(filePath);
    if (chaosGameDescription != null) {
      System.out.println("-> Here is the description of the chaos game read from the file:");
      System.out.println(chaosGameDescription);
    }
  }

  /**
   * Writes to a file.
   * <p>
   * It reads the name of the file from the user and uses the
   * ChaosGameFileHandler class to write the description to the file.
   * The user can choose between generating an affine or a julia transformation.
   * The user can also choose the number of transformations to generate.
   * The generated chaos game description is then written to the file.
   * Since the data generated is saved in the chosGameDescription object,
   * it can be used to run iterations and print the fractal without having
   * to run the readFromFile method after writing toFile.
   * </p>
   */
  private static void writeToFile() {
    System.out.println("Enter the name of the file you want to write to: ");
    String fileName = input.next();

    String filePath = "src/main/resources/" + fileName + ".txt";
    System.out.println("Do u want to generate your own transformation or use a preset? 1: ");
    System.out.println("""
        1. Own
        2. Preset
        """);
    int ownOrPreset = input.nextInt();
    if (ownOrPreset == 1) {
      System.out.println("Which kind of transformation do you want to generate? "
          + "1: Affine, 2: Julia");
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
    * Initiates the transformation for the Affine set.
    * The user is prompted to enter the coordinates for the transformations.
    * Then the user is asked to enter the number of transformations to add.
    * The user is then prompted to enter the values for the matrix
    * and vector for each transformation.
    *
    * @return an object of the ChaosGameDescription class containing
    * the coordinates and transformations.
    */

  private static ChaosGameDescription initiateTransformationAffine() {
    Vector2D[] coords = userInputForCoordsOfTransformations();

    System.out.println("How many transformations do you want to generate?");
    int numberOfTransformations = input.nextInt();

    double[][] matrices = new double[numberOfTransformations][4];
    double[][] vectors = new double[numberOfTransformations][2];

    for (int i = 0; i < numberOfTransformations; i++) {
      System.out.println("Enter the values for the matrix for transformation " + (i + 1) + "."
          + " Separate the values with a space. For example: 1 0 0 1");
      matrices[i][0] = input.nextDouble();
      matrices[i][1] = input.nextDouble();
      matrices[i][2] = input.nextDouble();
      matrices[i][3] = input.nextDouble();

      System.out.println("Enter the values for the vector for transformation "
          + (i + 1) + ". Separate the values with a space. For example: 0 1");
      vectors[i][0] = input.nextDouble();
      vectors[i][1] = input.nextDouble();
    }

    return ChaosGameDescriptionCreator
        .createAffineChaosGameDescriptionManual(numberOfTransformations, matrices,
            vectors, coords[0].getX0(), coords[0].getX1(), coords[1].getX0(),
            coords[1].getX1());

  }

  /**
   * Initiates the transformation for the Julia set.
   * The user is prompted to enter the coordinates for the
   * transformations.
   * Then the user is asked to enter the number of transformations
   * to add.
   *
   * @return an object of the ChaosGameDescription class containing
   * the coordinates and transformations.
   */
  private static ChaosGameDescription initiateTransformationJulia() {
    Vector2D[] coords = userInputForCoordsOfTransformations();


    System.out.println("Enter the values for the complex number for "
        + "the transformation. Separate the values with a space. For example: 0 1");
    double realPart = input.nextDouble();
    double imaginaryPart = input.nextDouble();

    System.out.println("What is the sign of the complex number? 1: Positive, -1: Negative");
    int sign = input.nextInt();

    return ChaosGameDescriptionCreator.createJuliaChaosGameDescriptionManual(realPart, imaginaryPart, sign, coords[0].getX0(), coords[0].getX1(), coords[1].getX0(), coords[1].getX1());

  }

  /**
   * Prompts the user for the minimum and maximum coordinates of a transformation.
   * This is then used to create an array of Vector2D objects that is returned.
   *
   * @return an array of Vector2D objects containing the min and max coordinates.
   */
  private static Vector2D[] userInputForCoordsOfTransformations() {
    System.out.println("What are the minimum coordinates for the canvas?"
        + " Separate the x and y values with a space. For example: 0 0");
    double minCoordsX0 = input.nextDouble();
    double minCoordsX1 = input.nextDouble();
    System.out.println("What are the maximum coordinates for the canvas? "
        + "Separate the x and y values with a space. For example: 1 1");
    double maxCoordsX0 = input.nextDouble();
    double maxCoordsX1 = input.nextDouble();
    return ChaosGameDescriptionCreator
        .coordsForTransformation(minCoordsX0, minCoordsX1, maxCoordsX0, maxCoordsX1);
  }

  /**
   * Runs a specific number of iterations.
   * <p>
   * The user can decide between entering values for the width and height
   * of the canvas or use the default configuration.
   * Then the user is prompted to enter the number of iterations to run.
   * A message is printed after the iterations are run.
   * </p>
   */
  private static void runIterations() {
    boolean isNewCanvas = isNewCanvas();
    if (isNewCanvas || chaosGame == null) {
      setUpCanvas();
      chaosGameDescriptionDuplicate = chaosGameDescription;
    }

    System.out.println("Enter the number of iterations you want to run: ");
    int iterations = input.nextInt();
    try {
      chaosGame.runSteps(iterations);
      System.out.println("Successfully ran " + iterations + " iterations.");
    } catch (Exception e) {
      System.out.println(e.getMessage());
      System.out.println("-> The configurations provided cannot be run as a chaos game."
          + " Please write a new configuration or try running one of our default configurations.");
      chaosGame = null;
    }

  }

  /**
   * Checks if the canvas is new or not.
   * The method checks if the chaosGameDescriptionDuplicate
   * object is the same as the chaosGameDescription object.
   * If the chaosGameDescriptionDuplicate object is null,
   * it means that no canvas has been created yet.
   * Therefore, the canvas is new.
   * If the chaosGameDescriptionDuplicate object is the same as the chaosGame object,
   * it means that it is the same canvas. So we can add on to the iterations.
   * If the chaosGameDescriptionDuplicate object is not the same as the chaosGame object,
   * it means that we need to clear the canvas and start a new one.
   *
   * @return a boolean value indicating whether the canvas is new or not.
   */
  private static boolean isNewCanvas() {
    if (chaosGameDescriptionDuplicate == null) {
      return true;
    } else {
      return !Objects.equals(chaosGameDescriptionDuplicate.toString(),
          chaosGameDescription.toString());
    }
  }

  /**
   * Sets up the canvas by asking the user whether they want to enter the size of the canvas.
   * or use the default configuration.
   * This information is used to initialise the chaosGame object.
   */
  private static void setUpCanvas() {
    if (chaosGameDescription == null) {
      System.out.println("-> You need to read a chaos game description from a file first.");
      return;
    }
    System.out.println("Would u like to enter the size of the canvas or use"
        + " the default configuration? 1: Enter size, 2: Default configuration");
    System.out.println("The default configuration is " + canvasWidth + "x" + canvasHeight);
    int choice = input.nextInt();
    if (choice == 1) {
      changeCanvasSize();
    }
    chaosGame = new ChaosGame(chaosGameDescription, canvasWidth, canvasHeight);
  }

  /**
   * Clears the canvas.
   */
  private static void clearCanvas() {
    if (chaosGame == null) {
      System.out.println("You need to run the chaos game first.");
      return;
    }
    chaosGame.getCanvas().clear();
    System.out.println("-> Successfully cleared the canvas.");
  }

  /**
   * Changes the size of the canvas.
   */
  private static void changeCanvasSize() {
    System.out.println("Enter the width of the canvas: ");
    canvasWidth = input.nextInt();
    System.out.println("Enter the height of the canvas: ");
    canvasHeight = input.nextInt();
    chaosGame = new ChaosGame(chaosGameDescription, canvasWidth, canvasHeight);
    System.out.println("-> Successfully changed the size of the canvas to " + canvasWidth
        + "x" + canvasHeight + ".");
  }

  /**
   * Prints the ASCII-fractal to the console.
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
    String transformType = (chaosGameDescription.getTransformType() == AffineTransform2D.class
        ? "Affine" : "Julia");
    System.out.println("-> Printing out the fractal of the type "
        + transformType.toUpperCase() + " to the console.");
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
      case 1 -> chaosGameDescription = ChaosGameDescriptionFactory.defaultSierpinskiTriangle();
      case 2 -> chaosGameDescription = ChaosGameDescriptionFactory.defaultBarnsleyFern();
      case 3 -> chaosGameDescription = ChaosGameDescriptionFactory.defaultJuliaSet();
      default -> System.out.println(INVALID_INPUT);
    }
  }

  /**
   * Prompts the user to choose a preset from the following list.:
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


}
