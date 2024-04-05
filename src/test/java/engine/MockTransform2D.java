package engine;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;
import edu.ntnu.idatt2003.model.transformations.Transform2D;

public class MockTransform2D extends Transform2D {

    @Override
    public Vector2D transform(Vector2D point) {
        // Mock implementation
        return point;
    }
}