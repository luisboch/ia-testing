/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.behaviors;

import iabasics.Vector2;
import iabasics.objs.BasicObject;
import java.util.Objects;

/**
 *
 * @author luis
 */
public class Attack implements State {

    private BasicObject target;
    private int force = 1;
    
    @Override
    public State update(BasicObject obj) {

        obj.setColor(obj.getAlertColor());
        
        if (target == null || !target.isAlive()) {
            obj.setColor(obj.getDefaultColor());
            return null;
        }

        obj.getVelocity().set(new Vector2(0, 0));
        target.setLife(target.getLife() - force);
        target.receiveAttack(obj);

        return this;
    }

    public Attack config(BasicObject target, int force) {
        this.target = target;
        this.force = force;
        return this;
    }

    public Attack setTarget(BasicObject target) {
        this.target = target;
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.target);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attack other = (Attack) obj;
        if (!Objects.equals(this.target, other.target)) {
            return false;
        }
        return true;
    }
}
