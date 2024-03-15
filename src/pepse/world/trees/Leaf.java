package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import static pepse.util.Constants.*;
import static danogl.components.Transition.TransitionType.TRANSITION_BACK_AND_FORTH;


public class Leaf extends GameObject {
    private int stretch = ONE;

    public Leaf(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(LEAF_TAG);

        windEffect();
    }

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
