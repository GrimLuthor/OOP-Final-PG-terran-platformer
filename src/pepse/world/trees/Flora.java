package pepse.world.trees;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flora {
    private final Terrain terrain; //TODO: callback instead of terrain instance
    private final Random rand = new Random(Constants.SEED);
    public Flora(Terrain terrain) {
        this.terrain = terrain;
    }
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();

        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            if (rand.nextInt(100) < 10) {
                trees.add(createTree(x));
                x += Constants.BLOCK_SIZE;
            }
        }
        return trees;
    }

    private Tree createTree(int x) {
        float y = terrain.groundHeightAt(x) - Constants.TREE_HEIGHT;
        return new Tree(Vector2.of(x, y),rand);
    }
}
