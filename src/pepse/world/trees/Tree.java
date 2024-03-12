package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

public class Tree extends GameObject {
    public Tree (Vector2 topLeftCorner, Renderable trunk) {
        super(topLeftCorner, Vector2.of(Constants.BLOCK_SIZE, Constants.TREE_HEIGHT), trunk);
    }
}
