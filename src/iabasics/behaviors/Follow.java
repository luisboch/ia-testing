/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.behaviors;

import iabasics.Vector2;
import iabasics.behaviors.State;
import iabasics.objs.BasicObject;
import iabasics.objs.BasicObject;

/**
 *
 * @author luis
 * @param <E>
 */
public class Follow<E extends BasicObject> implements State {

    private Handler<E> nextAction;
    private float arriveDist = 5f;
    private E target;
    private float velocity;
    private float distanceLimit;
    private boolean ignoreDistanceLimit = false;
    
    public Follow() {
    }

    public Follow config(Handler<E> whenArrive, E target, float velocity, float distanceLimit) {
        this.nextAction = whenArrive;
        this.target = target;
        this.velocity = velocity;
        this.distanceLimit = distanceLimit;
        return this;
    }

    public Follow config(Handler<E> whenArrive, E target, float velocity, boolean ignoreDistanceLimit) {
        this.nextAction = whenArrive;
        this.target = target;
        this.velocity = velocity;
        this.ignoreDistanceLimit = ignoreDistanceLimit;
        return this;
    }

    @Override
    public State update(BasicObject obj) {
        
        obj.setColor(obj.getRunColor());
        if (target == null || !target.isAlive() || (!ignoreDistanceLimit && target.getPosition().distance(obj.getPosition()) > distanceLimit)) {
            // Target is dead or lost
            return null;
        } else if (obj.getPosition().distance(target.getPosition()) < arriveDist) {
            return nextAction == null ? null : nextAction.action(target);
        }

        final Vector2 dir = new Vector2(obj.getPosition()).sub(target.getPosition()).normalize();
        
        obj.getDirection().set(dir);
        obj.getVelocity().set(dir).mul(obj.getRunVel());

        return this;
    }

}
