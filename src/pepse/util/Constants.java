package pepse.util;

import danogl.collisions.Layer;
import danogl.util.Vector2;

import java.awt.*;

public class Constants {

    // seed:
    public static final int SEED = 4;

    // layers:
    public static final int SKY_LAYER = Layer.BACKGROUND;
    public static final int SUN_LAYER = -100;
    public static final int SUN_HALO_LAYER = -101;
    public static final int BLOCK_LAYER = 1;
    public static final int TREE_LAYER = 2;
    public static final int AVATAR_LAYER = 3;
    public static final int NIGHT_SHADOW_LAYER = 100;
    public static final int ENERGY_COUNTER_LAYER = Layer.UI;

    // dimensions:
    public static final int TERRAIN_CHUNK_SIZE = 2010;
    public static final int BLOCK_SIZE = 30;
    public static final int SUN_SIZE = 150;
    public static final int SUN_HALO_SIZE = 250;
    public static final Vector2 ENERGY_COUNTER_UI_DIMENSIONS = Vector2.of(100, 50);

    // positions:
    public static final float SUN_POS_HEIGHT_OFFSET = (1f/3f);
    public static final float TERRAIN_POS_OFFSET = (2f/3f);

    // tags:
    public static final String GROUND_TAG = "ground";
    public static final String NIGHT_TAG = "night";
    public static final String SKY_TAG = "sky";
    public static final String SUN_TAG = "sun";
    public static final String SUN_HALO_TAG = "sun halo";
    public static final String ENERGY_COUNTER_TAG = "energy counter";
    public static final String LEAF_TAG = "leaf";

    // colors:
    public static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    public static final Color SUN_HALO_COLOR = new Color(255, 255, 0, 20);
    public static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    public static final Color TRUNK_COLOR = new Color(100, 50, 20);
    public static final Color LEAF_COLOR = new Color(50, 200, 30);

    // terrain gen variables:
    public static final int TERRAIN_DEPTH = 20;
    public static final int TERRAIN_NOISE_FACTOR = 7;

    // night-day cycle variables:
    public static final float MIDNIGHT_OPACITY = 0.95f;
    public static final float NIGHT_DAY_CYCLE_INTERVAL = 100;
    public static final float SUN_CYCLE_INTERVAL = NIGHT_DAY_CYCLE_INTERVAL * 2;

    // energy variables:
    public static final int ENERGY_POINTS_MAX = 100;

    // tree variables:
    public static final int TREE_HEIGHT = 300;
    public static final int LEAVES_CROWN_SIZE = 8;

    // animation:
    public static final double TIME_BETWEEN_CLIPS = 0.1;
}
