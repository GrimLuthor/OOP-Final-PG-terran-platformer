package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.util.function.Consumer;

import static danogl.components.Transition.TransitionType.TRANSITION_BACK_AND_FORTH;


public class Leaf extends GameObject {
    private Consumer<GameObject> operation;
    private int stretch = 1;

    public Leaf(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(Constants.LEAF_TAG);

        windEffect();
    }

    private void windEffect() {
        new ScheduledTask(
                this,
                Constants.rand.nextFloat(),
                false,
                this::wiggleTransition);
        new ScheduledTask(
                this,
                Constants.rand.nextFloat(),
                true,
                this::stretchTransition);
    }

    private void wiggleTransition(){
        new Transition<>(this,
                this.renderer()::setRenderableAngle,
                this.renderer().getRenderableAngle() - 7f, // range of movement
                this.renderer().getRenderableAngle() + 7f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                1f,
                TRANSITION_BACK_AND_FORTH,
                null);
    }

    private void stretchTransition(){
        new Transition<>(this,
                this::setDimensions,
                this.getDimensions(),
                this.getDimensions().add(Vector2.ONES.mult(stretch * 3f)), // range of enlargement
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                1f,
                TRANSITION_BACK_AND_FORTH,
                null);
        stretch *= -1;
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        operation.accept(this);
    }

    public void setOperation(Consumer<GameObject> operation) {
        this.operation = operation;
    }
}
