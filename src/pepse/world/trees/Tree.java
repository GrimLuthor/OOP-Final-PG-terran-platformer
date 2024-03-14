package pepse.world.trees;

import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    private final List<Leaf> leaves = new ArrayList<>();
    private final List<Fruit> fruits = new ArrayList<>();
    private Trunk trunk;
    private Vector2 topLeftCorner;

    public Tree(Vector2 topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
        createTrunk();
        createLeavesAndFruits();
    }

    private void createTrunk() {
        Renderable trunkRender = new RectangleRenderable(Constants.TRUNK_COLOR);
        int h = Constants.AVG_TREE_HEIGHT;
        int trunkHeight = (int) (Constants.rand.nextFloat(h * 1.2f - h * 0.8f) + h * 0.8f);
        topLeftCorner = topLeftCorner.add(Vector2.of(0, -trunkHeight));
        trunk = new Trunk(topLeftCorner, Vector2.of(Constants.BLOCK_SIZE,
                trunkHeight + Constants.BLOCK_SIZE / 2f), trunkRender);
        // TODO: make trunk like the block
//        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
//        trunk.physics().preventIntersectionsFromDirection(Vector2.RIGHT);
//        trunk.physics().preventIntersectionsFromDirection(Vector2.LEFT);
//        trunk.physics().preventIntersectionsFromDirection(Vector2.UP);
//        trunk.physics().preventIntersectionsFromDirection(Vector2.DOWN);
//        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    private void createLeavesAndFruits() {
        float half = Constants.LEAVES_CROWN_SIZE / 2f;
        Vector2 leavesTopLeftCorner = Vector2.of(topLeftCorner.x() - (half - 0.5f) * Constants.BLOCK_SIZE,
                topLeftCorner.y() - half * Constants.BLOCK_SIZE);

        float y = leavesTopLeftCorner.y();
        float x = leavesTopLeftCorner.x();

        for (int row = 0; row < Constants.LEAVES_CROWN_SIZE; row++) {
            for (int col = 0; col < Constants.LEAVES_CROWN_SIZE; col++) {
                float random = Constants.rand.nextFloat();
                if (random < Constants.LEAF_PROBABILITY) {
                    createLeaf(Vector2.of(x, y));
                }
                else if (random < Constants.FRUIT_PROBABILITY) {
                    createFruit(Vector2.of(x, y));
                }
                x += Constants.BLOCK_SIZE;
            }
            y += Constants.BLOCK_SIZE;
            x = leavesTopLeftCorner.x();
        }

    }

    private void createLeaf(Vector2 position) {
        Renderable leafRender =
                new RectangleRenderable(ColorSupplier.approximateColor(Constants.LEAF_COLOR));
        Leaf leaf = new Leaf(position,
                Vector2.ONES.mult(Constants.BLOCK_SIZE), leafRender);
        leaves.add(leaf);
    }

    private void createFruit(Vector2 position) {
        Renderable fruitRender =
                new OvalRenderable(ColorSupplier.approximateColor(Constants.FRUIT_COLOR));
        Vector2 size = Vector2.ONES.mult(Constants.BLOCK_SIZE).mult(0.7f);
        Fruit fruit = new Fruit(position.add(size.mult(0.2f)), size, fruitRender);
        fruits.add(fruit);
    }

    public List<Fruit> getFruits() {
        return fruits;
    }
    public List<Leaf> getLeaves() {
        return leaves;
    }

    public Trunk getTrunk() {
        return trunk;
    }
}
