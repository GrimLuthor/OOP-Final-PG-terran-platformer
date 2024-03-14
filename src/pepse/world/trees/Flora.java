package pepse.world.trees;

import danogl.util.Vector2;
import pepse.util.Constants;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Flora {
    private final Terrain terrain; //TODO: callback instead of terrain instance
    public Flora(Terrain terrain) {
        this.terrain = terrain;
    }
    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();

        for (int x = minX; x < maxX; x += Constants.BLOCK_SIZE) {
            if (Constants.rand.nextDouble() < Constants.TREE_PROBABILITY) {
                trees.add(createTree(x));
                x += Constants.BLOCK_SIZE;
            }
        }
        return trees;
    }

    private Tree createTree(int x) {
        float y = terrain.groundHeightAt(x);
        return new Tree(Vector2.of(x, y));
    }
}
