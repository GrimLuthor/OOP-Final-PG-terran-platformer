package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.util.function.Consumer;

public class Trunk extends GameObject {
    private Consumer<GameObject> operation;
    public Trunk(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(Constants.TRUNK_TAG);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        operation.accept(this);
    }

    public void setOperation(Consumer<GameObject> operation) {
        this.operation = operation;
    }
}
