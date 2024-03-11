package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;

import java.util.List;

public class PepseGameManager extends GameManager {
    private static final int SKY_LAYER = 0;
    private static final int BLOCK_LAYER = 1;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        // create sky:
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, SKY_LAYER);

        // create night:
        GameObject night = Night.create(windowController.getWindowDimensions(), 5);
        gameObjects().addGameObject(night, Layer.FOREGROUND);

        // create sun:
        GameObject sun = Sun.create(windowController.getWindowDimensions(),10);
        gameObjects().addGameObject(sun, SKY_LAYER);

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
