package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.awt.*;

public class Sun {
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {

        Vector2 sunDimensions = Vector2.ONES.mult(Constants.SUN_SIZE);
        Vector2 sunPos = new Vector2(windowDimensions.x() * 0.5f, windowDimensions.y() * Constants.SUN_POS_HEIGHT_OFFSET)
                .subtract(sunDimensions.mult(0.5f));

        GameObject sun = new GameObject(
                sunPos,
                sunDimensions,
                new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);


        sun.setTag(Constants.SUN_TAG);


        Vector2 horizonCenterPoint = new Vector2(windowDimensions.x() * 0.5f,
                windowDimensions.y() * (2f / 3f));

        Vector2 initialSunCenter = sun.getCenter();

        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter(initialSunCenter
                .subtract(horizonCenterPoint)
                .rotated(angle)
                .add(horizonCenterPoint)),
                0f,
                360f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null
                );

        return sun;
    }

}
