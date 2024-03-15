package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;
import java.util.function.Consumer;

import static pepse.util.Constants.*;

public class Fruit extends GameObject {
    private Consumer<GameObject> eatFruit;

    public Fruit(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(FRUIT_TAG);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(AVATAR_TAG) && eatFruit != null) {
            eatFruit.accept(this);
        }
    }

    public void setEatFruitOperation(Consumer<GameObject> eatFruit) {
        this.eatFruit = eatFruit;
    }

    public void changeColor(Color color) {
        this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(color)));
    }
}
