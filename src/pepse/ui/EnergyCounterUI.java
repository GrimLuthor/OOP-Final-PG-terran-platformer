package pepse.ui;


import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.Avatar;

public class EnergyCounterUI {

    public static GameObject create (Avatar avatar) {
        TextRenderable energyCountText = new TextRenderable("100%");
        GameObject energyCounterUI = new GameObject(Vector2.ZERO, Vector2.of(100f,50f),energyCountText);

        energyCounterUI.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        energyCounterUI.setTag(Constants.ENERGY_COUNTER_TAG);

        energyCounterUI.addComponent(deltaTime -> energyCountText.setString(
                (int)((avatar.getEnergyCount()/Constants.ENERGY_POINTS_MAX)*100)+"%"));

        return energyCounterUI;
    }
}
