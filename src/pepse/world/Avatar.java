package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.AvatarMovement;
import pepse.util.Constants;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 1000;

    private float energy = 100f;

    private AvatarMovement movementMode = AvatarMovement.IDLE;


    private AnimationRenderable idleAnimation;
    private AnimationRenderable jumpAnimation;
    private AnimationRenderable runAnimation;


    private final UserInputListener inputListener;
    private final ImageReader imageReader;

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, new Vector2(50, 78), imageReader.readImage("assets/idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.imageReader = imageReader;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        processJumping();
        processWalking();
    }

    private void processWalking() {
        float xVel = 0;

        if (energy >= 0.5f && (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) ^ inputListener.isKeyPressed(KeyEvent.VK_LEFT))) {
            xVel = processMovementInDirection();
        }

        transform().setVelocityX(xVel);

        if (getVelocity().x() != 0) {
            energy -= 0.5f;
        } else if (isOnGround() && inputListener.isKeyPressed(KeyEvent.VK_LEFT)
                == inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            energy = Math.min(energy + 1, 100);
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
        if (energy >= 10 && inputListener.isKeyPressed(KeyEvent.VK_SPACE) && isOnGround()) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= 10;
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
                    Constants.TIME_BETWEEN_CLIPS
            );
        }
        renderer().setRenderable(idleAnimation);
        setDimensions(Vector2.of(50,78));
    }

    private void setAnimationJump() {
        if (jumpAnimation == null) {
            jumpAnimation = new AnimationRenderable(
                    new Renderable[] {
                            imageReader.readImage("assets/jump_0.png", true),
                            imageReader.readImage("assets/jump_1.png", true),
                            imageReader.readImage("assets/jump_2.png", true),
                            imageReader.readImage("assets/jump_3.png", true)
                    },
                    Constants.TIME_BETWEEN_CLIPS
            );
        }
        renderer().setRenderable(jumpAnimation);
        setDimensions(Vector2.of(69,73));
    }

    private void setAnimationRun () {
        if (runAnimation == null) {
            runAnimation = new AnimationRenderable(
                    new Renderable[] {
                            imageReader.readImage("assets/run_0.png", true),
                            imageReader.readImage("assets/run_1.png", true),
                            imageReader.readImage("assets/run_2.png", true),
                            imageReader.readImage("assets/run_3.png", true),
                            imageReader.readImage("assets/run_4.png", true),
                            imageReader.readImage("assets/run_5.png", true)
                    },
                    Constants.TIME_BETWEEN_CLIPS
            );
        }
        renderer().setRenderable(runAnimation);
        setDimensions(Vector2.of(58,73));
    }

    private boolean isOnGround() {
        return getVelocity().y() == 0;
    }

    public float getEnergyCount() {
        return energy;
    }

    public AvatarMovement getMovementMode () {
        return movementMode;
    }
}
