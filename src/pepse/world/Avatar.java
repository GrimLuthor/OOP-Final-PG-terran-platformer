package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.AvatarMovement;

import java.awt.event.KeyEvent;

import static pepse.util.Constants.*;
/**
 * Avatar class represents the player's character in the game.
 * It extends GameObject and handles avatar movement, animations, and energy management.
 */
public class Avatar extends GameObject {
    private final UserInputListener inputListener;
    private final ImageReader imageReader;

    private float energy = ENERGY_POINTS_MAX;

    private AvatarMovement movementMode = AvatarMovement.IDLE;

    private AnimationRenderable idleAnimation;
    private AnimationRenderable jumpAnimation;
    private AnimationRenderable runAnimation;

    /**
     * Constructs an Avatar object with the specified position, input listener, and image reader.
     * @param pos The initial position of the avatar
     * @param inputListener The user input listener for controlling the avatar
     * @param imageReader The image reader for loading avatar resources
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, AVATAR_DIMENSIONS_IDLE,
                imageReader.readImage("assets/idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.setTag(AVATAR_TAG);
    }

    /**
     * Updates the avatar's state based on user input and energy level.
     * @param deltaTime The time elapsed since the last update
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        processJumping();
        processWalking();
    }

    /**
     * Processes avatar movement based on user input.
     */
    private void processWalking() {
        float xVel = 0;

        if (energy >= HALF && (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) ^
                inputListener.isKeyPressed(KeyEvent.VK_LEFT))) {
            xVel = processMovementInDirection();
        }

        transform().setVelocityX(xVel);

        if (getVelocity().x() != 0) {
            energy -= HALF;
        } else if (isOnGround() && inputListener.isKeyPressed(KeyEvent.VK_LEFT)
                == inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            energy = Math.min(energy + ONE, ENERGY_POINTS_MAX);
            movementMode = AvatarMovement.IDLE;
            setAnimationIdle();
        }

        if (energy == 0 && isOnGround()) {
            movementMode = AvatarMovement.IDLE;
            setAnimationIdle();
        }
    }

    /**
     * Processes avatar movement left or right based on user input.
     */
    private float processMovementInDirection() {
        if (movementMode == AvatarMovement.IDLE) {
            movementMode = AvatarMovement.RUNNING;
            setAnimationRun();
        }

        float xVel = 0;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= VELOCITY_X;
            renderer().setIsFlippedHorizontally(true);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += VELOCITY_X;
            renderer().setIsFlippedHorizontally(false);
        }

        return xVel;
    }

    /**
     * Processes avatar jumping based on user input and energy level.
     */
    private void processJumping() {
        if (energy >= ENERGY_POINTS_MIN && inputListener.isKeyPressed(KeyEvent.VK_SPACE) && isOnGround()) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= ENERGY_POINTS_MIN;
            movementMode = AvatarMovement.JUMPING;
            setAnimationJump();
        }
    }

    /**
     * Sets the avatar animation to idle state.
     */
    private void setAnimationIdle() {
        if (idleAnimation == null) {
            idleAnimation = new AnimationRenderable(
                    new Renderable[]{
                            imageReader.readImage("assets/idle_0.png", true),
                            imageReader.readImage("assets/idle_1.png", true),
                            imageReader.readImage("assets/idle_2.png", true),
                            imageReader.readImage("assets/idle_3.png", true)
                    },
                    TIME_BETWEEN_CLIPS
            );
        }
        renderer().setRenderable(idleAnimation);
        setDimensions(AVATAR_DIMENSIONS_IDLE);
    }

    /**
     * Sets the avatar animation to jump state.
     */
    private void setAnimationJump() {
        if (jumpAnimation == null) {
            jumpAnimation = new AnimationRenderable(
                    new Renderable[]{
                            imageReader.readImage("assets/jump_0.png", true),
                            imageReader.readImage("assets/jump_1.png", true),
                            imageReader.readImage("assets/jump_2.png", true),
                            imageReader.readImage("assets/jump_3.png", true)
                    },
                    TIME_BETWEEN_CLIPS
            );
        }
        renderer().setRenderable(jumpAnimation);
        setDimensions(AVATAR_DIMENSIONS_JUMP);
    }

    /**
     * Sets the avatar animation to run state.
     */
    private void setAnimationRun() {
        if (runAnimation == null) {
            runAnimation = new AnimationRenderable(
                    new Renderable[]{
                            imageReader.readImage("assets/run_0.png", true),
                            imageReader.readImage("assets/run_1.png", true),
                            imageReader.readImage("assets/run_2.png", true),
                            imageReader.readImage("assets/run_3.png", true),
                            imageReader.readImage("assets/run_4.png", true),
                            imageReader.readImage("assets/run_5.png", true)
                    },
                    TIME_BETWEEN_CLIPS
            );
        }
        renderer().setRenderable(runAnimation);
        setDimensions(AVATAR_DIMENSIONS_RUN);
    }

    /**
     * Checks if the avatar is on the ground.
     * @return True if the avatar is on the ground, false otherwise
     */
    private boolean isOnGround() {
        return getVelocity().y() == 0;
    }

    /**
     * Gets the current energy count of the avatar.
     * @return The current energy count
     */
    public float getEnergyCount() {
        return energy;
    }

    /**
     * Sets the energy count of the avatar.
     * @param energy The energy count to set
     */
    public void setEnergyCount(float energy) {
        this.energy = energy > ENERGY_POINTS_MAX ? ENERGY_POINTS_MAX : energy;
    }

    /**
     * Gets the current movement mode of the avatar.
     * @return The current movement mode
     */
    public AvatarMovement getMovementMode() {
        return movementMode;
    }
}
