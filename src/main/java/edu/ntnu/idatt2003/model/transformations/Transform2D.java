package edu.ntnu.idatt2003.model.transformations;

import edu.ntnu.idatt2003.model.basicLinalg.Vector2D;

public abstract class Transform2D {

    public abstract Vector2D transform(Vector2D point);
/*
    @Override
    public boolean equals(Object obj) {
        // Since this is an abstract class, we don't implement the equals method here.
        // Instead, we implement it in each of the subclasses.
        throw new UnsupportedOperationException();
    }
*/
}
