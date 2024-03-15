package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import static pepse.util.Constants.*;

public class SunHalo {
    public static GameObject create(GameObject sun) {
        GameObject sunHalo = new GameObject(sun.getTopLeftCorner(),
                Vector2.ONES.mult(SUN_HALO_SIZE),
                new OvalRenderable(SUN_HALO_COLOR));

        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);

        return sunHalo;
    }
}
