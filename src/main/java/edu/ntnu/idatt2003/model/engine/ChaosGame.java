package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;

import java.util.Random;

/**
 * The chaos game is represented av class ChaosGame and must have instances of both the description and a canvas for drawing.
 * In addition, it must know which point (x0, x1) it is at at any given time.
 * For most fractals, it will work to initialize this to (0, 0).
 * So the most important thing - a method runSteps(int) to play a given number of rounds of the chaos game.
 * In the method, you must throw dice (use the class java.util.Random and the method nextInt(int)), choose a transformation, move there, and draw a point.
 * Implement the class ChaosGame according to figure 6.
 */

public class ChaosGame {
    /**
     * The canvas where the fractal is drawn.
     */
    private ChaosCanvas canvas;
    /**
     * The description of the chaos game.
     */
    private ChaosGameDescription description;
    /**
     * The current point in the chaos game.
     */
    private Vector2D currentPoint;
    /**
     * The random number generator.
     */
    private Random random;

    /**
     * Constructor for the ChaosGame class.
     * The constructor initialises the attributes of the class.
     * The current point is initialised to (0, 0), and the canvas is initialised with the given width and height.
     *
     * @param inputDescription the description of the chaos game
     * @param width            the width of the canvas
     * @param height           the height of the canvas
     */

    public ChaosGame(ChaosGameDescription inputDescription, int width, int height) {
        this.description = inputDescription;
        this.canvas = new ChaosCanvas(width, height, inputDescription.getMinCoords(), inputDescription.getMaxCoords());
        this.currentPoint = new Vector2D(0, 0);
        this.random = new Random();
    }

    /**
     * Getter for the canvas.
     *
     * @return the canvas
     */
    public ChaosCanvas getCanvas() {
        return canvas;
    }

    /**
     * Runs a given number of steps of the chaos game.
     * In each step, a random transformation is chosen from the list of transformations in the description.
     * The current point is then transformed using the chosen transformation, and the new point is drawn on the canvas.
     *
     * @param steps the number of steps to run
     */

    public void runSteps(int steps) {
        for (int i = 0; i < steps; i++) {
            int randomIndex = random.nextInt(description.getTransforms().size());
            Vector2D newPoint = description.getTransforms().get(randomIndex).transform(currentPoint);
            canvas.putPixel(newPoint);
            currentPoint = newPoint;
        }
    }

}
