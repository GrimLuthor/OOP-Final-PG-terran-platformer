package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.ui.EnergyCounterUI;
import pepse.util.AvatarMovement;
import pepse.util.Constants;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;
import pepse.world.trees.Fruit;
import pepse.world.trees.Leaf;
import pepse.world.trees.Tree;

import java.util.List;
import java.util.function.*;


public class PepseGameManager extends GameManager {


    private Avatar avatar;
    private Terrain terrain;
    private Flora flora;

    private int mostLeftChunk = 0;
    private int mostRightChunk = 0;

    private Vector2 windowDimensions;

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowDimensions = windowController.getWindowDimensions();

        gameObjects().layers().shouldLayersCollide(Constants.BLOCK_LAYER, Constants.AVATAR_LAYER, true);

        // create sky:
        GameObject sky = Sky.create(windowController.getWindowDimensions());
        gameObjects().addGameObject(sky, Constants.SKY_LAYER);

        // create night:
        GameObject night = Night.create(windowController.getWindowDimensions(),
                Constants.NIGHT_DAY_CYCLE_INTERVAL);
        gameObjects().addGameObject(night, Constants.NIGHT_SHADOW_LAYER);

        // create sun:
        GameObject sun = Sun.create(windowController.getWindowDimensions(), Constants.SUN_CYCLE_INTERVAL);
        gameObjects().addGameObject(sun, Constants.SUN_LAYER);

        // create sun halo:
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo, Constants.SUN_HALO_LAYER);


        // create avatar:
        avatar = new Avatar(
                new Vector2(0, windowController.getWindowDimensions().y() * Constants.SUN_POS_HEIGHT_OFFSET),
                inputListener,
                imageReader);
        setCamera(new Camera(Vector2.ZERO,
                windowController.getWindowDimensions(), windowController.getWindowDimensions()));
        gameObjects().addGameObject(avatar, Constants.AVATAR_LAYER);

        // create energy UI:
        GameObject energyCounterUI = EnergyCounterUI.create(avatar);
        gameObjects().addGameObject(energyCounterUI, Constants.ENERGY_COUNTER_LAYER);

        // create terrain:
        terrain = new Terrain(windowDimensions, Constants.SEED);
        generateTerrain(0, Constants.TERRAIN_CHUNK_SIZE);

        // create trees:
        flora = new Flora(terrain);
        generateFlora(0, Constants.TERRAIN_CHUNK_SIZE);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // update camera
        camera().setCenter(Vector2.of(avatar.getCenter().x(), windowDimensions.y() / 2));

        // infinite gen:
        handleWorldGeneration();
    }

    private void generateTerrain(int minX, int maxX) {
        List<Block> blocks = terrain.createInRange(minX, maxX);
        for (Block block : blocks) {
            gameObjects().addGameObject(block, Constants.BLOCK_LAYER);
        }
    }

    private void generateFlora(int minX, int maxX) {
        // TODO: problem here :( condition is not good
        Supplier<AvatarMovement> getMovementMode = avatar::getMovementMode;
        BooleanSupplier condition = () -> getMovementMode.get() == AvatarMovement.JUMPING;
        List<Tree> trees = flora.createInRange(minX, maxX);
        for (Tree tree : trees) {
            gameObjects().addGameObject(tree.getTrunk(), Constants.TRUNK_LAYER);
            tree.getTrunk().setOperation(gameObjectOperation(tree.getTrunk(), condition));
            for (Leaf leaf : tree.getLeaves()) {
                // here we set the tree operation for the leaf
                leaf.setOperation(gameObjectOperation(leaf, condition));
                gameObjects().addGameObject(leaf, Constants.LEAF_LAYER);
            }
            for (Fruit fruit : tree.getFruits()) {
                // here we set the tree operation for the fruit
                fruit.setOperation(gameObjectOperation(fruit, condition));
                gameObjects().addGameObject(fruit, Constants.LEAF_LAYER);
            }
        }
    }

    private void handleWorldGeneration() {
        if (avatar.getCenter().x() - ((mostLeftChunk) * Constants.TERRAIN_CHUNK_SIZE) < windowDimensions.x() / 2) {
            int minX = (mostLeftChunk - 1) * Constants.TERRAIN_CHUNK_SIZE;
            int maxX = minX + Constants.TERRAIN_CHUNK_SIZE;
            generateTerrain(minX, maxX);
            generateFlora(minX, maxX);
            mostLeftChunk -= 1;
        }
        if (((mostRightChunk + 1) * Constants.TERRAIN_CHUNK_SIZE) - avatar.getCenter().x() < windowDimensions.x() / 2) {
            int minX = (mostRightChunk + 1) * Constants.TERRAIN_CHUNK_SIZE;
            int maxX = minX + Constants.TERRAIN_CHUNK_SIZE;
            generateTerrain(minX, maxX);
            generateFlora(minX, maxX);
            mostRightChunk += 1;
        }
    }

    private Consumer<GameObject> gameObjectOperation(GameObject gameObject, BooleanSupplier condition) {
        switch(gameObject.getTag()) {
            case Constants.LEAF_TAG:
                return (leaf) -> {
                    if (condition.getAsBoolean()) {
                        leafOperation().accept(leaf);
                    }
                };
            case Constants.FRUIT_TAG:
                return (fruit) -> {
                    if (condition.getAsBoolean()) {
                        fruitOperation().accept(fruit);
                    }
                };
            case Constants.TRUNK_TAG:
                return (trunk) -> {
                    if (condition.getAsBoolean()) {
                        trunkOperation().accept(trunk);
                    }
                };
            default:
                return null;
        }
    }

    private Consumer<GameObject> leafOperation(){
        // creating many transitions for each leaf while the avatar is in the air, so it doesn't work properly
        return (leaf) -> new Transition<>(
                leaf,
                leaf.renderer()::setRenderableAngle,
                leaf.renderer().getRenderableAngle(),
                leaf.renderer().getRenderableAngle() + 90f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                1f,
                Transition.TransitionType.TRANSITION_ONCE,
                null);
    }

    private Consumer<GameObject> fruitOperation(){
        return (fruit) -> {
            // TODO
        };
    }

    private Consumer<GameObject> trunkOperation(){
        return (trunk) -> {
            // TODO
        };
    }
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
