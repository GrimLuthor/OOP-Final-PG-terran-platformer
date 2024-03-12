package pepse.ui;


import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.Avatar;

import java.awt.*;

public class EnergyCounterUI {

    public static GameObject create(Avatar avatar) {
        TextRenderable energyCountText = new TextRenderable("100%");
        GameObject energyCounterUI = new GameObject(Vector2.ZERO,
                Constants.ENERGY_COUNTER_UI_DIMENSIONS, energyCountText);

        energyCounterUI.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyCounterUI.setTag(Constants.ENERGY_COUNTER_TAG);

        energyCounterUI.addComponent((deltaTime) ->
                {
                    int percentage = (int) ((avatar.getEnergyCount() / Constants.ENERGY_POINTS_MAX) * 100);
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
