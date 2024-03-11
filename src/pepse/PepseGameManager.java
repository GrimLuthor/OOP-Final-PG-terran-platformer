package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;

import java.util.List;

public class PepseGameManager extends GameManager {
    private static final int SKY_LAYER = 0;
    private static final int BLOCK_LAYER = 1;
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, SKY_LAYER);

        Terrain terrain = new Terrain(windowController.getWindowDimensions(), 4);
        List<Block> blocks = terrain.createInRange(0, 2000);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, BLOCK_LAYER);
        }
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
