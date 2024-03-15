package pepse.world.trees;

import danogl.components.Component;
import danogl.util.Vector2;
import pepse.util.AvatarMovement;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static pepse.util.Constants.*;

public class Flora {
    private final Supplier<AvatarMovement> avatarMovement;
    private final Function<Float, Float> groundHeightAtX;
    public Flora(Function<Float, Float> groundHeightAtX, Supplier<AvatarMovement> avatarMovement) {
        this.groundHeightAtX = groundHeightAtX;
        this.avatarMovement = avatarMovement;
    }
    public Set<Tree> createInRange(int minX, int maxX) {
        Set<Tree> trees = new HashSet<>();
        for (float x = minX; x < maxX; x += BLOCK_SIZE) {
            if (rand.nextDouble() < TREE_PROBABILITY) {
                trees.add(createTree(x));
                x += BLOCK_SIZE;
            }
        }
        return trees;
    }

    private Tree createTree(float x) {
        float y = groundHeightAtX.apply((x));
        Tree tree = new Tree(Vector2.of(x, y));
        tree.addComponent(new Component() {
            private AvatarMovement previous = AvatarMovement.IDLE;
            private Color fruitColor = FRUIT_COLOR_1;
            @Override
            public void update(float v) {
                if (!previous.equals(AvatarMovement.JUMPING) &&
                        avatarMovement.get().equals(AvatarMovement.JUMPING)){
                    fruitColor = fruitColor.equals(FRUIT_COLOR_1) ? FRUIT_COLOR_2 : FRUIT_COLOR_1;
                    previous = AvatarMovement.JUMPING;
                    // change tree color
                    tree.getTrunk().changeColor(TRUNK_COLOR);
                    // rotate leaves
                    for (Leaf leaf : tree.getLeaves()) {
                        leaf.rotate90Degree();
                    }
                    // change fruit color
                    for (Fruit fruit : tree.getFruits()) {
                        fruit.changeColor(fruitColor);
                    }
                }
                else {
                    previous = avatarMovement.get();
                }
            }
        });
        return tree;
    }
}
