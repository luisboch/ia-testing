/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.objs;

import iabasics.Log;
import iabasics.Physics;
import iabasics.UID;
import iabasics.Vector2;
import iabasics.behaviors.Attack;
import iabasics.behaviors.State;
import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author carlos.boch
 */
public class BasicObject {

    private long id = UID.next();

    private boolean alive = true;

    protected Vector2 position = new Vector2();
    protected Vector2 direction = new Vector2();
    protected Vector2 velocity = new Vector2();

    protected int width, height, life;
    protected Color color = Color.YELLOW;
    protected Color blinkColor = Color.GREEN;
    protected Color defaultColor = Color.YELLOW;
    protected Color alertColor = Color.YELLOW;
    protected Color runColor = Color.ORANGE;

    protected final LinkedList<State> states = new LinkedList<>();
    protected State currentState;

    protected float runVel = 100f, walkVel = 50f, viewLimit = 30f;

    private static final int MAX_MEM_STATES = 3;

    public BasicObject(Color defaultColor) {
        color = defaultColor;
        this.defaultColor = defaultColor;
        this.life = 100;
    }

    public void setCurrentState(State currentState) {

        if (currentState == null) {
            this.currentState = null;
            return;
        }

        if (currentState.equals(this.currentState)) {
            return;
        }

        if (states.size() >= MAX_MEM_STATES) {
            states.remove(states.getFirst());
        }

        states.add(this.currentState);
        this.currentState = currentState;
        // Initialize the state.
        this.currentState.start(this);
        Log.info(getClass().getSimpleName() + "-> " + (currentState == null ? "<null>" : currentState.getClass().getSimpleName()));
    }

    public void finishCurrentState() {

        // No behavior?
        if (states.isEmpty()) {
            this.currentState = null;
            return;
        }

        states.remove(currentState);
        this.currentState = states.get(states.size() - 1);
        
        if (this.currentState != null) {
            this.currentState.start(this);
        }

        Log.info(getClass().getSimpleName() + " <- " + (currentState == null ? "<null>" : currentState.getClass().getSimpleName()));
    }

    public void draw(Graphics d) {

        int posX, posY;

        posX = (int) position.x + ((int) (width * 0.5f));
        posY = (int) position.y + ((int) (height * 0.5f));

        d.setColor(color);
        d.fillRect(posX, posY, width, height);
    }

    public void tick() {
    }

    public final void update() {

        // No behavior?
        if (currentState != null) {
            State next = this.currentState.update(this);

            // This state was finished?
            if (next == null) {
                finishCurrentState();
            } else if (next != this.currentState) {
                // The state was updated?
                // Then use the new state, and add to List
                setCurrentState(next);
            }
        }

        tick();

        this.position.add(new Vector2(velocity).mul(Physics.deltaTime));
    }

    private Color randomColor() {
        final Random rdn = new Random();
        int aux = (int) (Math.max(rdn.nextFloat(), 0.5f) * 150);
        return new Color(aux, aux, aux);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Color getColor() {
        return color;
    }

    public Vector2 getDirection() {
        return direction;
    }

    public float getRunVel() {
        return runVel;
    }

    public float getWalkVel() {
        return walkVel;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getAlertColor() {
        return alertColor;
    }

    public Color getBlinkColor() {
        return blinkColor;
    }

    public Color getRunColor() {
        return runColor;
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public boolean isAlive() {
        return alive && life > 0;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public float getViewLimit() {
        return viewLimit;
    }

    public BasicObject receiveAttack(BasicObject from) {
        this.setCurrentState(new Attack().setTarget(from));
        return this;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final BasicObject other = (BasicObject) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
