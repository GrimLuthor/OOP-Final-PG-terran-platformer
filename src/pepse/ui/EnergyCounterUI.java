package pepse.ui;


import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.Avatar;

import java.awt.*;

import static pepse.util.Constants.*;

/**
 * EnergyCounterUI class represents the user interface element for displaying the avatar's energy level.
 * It provides a static method to create an energy counter UI GameObject with specified properties.
 */
public class EnergyCounterUI {
    /**
     * Creates an energy counter UI GameObject for displaying the avatar's energy level.
     *
     * @param avatar The avatar whose energy level will be displayed
     * @return The created energy counter UI GameObject
     */
    public static GameObject create(Avatar avatar) {
        TextRenderable energyCountText = new TextRenderable("100%");
        GameObject energyCounterUI = new GameObject(Vector2.ZERO,
                ENERGY_COUNTER_UI_DIMENSIONS, energyCountText);

        energyCounterUI.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyCounterUI.setTag(ENERGY_COUNTER_TAG);

        energyCounterUI.addComponent(
                (deltaTime) -> {
                    int percentage = (int) ((avatar.getEnergyCount() /
                            ENERGY_POINTS_MAX) * ENERGY_POINTS_MAX);
                    energyCountText.setString(percentage + "%");
                    if (percentage == 0) {
                        energyCountText.setColor(Color.RED);
                    } else {
                        energyCountText.setColor(Color.BLACK);
                    }
                }
        );

        return energyCounterUI;
    }
}
