/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.behaviors;

import iabasics.objs.BasicObject;
import iabasics.Vector2;
import iabasics.World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author luis
 */
public class Search implements State {

    private final List<Config> searchConfig = new ArrayList<>();

    private float chanceToChangeDir = 0.00002f;

    private Random rnd = new Random();

    @Override
    public void start(BasicObject obj) {
        obj.getDirection().set(newRandomDir());
        obj.getVelocity().set(obj.getDirection()).mul(obj.getWalkVel());
    }

    public <E extends BasicObject> Search config(Class<E> lookingType, Handler<E> doWhenLocate) {
        this.searchConfig.add(new Config(lookingType, doWhenLocate));
        return this;
    }

    public <E extends BasicObject> Search config(Config... searchConfig) {
        this.searchConfig.addAll(Arrays.asList(searchConfig));
        return this;
    }

    public Search clear() {
        this.searchConfig.clear();
        return this;
    }

    @Override
    public State update(BasicObject obj) {

        // just validation
        if (obj == null || !obj.isAlive()) {
            return null;
        }

        obj.setColor(obj.getDefaultColor());

        for (Config config : searchConfig) {
            List<BasicObject> found = World.getInstance().lookFor(obj.getPosition(), obj.getDirection(), 60f, obj.getViewLimit() * 0.5f, config.searchClass);

            Vector2 direction = new Vector2(obj.getDirection()).normalize();

            if (!found.isEmpty()) {
                BasicObject food = found.get(0);
                return config.doWhenLocate != null ? config.doWhenLocate.action(food) : null;
            } else if (rnd.nextFloat() < chanceToChangeDir) {
                obj.getDirection().set(newRandomDir());
                
                // newRandonDir returns a normalized vector.
                obj.getVelocity().set(obj.getDirection()).mul(obj.getWalkVel());
            }
        }

        return this;
    }

    private Vector2 newRandomDir() {
        return new Vector2(rnd.nextFloat() * (rnd.nextBoolean() ? -1 : 1), rnd.nextFloat() * (rnd.nextBoolean() ? -1 : 1)).normalize();
    }

    public static class Config<E extends BasicObject> {

        private final List<Class<E>> searchClass;
        private final Handler<E> doWhenLocate;

        public Config(Class<E> searchClass, Handler<E> doWhenLocate) {
            this.searchClass = new ArrayList<>();
            this.searchClass.add(searchClass);
            this.doWhenLocate = doWhenLocate;
        }

        public Config(List<Class<E>> searchClass, Handler<E> action) {
            this.searchClass = searchClass;
            this.doWhenLocate = action;
        }

        public Handler<E> getDoWhenLocate() {
            return doWhenLocate;
        }

        public List<Class<E>> getSearchClass() {
            return searchClass;
        }

    }

}
