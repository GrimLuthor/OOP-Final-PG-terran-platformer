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

public class Avatar extends GameObject {
    private final UserInputListener inputListener;
    private final ImageReader imageReader;

    private float energy = ENERGY_POINTS_MAX;

    private AvatarMovement movementMode = AvatarMovement.IDLE;

    private AnimationRenderable idleAnimation;
    private AnimationRenderable jumpAnimation;
    private AnimationRenderable runAnimation;

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, AVATAR_DIMENSIONS_IDLE,
                imageReader.readImage("assets/idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.setTag(AVATAR_TAG);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        processJumping();
        processWalking();
    }

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

    private void processJumping() {
        if (energy >= ENERGY_POINTS_MIN && inputListener.isKeyPressed(KeyEvent.VK_SPACE) && isOnGround()) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= ENERGY_POINTS_MIN;
            movementMode = AvatarMovement.JUMPING;
            setAnimationJump();
        }
    }

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

    private boolean isOnGround() {
        return getVelocity().y() == 0;
    }

    public float getEnergyCount() {
        return energy;
    }

    public void setEnergyCount(float energy) {
        this.energy = energy > ENERGY_POINTS_MAX ? ENERGY_POINTS_MAX : energy;
    }

    public AvatarMovement getMovementMode() {
        return movementMode;
    }
}
