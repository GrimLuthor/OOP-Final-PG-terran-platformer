package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Terrain;

import java.util.HashSet;
import java.util.Set;

import static pepse.util.Constants.*;

public class Flora {
    private final Terrain terrain; //TODO: callback instead of terrain instance
    private final Set<Tree> trees = new HashSet<>();
    public Flora(Terrain terrain) {
        this.terrain = terrain;
    }
    public Set<Tree> createInRange(int minX, int maxX) {
        for (int x = minX; x < maxX; x += BLOCK_SIZE) {
            if (rand.nextDouble() < TREE_PROBABILITY) {
                trees.add(createTree(x));
                x += BLOCK_SIZE;
            }
        }
        return trees;
    }

    private Tree createTree(int x) {
        float y = terrain.groundHeightAt(x);
        return new Tree(Vector2.of(x, y));
    }
}
