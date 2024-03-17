package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import static pepse.util.Constants.*;
/**
 * Sky class provides a static method to create a sky game object.
 */
public class Sky {
    /**
     * Creates a sky game object with the specified window dimensions.
     * @param windowDimensions The dimensions of the game window
     * @return The created sky game object
     */
    public static GameObject create(Vector2 windowDimensions) {
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(SKY_TAG);
        return sky;
    }
}
