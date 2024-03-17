package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import static pepse.util.Constants.*;

/**
 * Trunk class represents the trunk of a tree object in the game world.
 * It extends GameObject and provides methods to change its color.
 */
public class Trunk extends GameObject {

    /**
     * Constructs a Trunk object with the specified top-left corner position, size, and renderable.
     * @param topLeftCorner The top-left corner position of the trunk
     * @param size The size of the trunk
     * @param renderable The renderable representing the appearance of the trunk
     */
    public Trunk(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(TRUNK_TAG);
    }

    /**
     * Changes the color of the trunk.
     * @param color The new color of the trunk
     */
    public void changeColor(Color color) {
        // Set the renderable object of the trunk with a new color
        this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(color)));
    }
}
