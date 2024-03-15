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
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.*;

import static pepse.util.Constants.*;


public class PepseGameManager extends GameManager {

    private Avatar avatar;
    private Terrain terrain;
    private Flora flora;

    private int mostLeftChunk = 0;
    private int mostRightChunk = 0;

    private Vector2 windowDimensions;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(ONE);

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();

        // create sky:
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, SKY_LAYER);

        // create night:
        GameObject night = Night.create(windowController.getWindowDimensions(),
                NIGHT_DAY_CYCLE_INTERVAL);
        gameObjects().addGameObject(night, NIGHT_SHADOW_LAYER);

        // create sun:
        GameObject sun = Sun.create(windowController.getWindowDimensions(), SUN_CYCLE_INTERVAL);
        gameObjects().addGameObject(sun, SUN_LAYER);

        // create sun halo:
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, SUN_HALO_LAYER);

        // create avatar:
        createAvatar(imageReader, inputListener, windowController);

        // create energy UI:
        GameObject energyCounterUI = EnergyCounterUI.create(avatar);
        gameObjects().addGameObject(energyCounterUI, ENERGY_COUNTER_LAYER);

        // create terrain:
        terrain = new Terrain(windowDimensions, SEED);
        generateTerrain(0, TERRAIN_CHUNK_SIZE);

        // create trees:
        flora = new Flora(terrain::groundHeightAt, avatar::getMovementMode);
        generateFlora(0, TERRAIN_CHUNK_SIZE);

        // handle collisions:
        gameObjects().layers().shouldLayersCollide(BLOCK_LAYER, AVATAR_LAYER, true);
        gameObjects().layers().shouldLayersCollide(TRUNK_LAYER, AVATAR_LAYER, true);
        gameObjects().layers().shouldLayersCollide(AVATAR_LAYER, FRUIT_LAYER, true);

    }

    private void createAvatar(ImageReader imageReader, UserInputListener inputListener,
                              WindowController windowController) {
        avatar = new Avatar(Vector2.of(0,
                        windowController.getWindowDimensions().y() * SUN_POS_HEIGHT_OFFSET),
                           inputListener, imageReader);
        setCamera(new Camera(Vector2.ZERO,
                windowController.getWindowDimensions(), windowController.getWindowDimensions()));
        gameObjects().addGameObject(avatar, AVATAR_LAYER);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // update camera
        camera().setCenter(Vector2.of(avatar.getCenter().x(), windowDimensions.y() * HALF));

        // infinite gen:
        handleWorldGeneration();
    }

    private void handleWorldGeneration() {
        if (avatar.getCenter().x() - ((mostLeftChunk) * TERRAIN_CHUNK_SIZE) < windowDimensions.x() * HALF) {
            int minX = (mostLeftChunk - ONE) * TERRAIN_CHUNK_SIZE;
            int maxX = minX + TERRAIN_CHUNK_SIZE;
            generateTerrain(minX, maxX);
            generateFlora(minX, maxX);
            mostLeftChunk--;
        }
        if (((mostRightChunk + ONE) * TERRAIN_CHUNK_SIZE) -
                avatar.getCenter().x() < windowDimensions.x() * HALF) {
            int minX = (mostRightChunk + ONE) * TERRAIN_CHUNK_SIZE;
            int maxX = minX + TERRAIN_CHUNK_SIZE;
            generateTerrain(minX, maxX);
            generateFlora(minX, maxX);
            mostRightChunk++;
        }
    }

    private void generateTerrain(int minX, int maxX) {
        List<Block> blocks = terrain.createInRange(minX, maxX);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, BLOCK_LAYER);
        }
    }

    private void generateFlora(int minX, int maxX) {
        Set<Tree> trees = flora.createInRange(minX, maxX);
        for (Tree tree : trees) {
            gameObjects().addGameObject(tree);
            gameObjects().addGameObject(tree.getTrunk(), TRUNK_LAYER);
            for (Leaf leaf : tree.getLeaves()) {
                gameObjects().addGameObject(leaf, LEAF_LAYER);
            }
            for (Fruit fruit : tree.getFruits()) {
                fruit.setEatFruitOperation(eatFruit());
                gameObjects().addGameObject(fruit, FRUIT_LAYER);
            }
        }
    }

    private Consumer<GameObject> eatFruit(){
        return (item) -> {
            gameObjects().removeGameObject(item, FRUIT_LAYER);
            executor.schedule(() -> gameObjects().addGameObject(item, FRUIT_LAYER),
                    FRUIT_RESPAWN, java.util.concurrent.TimeUnit.SECONDS);
            avatar.setEnergyCount(avatar.getEnergyCount() + ENERGY_FROM_FRUIT);
        };
    }
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
