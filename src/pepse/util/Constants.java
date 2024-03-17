package pepse.util;

import danogl.collisions.Layer;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

/**
 * Constants class contains various constants used throughout the game.
 * These constants include seed, layers, dimensions, positions, tags, colors,
 * terrain generation variables, night-day cycle variables, energy variables,
 * avatar variables, tree variables, spawn probabilities, animation variables, and utility constants.
 */
public class Constants {

    // seed:
    /**
     * The seed used for random number generation.
     */
    public static final int SEED = 4;
    /**
     * Random object initialized with the SEED.
     */
    public static final Random rand = new Random(SEED);

    // layers:
    /**
     * The layer index for the sky background.
     */
    public static final int SKY_LAYER = Layer.BACKGROUND;
    /**
     * The layer index for the sun object.
     */
    public static final int SUN_LAYER = -100;
    /**
     * The layer index for the sun halo object.
     */
    public static final int SUN_HALO_LAYER = -101;
    /**
     * The layer index for block objects.
     */
    public static final int BLOCK_LAYER = 1;
    /**
     * The layer index for trunk objects.
     */
    public static final int TRUNK_LAYER = 2;
    /**
     * The layer index for leaf objects.
     */
    public static final int LEAF_LAYER = 3;
    /**
     * The layer index for fruit objects.
     */
    public static final int FRUIT_LAYER = 4;
    /**
     * The layer index for avatar objects.
     */
    public static final int AVATAR_LAYER = 5;
    /**
     * The layer index for night shadow objects.
     */
    public static final int NIGHT_SHADOW_LAYER = 100;
    /**
     * The layer index for energy counter UI objects.
     */
    public static final int ENERGY_COUNTER_LAYER = Layer.UI;

    // dimensions:
    /**
     * The size of each chunk of generated terrain.
     */
    public static final int TERRAIN_CHUNK_SIZE = 2010;
    /**
     * The size of each block in the game world.
     */
    public static final int BLOCK_SIZE = 30;
    /**
     * The size of the sun object.
     */
    public static final int SUN_SIZE = 150;
    /**
     * The size of the sun halo object.
     */
    public static final int SUN_HALO_SIZE = 250;
    /**
     * The dimensions of the energy counter UI.
     */
    public static final Vector2 ENERGY_COUNTER_UI_DIMENSIONS = Vector2.of(100, 50);

    // positions:
    /**
     * The height offset of the sun position relative to the window dimensions.
     */
    public static final float SUN_POS_HEIGHT_OFFSET = (1f/3f);
    /**
     * The offset of the terrain position relative to the window dimensions.
     */
    public static final float TERRAIN_POS_OFFSET = (2f/3f);

    // tags:
    /**
     * Tag for ground objects.
     */
    public static final String GROUND_TAG = "ground";
    /**
     * Tag for avatar objects.
     */
    public static final String AVATAR_TAG = "avatar";
    /**
     * Tag for night objects.
     */
    public static final String NIGHT_TAG = "night";
    /**
     * Tag for sky objects.
     */
    public static final String SKY_TAG = "sky";
    /**
     * Tag for sun objects.
     */
    public static final String SUN_TAG = "sun";
    /**
     * Tag for sun halo objects.
     */
    public static final String SUN_HALO_TAG = "sun halo";
    /**
     * Tag for energy counter UI objects.
     */
    public static final String ENERGY_COUNTER_TAG = "energy counter";
    /**
     * Tag for trunk objects.
     */
    public static final String TRUNK_TAG = "trunk";
    /**
     * Tag for leaf objects.
     */
    public static final String LEAF_TAG = "leaf";
    /**
     * Tag for fruit objects.
     */
    public static final String FRUIT_TAG = "fruit";

    // colors:
    /**
     * The base color of the ground.
     */
    public static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    /**
     * The color of the sun halo.
     */
    public static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    /**
     * The basic color of the sky.
     */
    public static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    /**
     * The color of tree trunks.
     */
    public static final Color TRUNK_COLOR = new Color(100, 50, 20);
    /**
     * The color of tree leaves.
     */
    public static final Color LEAF_COLOR = new Color(50, 200, 30);

