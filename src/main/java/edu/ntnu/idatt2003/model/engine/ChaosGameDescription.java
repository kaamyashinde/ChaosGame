package edu.ntnu.idatt2003.model.engine;

import edu.ntnu.idatt2003.model.basicLinalg.Matrix2x2;
import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.AffineTransform2D;
import edu.ntnu.idatt2003.model.transformations.JuliaTransform;
import edu.ntnu.idatt2003.model.transformations.Transform2D;

import java.util.List;
import java.util.Objects;

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

  /*
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChaosGameDescription that = (ChaosGameDescription) obj;
        return Objects.equals(minCoords, that.minCoords) &&
                Objects.equals(maxCoords, that.maxCoords) &&
                Objects.equals(transforms, that.transforms);
    }

    @Override
public int hashCode() {
    return Objects.hash(getMinCoords(), getMaxCoords(), getTransforms());
}
*/
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Check if we have transformations and write the type of the first one
        if (!transforms.isEmpty()) {
            String transformType = transforms.get(0).getClass().getSimpleName();
            sb.append(transformType).append(" # Type of transformation\n");
        }

        // Write the coordinates of the lower left corner
        sb.append(minCoords.getX0()).append(",").append(minCoords.getX1()).append(" # Lower left\n");

        // Write the coordinates of the upper right corner
        sb.append(maxCoords.getX0()).append(",").append(maxCoords.getX1()).append(" # Upper right\n");

        // Write the parameters of each transformation
        int transformNumber = 1;
        for (Transform2D transform : transforms) {
            if (transform instanceof AffineTransform2D) {
                AffineTransform2D affine = (AffineTransform2D) transform;
                Matrix2x2 matrix = affine.getMatrix();
                Vector2D vector = affine.getVector();
                sb.append(matrix.getA00()).append(",").append(matrix.getA01()).append(",")
                        .append(matrix.getA10()).append(",").append(matrix.getA11()).append(",")
                        .append(vector.getX0()).append(",").append(vector.getX1()).append(" # ").append(transformNumber).append("st transform\n");
            } else if (transform instanceof JuliaTransform) {
                JuliaTransform julia = (JuliaTransform) transform;
                Vector2D point = julia.getPoint();
                int sign = julia.getSign();
                sb.append(point.getX0()).append(",").append(point.getX1()).append(",")
                        .append(sign).append(" # ").append(transformNumber).append("st transform\n");
            }
            transformNumber++;
        }

        return sb.toString();
    }
}
