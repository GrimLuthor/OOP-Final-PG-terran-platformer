package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static pepse.util.Constants.*;
/**
 * Block class represents a static block object in the game world.
 * It extends GameObject and is used for creating terrain or obstacles.
 */
public class Block extends GameObject{
    /**
     * Constructs a Block object with the specified position and renderable.
     * @param topLeftCorner The position of the top-left corner of the block
     * @param renderable The renderable representing the appearance of the block
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(GROUND_TAG);
    }
}
