package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.List;
import static pepse.util.Constants.*;

public class Terrain {
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;

    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = windowDimensions.y() * TERRAIN_POS_OFFSET;
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        int x = (minX / BLOCK_SIZE) * BLOCK_SIZE;
        // if x was negative then it was rounded upwards, so we need to subtract a block
        x -= minX < x ? BLOCK_SIZE : 0;
        while(x < maxX) {
            float y = groundHeightAt(x);
            for (int i = 0; i < TERRAIN_DEPTH; i++, y += BLOCK_SIZE) {
                blocks.add(createBlock(Vector2.of(x, y)));
            }
            x += BLOCK_SIZE;
        }
        return blocks;
    }

    public float groundHeightAt(float x) {
        float noise = (float)
                noiseGenerator.noise(x, BLOCK_SIZE * TERRAIN_NOISE_FACTOR);
        x = groundHeightAtX0 + noise;
        return (float) Math.floor(x / BLOCK_SIZE) * BLOCK_SIZE;
    }

    private Block createBlock(Vector2 position) {
        Renderable blockRender =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        return new Block(position, blockRender);
    }
}
