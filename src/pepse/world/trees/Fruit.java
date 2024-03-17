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

/**
 * Fruit class represents a consumable fruit object in the game world.
 * It extends GameObject and can be eaten by the avatar.
 */
public class Fruit extends GameObject {
    // Consumer function to handle fruit consumption
    private Consumer<GameObject> eatFruit;

    /**
     * Constructs a Fruit object with the specified position, size, and renderable.
     * @param topLeftCorner The position of the top-left corner of the fruit
     * @param size The size of the fruit
     * @param renderable The renderable representing the appearance of the fruit
     */
    public Fruit(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(FRUIT_TAG);
    }

    /**
     * Handles collision events with other game objects.
     * If the collision is with an avatar and an eatFruit operation is set, the fruit is consumed.
     * @param other The other game object involved in the collision
     * @param collision The details of the collision
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(AVATAR_TAG) && eatFruit != null) {
            eatFruit.accept(this);
        }
    }

    /**
     * Sets the operation to be executed when the fruit is eaten.
     * @param eatFruit The consumer function to handle fruit consumption
     */
    public void setEatFruitOperation(Consumer<GameObject> eatFruit) {
        this.eatFruit = eatFruit;
    }

    /**
     * Changes the color of the fruit.
     * @param color The new color of the fruit
     */
    public void changeColor(Color color) {
        this.renderer().setRenderable(new OvalRenderable(ColorSupplier.approximateColor(color)));
    }
}
