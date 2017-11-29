/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics;

import iabasics.objs.BasicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author luis
 */
public class World {

    private static World instance;

    private final List<BasicObject> objects = new ArrayList<>();
    private final List<BasicObject> toRemove = new ArrayList<>();

    // Singleton
    private World() {
    }

    public static World getInstance() {

        if (instance == null) {
            instance = new World();
        }

        return instance;
    }

    public World add(BasicObject... obj) {
        if (obj == null || obj.length == 0) {
            return this;
        }

        for (BasicObject ob : obj) {
            if (ob == null) {
                continue;
            }
            objects.add(ob);
            ob.getVelocity().set(ob.getDirection());
            ob.getVelocity().normalize().mul(ob.getWalkVel());

        }
        return this;
    }

    public World remove(BasicObject... obj) {
        toRemove.addAll(Arrays.asList(obj));
        return this;
    }

    public <E> List<E> findSphere(Vector2 pos, float radius, Class<E> type) {
        return lookFor(pos, null, 0f, radius, type);
    }

    public <E> List<E> lookFor(Vector2 pos, Vector2 dir, float viewAngle, float viewDist, Class<E> type) {
        List<Class> types = new ArrayList<>();
        types.add(type);
        final List<BasicObject> lookFor = lookFor(pos, dir, viewAngle, viewDist, types);
        final List<E> collected = new ArrayList<>(lookFor.size());

        for (BasicObject b : lookFor) {
            collected.add((E) b);
        }

        return collected;

    }

    public List<BasicObject> lookFor(Vector2 pos, Vector2 dir, float viewAngle, float viewDist, List<Class> types) {
        pos = new Vector2(pos);
        
        if (dir != null) {
            dir = new Vector2(dir);
        }

        List<BasicObject> found = new ArrayList<>();

        for (BasicObject obj : objects) {
            if (isType(obj, types) && obj.getPosition().distance(pos) <= viewDist && isVisible(pos, dir, viewAngle, obj.getPosition())) {
                found.add(obj);
            }
        }

        return found;
    }

    private boolean isType(BasicObject obj, List<Class> types) {

        if (obj == null || types == null || types.size() == 0) {
            return false;
        }

        for (Class c : types) {
            if (c != null && c.equals(obj.getClass())) {
                return true;
            }
        }

        return false;
    }

    public void update() {

        // Kill all removed objects
        toRemove.forEach((a) -> {
            a.setAlive(false);
        });

        objects.removeAll(toRemove);
        toRemove.clear();

        for (Iterator<BasicObject> iterator = objects.iterator(); iterator.hasNext();) {
            BasicObject next = iterator.next();
            if (!next.isAlive()) {
                toRemove.add(next);
            } else {
                next.update();
            }
        }
    }

    public List<BasicObject> getObjects() {
        return objects;
    }

    private boolean isVisible(Vector2 pos, Vector2 dir, float viewAngle, Vector2 target) {

        if (dir == null || pos.distance(target) < 15f) {
            return true;
        }

        Vector2 dif = new Vector2(pos).sub(target).normalize();
        float difAng = dif.normalize().sub(new Vector2(dir).normalize()).length();
        return difAng < (viewAngle / 180);
    }

}
