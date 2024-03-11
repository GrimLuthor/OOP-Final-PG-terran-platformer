package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

public class Avatar extends GameObject {
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 1000;

    private float energy = 100f;

    private final UserInputListener inputListener;

    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos, new Vector2(50, 78), imageReader.readImage("assets/idle_0.png", true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        processWalking();

        processJumping();
    }

    private void processWalking() {
        float xVel = 0;
        if (energy >= 0.5f) {

            if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
                xVel -= VELOCITY_X;
            if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
                xVel += VELOCITY_X;

        }


        if (getVelocity().x() != 0) {
            energy -= 0.5f;
        } else if (energy < 100){
            energy += 1;
        }
        transform().setVelocityX(xVel);
    }

    private void processJumping() {

        if (energy < 10) return;

        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= 10;
        }
    }

    public float getEnergyCount() {
        return energy;
    }

}
