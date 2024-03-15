package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
import static pepse.util.Constants.*;

public class Sun {
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
