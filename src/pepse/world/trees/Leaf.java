package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static pepse.util.Constants.*;
import static danogl.components.Transition.TransitionType.TRANSITION_BACK_AND_FORTH;

/**
 * Leaf class represents a leaf object in the game world.
 * It extends GameObject and provides wind effects and animations.
 */
public class Leaf extends GameObject {
    private int stretch = ONE;

    /**
     * Constructs a Leaf object with the specified position, size, and renderable.
     * @param topLeftCorner The position of the top-left corner of the leaf
     * @param size The size of the leaf
     * @param renderable The renderable representing the appearance of the leaf
     */
    public Leaf(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(LEAF_TAG);

        windEffect();
    }

    /**
     * Applies wind effects to the leaf, including wiggling and stretching transitions.
     */
    private void windEffect() {
        new ScheduledTask(
                this,
                rand.nextFloat(),
                false,
                this::wiggleTransition);
        new ScheduledTask(
                this,
                rand.nextFloat(),
                true,
                this::stretchTransition);
    }

    /**
     * Initiates a wiggling transition for the leaf's angle.
     */
    private void wiggleTransition(){
        new Transition<>(this,
                this.renderer()::setRenderableAngle,
                this.renderer().getRenderableAngle() - f7, // range of movement
                this.renderer().getRenderableAngle() + f7,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                1f,
                TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * Initiates a stretching transition for the leaf's size.
     */
    private void stretchTransition(){
        new Transition<>(this,
                this::setDimensions,
                this.getDimensions(),
                this.getDimensions().add(Vector2.ONES.mult(stretch * f3)), // range of enlargement
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                ONE,
                TRANSITION_BACK_AND_FORTH,
                null);
        stretch *= -ONE;
    }

    /**
     * Rotates the leaf by 90 degrees.
     */
    public void rotate90Degree(){
        new Transition<>(
                this,
                this.renderer()::setRenderableAngle,
                this.renderer().getRenderableAngle(),
                this.renderer().getRenderableAngle() + f90,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                ONE,
                Transition.TransitionType.TRANSITION_ONCE,
                null);
    }
}
