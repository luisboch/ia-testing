/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics;

import iabasics.behaviors.Attack;
import iabasics.behaviors.Eat;
import iabasics.behaviors.Follow;
import iabasics.objs.Wolf;
import iabasics.objs.BasicObject;
import iabasics.objs.Food;
import iabasics.objs.Human;
import iabasics.behaviors.Search;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.List;
import java.util.Random;

/**
 *
 * @author carlos.boch
 */
public class Game implements Screen {

    private static final Random rdn = new Random();

    public static World world = World.getInstance();
    private static Window window;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        window = new Window();
        window.setScreen(new Game());
        window.setVisible(true);
    }

    public Game() {
//        fixedObjs();
        create(10, 30, 40);
    }

    @Override
    public void draw(Graphics g) {
        world.getObjects().forEach((obj) -> {
            obj.draw(g);
        });
    }

    private BasicObject createEnemy() {
        Human obj = new Human();

        final float enemyFollowVel = obj.getRunVel();
        final float viewLimit = obj.getViewLimit();

        final Attack attack = new Attack();
        final Search search = new Search();
        final Follow follow = new Follow();

        obj.getPosition().set(new Vector2(500, 350));
        obj.getVelocity().add(new Vector2(0, 1));
        obj.getDirection().set(new Vector2(obj.getVelocity()).normalize());

        obj.setCurrentState(search.config(Wolf.class, (a) -> {
            return follow.config((n) -> {
                return attack.config(n, 2);
            }, a, enemyFollowVel, viewLimit);
        }));

        return obj;
    }

    private BasicObject createWolf() {

        final Wolf obj = new Wolf();

        final float objFollowVel = obj.getRunVel();
        final float viewLimit = obj.getViewLimit();

        final Attack attack = new Attack();
        final Eat eating = new Eat();
        final Follow follow = new Follow();
        final Search search = new Search();

        search.config(new Search.Config(
                Food.class, (a) -> {
                    return follow.config((f) -> {
                        if (f instanceof Food) {
                            return eating.setToEat((Food) f).setAfterEat((s) -> {
                                return search;
                            });
                        } else {
                            return null;
                        }
                    }, a, objFollowVel, viewLimit);
                }
        ), new Search.Config(Human.class, (e) -> {
            final Follow howl = new Follow();

            howl.config((e2) -> {
                return attack.config(e2, 1);
            }, e, objFollowVel, true);

            final List<Wolf> found = world.findSphere(obj.getPosition(), obj.getViewLimit() * 3, Wolf.class);
            Log.info("Howl to: " + found);
            // howl for another wolfs
            for (Wolf f : found) {
                if (f != obj) {
                    f.setCurrentState(howl);
                }
            }
            return howl;
        })
        );

        obj.setCurrentState(search);

        return obj;
    }

    private Food createFood() {
        final Food obj = new Food();
        obj.getVelocity().set(0, 0);
        obj.getDirection().set(1, 1);
        return obj;
    }

    private void fixedObjs() {

        BasicObject obj = createEnemy();
        world.add(obj);

        obj = createWolf();
        obj.getPosition().set(new Vector2(500, 250));
        obj.getVelocity().add(new Vector2(0, -15));
        obj.getDirection().set(new Vector2(obj.getVelocity()).normalize());
        world.add(obj);

        obj = createWolf();
        obj.getPosition().set(new Vector2(400, 250));
        obj.getVelocity().add(new Vector2(-10, -15));
        obj.getDirection().set(new Vector2(obj.getVelocity()).normalize());
        world.add(obj);

        obj = createWolf();
        obj.getPosition().set(new Vector2(400, 350));
        obj.getVelocity().add(new Vector2(-10, -15));
        obj.getDirection().set(new Vector2(obj.getVelocity()).normalize());
        world.add(obj);

        obj = createWolf();
        obj.getPosition().set(new Vector2(30, 30));
        obj.getVelocity().set(new Vector2(1, 1));
        obj.getDirection().set(new Vector2(obj.getVelocity()).normalize());
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(50, 50));
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(150, 200));
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(300, 150));
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(400, 450));
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(600, 200));
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(620, 550));
        world.add(obj);

        obj = createFood();
        obj.getPosition().set(new Vector2(800, 150));

        world.add(obj);
    }

    private Vector2 newRamdonPos() {
        Dimension size = window.getSize();
//        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        return new Vector2(rdn.nextFloat() * size.width, rdn.nextFloat() * size.height);
    }

    private void create(int h, int w, int f) {
        
        BasicObject obj;
        for (int i = 0; i < h; i++) {
            obj = createEnemy();
            obj.getPosition().set(newRamdonPos());
            world.add(obj);
        }
        
        for (int i = 0; i < w; i++) {
            obj = createWolf();
            obj.getPosition().set(newRamdonPos());
            world.add(obj);
        }
        
        for (int i = 0; i < f; i++) {
            obj = createFood();
            obj.getPosition().set(newRamdonPos());
            world.add(obj);
        }

    }
}