    // terrain gen variables:
    /**
     * The depth of generated terrain.
     */
    public static final int TERRAIN_DEPTH = 20;
    /**
     * The noise factor for terrain generation.
     */
    public static final int TERRAIN_NOISE_FACTOR = 7;

    // night-day cycle variables:
    /**
     * The opacity of the sky at midnight.
     */
    public static final float MIDNIGHT_OPACITY = 0.95f;
    /**
     * The interval for night-day cycle transition.
     */
    public static final float NIGHT_DAY_CYCLE_INTERVAL = 100;
    /**
     * The interval for sun cycle transition.
     */
    public static final float SUN_CYCLE_INTERVAL = NIGHT_DAY_CYCLE_INTERVAL * 2;

    // energy variables:
    /**
     * The maximum energy points for the avatar.
     */
    public static final int ENERGY_POINTS_MAX = 100;
    /**
     * The minimum energy points for the avatar to perform actions.
     */
    public static final int ENERGY_POINTS_MIN = 10;
    /**
     * The energy gained by avatar when consuming fruit.
     */
    public static final int ENERGY_FROM_FRUIT = 10;

    // avatar variables:
    /**
     * The dimensions of the avatar when in idle state.
     */
    public static final Vector2 AVATAR_DIMENSIONS_IDLE = Vector2.of(50, 78);
    /**
     * The dimensions of the avatar when jumping.
     */
    public static final Vector2 AVATAR_DIMENSIONS_JUMP = Vector2.of(69, 73);
    /**
     * The dimensions of the avatar when running.
     */
    public static final Vector2 AVATAR_DIMENSIONS_RUN = Vector2.of(58, 73);
    /**
     * The horizontal velocity of the avatar.
     */
    public static final float VELOCITY_X = 400;
    /**
     * The vertical velocity of the avatar when jumping.
     */
    public static final float VELOCITY_Y = -800;
    /**
     * The gravitational acceleration applied to the avatar.
     */
    public static final float GRAVITY = 1000;

    // tree variables:
    /**
     * The average height of trees.
     */
    public static final int AVG_TREE_HEIGHT = 200;
    /**
     * The size of the crown of leaves on trees.
     */
    public static final int LEAVES_CROWN_SIZE = 6;
    /**
     * The size of fruits on trees.
     */
    public static final float FRUIT_SIZE = BLOCK_SIZE * 0.7f;
    /**
     * The time interval for fruit respawn on trees.
     */
    public static final int FRUIT_RESPAWN = 30;
    /**
     * The color of the first type of fruit.
     */
    public static final Color FRUIT_COLOR_1 = Color.RED;
    /**
     * The color of the second type of fruit.
     */
    public static final Color FRUIT_COLOR_2 = Color.YELLOW;

    // spawn probabilities:
    /**
     * The probability of spawning a tree.
     */
    public static final double TREE_PROBABILITY = 0.069;
    /**
     * The probability of spawning a leaf on a tree.
     */
    public static final double LEAF_PROBABILITY = 0.69;
    /**
     * The probability of spawning a fruit on a tree.
     */
    public static final double FRUIT_PROBABILITY = LEAF_PROBABILITY + 0.15;

    // animation:
    /**
     * The time interval between animation clips.
     */
    public static final double TIME_BETWEEN_CLIPS = 0.2;

    // util constants
    /**
     * Integer constant representing the value 1.
     */
    public static final int ONE = 1;
    /**
     * Float constant representing the value 0.2.
     */
    public static final float f0_2 = 0.2f;
    /**
     * Float constant representing the value 0.5.
     */
    public static final float HALF = 0.5f;
    /**
     * Float constant representing the value 2/3.
     */
    public static final float TWO_THIRDS = 2f/3f;
    /**
     * Float constant representing the value 0.8.
     */
    public static final float f0_8 = 0.8f;
    /**
     * Float constant representing the value 1.2.
     */
    public static final float f1_2 = 1.2f;
    /**
     * Float constant representing the value 3.
     */
    public static final float f3 = 3f;
    /**
     * Float constant representing the value 7.
     */
    public static final float f7 = 7f;
    /**
     * Float constant representing the value 90.
     */
    public static final float f90 = 90f;
    /**
     * Float constant representing the value 360.
     */
    public static final float CIRCLE = 360f;
}
