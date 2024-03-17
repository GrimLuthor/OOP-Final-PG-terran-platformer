package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static pepse.util.Constants.*;

/**
 * Night class represents the night environment in the game world.
 * It provides a static method to create a night GameObject with specified properties.
 */
public class Night {

    /**
     * Creates a night GameObject with the specified window dimensions and cycle length.
     * @param windowDimensions The dimensions of the game window
     * @param cycleLength The length of the day-night cycle
     * @return The created night GameObject
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject night  = new GameObject(Vector2.ZERO,windowDimensions,
                new RectangleRenderable(Color.BLACK));

        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);

        new Transition<>(
                night, // the game object being changed
                night.renderer()::setOpaqueness, // the method to call
                0f, // initial transition value
                MIDNIGHT_OPACITY, // final transition value
                Transition.CUBIC_INTERPOLATOR_FLOAT,    // use a cubic interpolator
                cycleLength, // transition fully over half a day
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, // Choose appropriate ENUM value
                null
        );

        return night;
    }
}
