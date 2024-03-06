package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;

import java.util.List;

public class ChaosGameDescription {

    private Vector2D minCoords;
    private Vector2D maxCoords;
    private List<Transform2D> transforms;

    public ChaosGameDescription(Vector2D minCoords, Vector2D maxCoords, List<Transform2D> transforms) {
        this.minCoords = minCoords;
        this.maxCoords = maxCoords;
        this.transforms = transforms;
    }

    public Vector2D getMinCoords() {
        return minCoords;
    }

    public Vector2D getMaxCoords() {
        return maxCoords;
    }

    public List<Transform2D> getTransforms() {
        return transforms;
    }
}
