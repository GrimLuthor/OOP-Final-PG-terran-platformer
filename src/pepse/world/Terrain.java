package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Terrain {
    private final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private final NoiseGenerator noiseGenerator;
    private final float groundHeightAtX0;
    private final int TERRAIN_DEPTH = 20;
    private final int FACTOR = 7;

    public Terrain(Vector2 windowDimensions, int seed) {
        groundHeightAtX0 = windowDimensions.y() * (2f/3f);
        noiseGenerator = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    private float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Block.SIZE * FACTOR);
        return groundHeightAtX0 + noise;
    }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        int x = (minX / Block.SIZE) * Block.SIZE;
        // if x was negative then it was rounded upwards, so we need to subtract a block
        x -= minX < x ? Block.SIZE : 0;
        while(x < maxX) {
            float y = (float) Math.floor(groundHeightAt(x) / Block.SIZE) * Block.SIZE;
            for (int i = 0; i < TERRAIN_DEPTH; i++) {
                Vector2 position = new Vector2(x, y);
                Renderable blockRender =
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block block = new Block(position, blockRender);
                block.setTag("ground");
                blocks.add(block);
                y += Block.SIZE;
            }
            x += Block.SIZE;
        }
        return blocks;
    }
}
