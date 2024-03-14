package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.Constants;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.List;

public class Terrain {
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;

    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = windowDimensions.y() * Constants.TERRAIN_POS_OFFSET;
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        int x = (minX / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
        // if x was negative then it was rounded upwards, so we need to subtract a block
        x -= minX < x ? Constants.BLOCK_SIZE : 0;
        while(x < maxX) {
            float y = groundHeightAt(x);
            for (int i = 0; i < Constants.TERRAIN_DEPTH; i++, y += Constants.BLOCK_SIZE) {
                blocks.add(createBlock(Vector2.of(x, y)));
            }
            x += Constants.BLOCK_SIZE;
        }
        return blocks;
    }

    public float groundHeightAt(float x) {
        float noise = (float)
                noiseGenerator.noise(x, Constants.BLOCK_SIZE * Constants.TERRAIN_NOISE_FACTOR);
        x = groundHeightAtX0 + noise;
        return (float) Math.floor(x / Constants.BLOCK_SIZE) * Constants.BLOCK_SIZE;
    }

    private Block createBlock(Vector2 position) {
        Renderable blockRender =
                new RectangleRenderable(ColorSupplier.approximateColor(Constants.BASE_GROUND_COLOR));
        return new Block(position, blockRender);
    }
}
