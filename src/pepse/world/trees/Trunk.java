package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import static pepse.util.Constants.*;

public class Trunk extends GameObject {
    public Trunk(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(TRUNK_TAG);
    }

    public void changeColor(Color color) {
        this.renderer().setRenderable(new RectangleRenderable(ColorSupplier.approximateColor(color)));
    }
}
