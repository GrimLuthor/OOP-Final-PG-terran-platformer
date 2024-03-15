package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.util.HashSet;
import java.util.Set;

import static pepse.util.Constants.*;

public class Tree extends GameObject {
    private final Set<Leaf> leaves = new HashSet<>();
    private final Set<Fruit> fruits = new HashSet<>();
    private Vector2 topLeftCorner;
    private Trunk trunk;

    public Tree(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.topLeftCorner = topLeftCorner;
        createTrunk();
        createLeavesAndFruits();
    }

    private void createTrunk() {
        Renderable trunkRender = new RectangleRenderable(TRUNK_COLOR);
        int h = AVG_TREE_HEIGHT;
        int trunkHeight = (int) (rand.nextFloat(h * f1_2 - h * f0_8) + h * f0_8);
        topLeftCorner = topLeftCorner.add(Vector2.of(0, -trunkHeight));
        trunk = new Trunk(topLeftCorner, Vector2.of(BLOCK_SIZE,
                trunkHeight + BLOCK_SIZE * HALF), trunkRender);
        trunk.physics().preventIntersectionsFromDirection(Vector2.ZERO);
        trunk.physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }

    private void createLeavesAndFruits() {
        float half = LEAVES_CROWN_SIZE * HALF;
        Vector2 leavesTopLeftCorner =
                Vector2.of(topLeftCorner.x() - (half - HALF) * BLOCK_SIZE,
                topLeftCorner.y() - half * BLOCK_SIZE);

        float y = leavesTopLeftCorner.y();
        float x = leavesTopLeftCorner.x();

        for (int row = 0; row < LEAVES_CROWN_SIZE; row++) {
            for (int col = 0; col < LEAVES_CROWN_SIZE; col++) {
                float random = rand.nextFloat();
                if (random < LEAF_PROBABILITY) {
                    createLeaf(Vector2.of(x, y));
                }
                else if (random < FRUIT_PROBABILITY) {
                    createFruit(Vector2.of(x, y));
                }
                x += BLOCK_SIZE;
            }
            y += BLOCK_SIZE;
            x = leavesTopLeftCorner.x();
        }

    }

    private void createLeaf(Vector2 position) {
        Renderable leafRender =
                new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR));
        Leaf leaf = new Leaf(position,
                Vector2.ONES.mult(BLOCK_SIZE), leafRender);
        leaves.add(leaf);
    }

    private void createFruit(Vector2 position) {
        Renderable fruitRender =
                new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR_1));
        Vector2 size = Vector2.ONES.mult(FRUIT_SIZE);
        Fruit fruit = new Fruit(position.add(size.mult(f0_2)), size, fruitRender);
        fruits.add(fruit);
    }

    public Set<Fruit> getFruits() {
        return fruits;
    }
    public Set<Leaf> getLeaves() {
        return leaves;
    }

    public Trunk getTrunk() {
        return trunk;
    }
}
