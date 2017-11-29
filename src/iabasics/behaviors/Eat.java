/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.behaviors;

import iabasics.objs.BasicObject;
import iabasics.objs.Food;
import iabasics.Physics;
import iabasics.World;
import java.awt.Color;

/**
 *
 * @author luis
 */
public class Eat implements State {

    private float colorChangeTime, changedTime = 0.5f;

    private long lastTime = 0;

    private final float timeToEat = 3f;
    private Food toEat;
    private Handler afterEat;

    public Eat() {
    }

    public Eat setToEat(Food toEat) {
        this.toEat = toEat;
        this.lastTime = System.currentTimeMillis();
        return this;
    }

    public Eat setAfterEat(Handler afterEat) {
        this.afterEat = afterEat;
        return this;
    }

    @Override
    public State update(BasicObject obj) {

        changedTime -= Physics.deltaTime;

        if (((System.currentTimeMillis() - lastTime) / 1000) > timeToEat) {
            World.getInstance().remove(toEat);
            obj.setColor(obj.getDefaultColor());
            return afterEat == null ? null : afterEat.action(null);
        }

        if (changedTime < 0f) {
            changedTime = colorChangeTime;
            if (obj.getColor().equals(obj.getDefaultColor())) {
                obj.setColor(obj.getBlinkColor());
            } else {
                obj.setColor(obj.getDefaultColor());
            }
        }

        obj.getVelocity().set(0, 0);

        return this;

    }

}
