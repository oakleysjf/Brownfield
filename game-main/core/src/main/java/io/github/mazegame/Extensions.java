package io.github.mazegame;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.MathUtils;

/** Has a selection of useful functions */
public final class Extensions {
    /** Returns a vector clamped between two other vectors.
     * 
     * @param vector the vector to be clamped
     * @param minVector the minimum vector
     * @param maxVector the maximum vector
     * @return the vector clamped between the two other values
     */
    public static Vector2 clamp(Vector2 vector, Vector2 minVector, Vector2 maxVector){
        return new Vector2(MathUtils.clamp(vector.x, minVector.x, maxVector.x),
                           MathUtils.clamp(vector.y, minVector.y, maxVector.y));
    }
}
