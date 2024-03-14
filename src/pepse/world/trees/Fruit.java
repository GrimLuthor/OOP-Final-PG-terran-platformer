package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.Constants;

import java.util.function.Consumer;

public class Fruit extends GameObject {
    private Consumer<GameObject> operation;
    public Fruit(Vector2 topLeftCorner, Vector2 size, Renderable renderable) {
        super(topLeftCorner, size, renderable);
        this.setTag(Constants.FRUIT_TAG);
    }

    public void update(float deltaTime) {
        super.update(deltaTime);
        operation.accept(this);
    }

    public void setOperation(Consumer<GameObject> operation) {
        this.operation = operation;
    }
}
