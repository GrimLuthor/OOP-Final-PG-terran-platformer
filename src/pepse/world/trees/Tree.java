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

/**
 * Tree class represents a tree object in the game world.
 * It extends GameObject and contains leaves and fruits.
 */
public class Tree extends GameObject {
    private final Set<Leaf> leaves = new HashSet<>();
    private final Set<Fruit> fruits = new HashSet<>();
    private Vector2 topLeftCorner;
    private Trunk trunk;

    /**
     * Constructs a Tree object with the specified top-left corner position.
     * @param topLeftCorner The top-left corner position of the tree
     */
    public Tree(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ZERO, null);
        this.topLeftCorner = topLeftCorner;
        createTrunk();
        createLeavesAndFruits();
    }

    /**
     * Creates the trunk of the tree.
     */
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

    /**
     * Creates leaves and fruits of the tree.
     */
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

    /**
     * Creates a leaf at the specified position.
     * @param position The position of the leaf
     */
    private void createLeaf(Vector2 position) {
        Renderable leafRender =
                new RectangleRenderable(ColorSupplier.approximateColor(LEAF_COLOR));
        Leaf leaf = new Leaf(position,
                Vector2.ONES.mult(BLOCK_SIZE), leafRender);
        leaves.add(leaf);
    }

    /**
     * Creates a fruit at the specified position.
     * @param position The position of the fruit
     */
    private void createFruit(Vector2 position) {
        Renderable fruitRender =
                new OvalRenderable(ColorSupplier.approximateColor(FRUIT_COLOR_1));
        Vector2 size = Vector2.ONES.mult(FRUIT_SIZE);
        Fruit fruit = new Fruit(position.add(size.mult(f0_2)), size, fruitRender);
        fruits.add(fruit);
    }

    /**
     * Retrieves the set of fruits of the tree.
     * @return The set of fruits of the tree
     */
    public Set<Fruit> getFruits() {
        return fruits;
    }

    /**
     * Retrieves the set of leaves of the tree.
     * @return The set of leaves of the tree
     */
    public Set<Leaf> getLeaves() {
        return leaves;
    }

    /**
     * Retrieves the trunk of the tree.
     * @return The trunk of the tree
     */
    public Trunk getTrunk() {
        return trunk;
    }
}
