package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.world.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tree {
    private final Vector2 trunkTopLeftCorner;

    List<GameObject> leaves = new ArrayList<>();
    GameObject trunk;
    Random random;

    public Tree(Vector2 topLeftCorner,Random random) {

        this.random = random;

        Renderable trunkRender = new RectangleRenderable(Constants.TRUNK_COLOR);
        trunkTopLeftCorner = topLeftCorner;
        trunk = new GameObject(topLeftCorner, Vector2.of(Constants.BLOCK_SIZE, Constants.TREE_HEIGHT), trunkRender);
        createLeaves();
    }

    private void createLeaves() {
        Vector2 leavesTopLeftCorner = Vector2.of(trunkTopLeftCorner.x() - 3.5f * Constants.BLOCK_SIZE
                , trunkTopLeftCorner.y() - 4f * Constants.BLOCK_SIZE);

        float y = leavesTopLeftCorner.y();
        float x = leavesTopLeftCorner.x();


        for (int row = 0; row < Constants.LEAVES_CROWN_SIZE; row++) {
            for (int col = 0; col < Constants.LEAVES_CROWN_SIZE; col++) {
                if (random.nextInt(100) < 80) {
                    GameObject leaf = new GameObject(Vector2.of(x,y), Vector2.ONES.mult(Constants.BLOCK_SIZE),
                            new RectangleRenderable(ColorSupplier.approximateColor(Constants.LEAF_COLOR)));
                    leaf.setTag(Constants.LEAF_TAG);
                    leaves.add(leaf);
                }
                x += Constants.BLOCK_SIZE;
            }
            y += Constants.BLOCK_SIZE;
            x = leavesTopLeftCorner.x();
        }

    }

    public List<GameObject> getLeaves() {
        return leaves;
    }

    public GameObject getTrunk() {
        return trunk;
    }
}
