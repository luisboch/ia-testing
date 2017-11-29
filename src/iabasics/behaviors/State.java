/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.behaviors;

import iabasics.objs.BasicObject;

/**
 *
 * @author carlos.boch
 */
public interface State {

    /**
     * Executed when state starts
     * @param obj
     */
    default void start(BasicObject obj) {
    }

    /**
     * Update the logics and, when the state must be changed: return the new
     * state; when this state was finished returns null (and then, the object
     * will back to last state, using FILO); when nothing happens return this/
     * the same instance;
     *
     * @param obj
     * @return
     */
    public State update(BasicObject obj);

    @FunctionalInterface
    public interface Handler<E extends BasicObject> {

        State action(E found);
    }

}
