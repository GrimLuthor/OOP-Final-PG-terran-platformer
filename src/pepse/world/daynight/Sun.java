package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import static pepse.util.Constants.*;

/**
 * Sun class represents the sun object in the game world.
 * It provides a static method to create a sun GameObject with specified properties.
 */
public class Sun {
    /**
     * Creates a sun GameObject with the specified window dimensions and cycle length.
     * @param windowDimensions The dimensions of the game window
     * @param cycleLength The length of the day-night cycle
     * @return The created sun GameObject
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {

        Vector2 sunDimensions = Vector2.ONES.mult(SUN_SIZE);
        Vector2 sunPos = new Vector2(windowDimensions.x() * HALF,
                windowDimensions.y() * SUN_POS_HEIGHT_OFFSET)
                .subtract(sunDimensions.mult(HALF));

        GameObject sun = new GameObject(
                sunPos,
                sunDimensions,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        sun.setTag(SUN_TAG);

        Vector2 horizonCenterPoint = new Vector2(windowDimensions.x() * HALF,
                windowDimensions.y() * TWO_THIRDS);

        Vector2 initialSunCenter = sun.getCenter();

        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter(initialSunCenter
                .subtract(horizonCenterPoint)
                .rotated(angle)
                .add(horizonCenterPoint)),
                0f,
                CIRCLE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
                );

        return sun;
    }

}
