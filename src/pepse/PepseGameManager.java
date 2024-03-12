package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.ui.EnergyCounterUI;
import pepse.util.Constants;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Tree;

import java.util.List;

public class PepseGameManager extends GameManager {


    private Avatar avatar;
    private Vector2 windowDimensions;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);


        windowDimensions = windowController.getWindowDimensions();

        gameObjects().layers().shouldLayersCollide(Constants.BLOCK_LAYER,Constants.AVATAR_LAYER,true);

        // create sky:
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Constants.SKY_LAYER);

        // create night:
        GameObject night = Night.create(windowController.getWindowDimensions(), 50);
        gameObjects().addGameObject(night, Constants.NIGHT_SHADOW_LAYER);

        // create sun:
        GameObject sun = Sun.create(windowController.getWindowDimensions(),100);
        gameObjects().addGameObject(sun, Constants.SUN_LAYER);

        // create sun halo:
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo,Constants.SUN_HALO_LAYER);


        // create avatar:
        avatar = new Avatar(
                new Vector2(0,windowController.getWindowDimensions().y()*Constants.SUN_POS_HEIGHT_OFFSET),
                inputListener,
                imageReader);
        setCamera(new Camera(Vector2.ZERO,
                windowController.getWindowDimensions(), windowController.getWindowDimensions()));
        gameObjects().addGameObject(avatar,Constants.AVATAR_LAYER);

        // create energy UI:
        GameObject energyCounterUI = EnergyCounterUI.create(avatar);
        gameObjects().addGameObject(energyCounterUI,Constants.ENERGY_COUNTER_LAYER);

        // create terrain:
        Terrain terrain = new Terrain(windowController.getWindowDimensions(), Constants.SEED);
        List<Block> blocks = terrain.createInRange(Constants.RANGE[0], Constants.RANGE[1]);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Constants.BLOCK_LAYER);
        }

        // create trees:
        Flora flora = new Flora(terrain);
        List<Tree> trees = flora.createInRange(Constants.RANGE[0], Constants.RANGE[1]);
        for (Tree tree : trees) {
            gameObjects().addGameObject(tree, Constants.TREE_LAYER);
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // update camera
        camera().setCenter(Vector2.of(avatar.getCenter().x(), windowDimensions.y()/2));
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
