/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.objs;

import iabasics.behaviors.Attack;
import java.awt.Color;

/**
 *
 * @author carlos.boch
 */
public class Human extends BasicObject {

    public Human() {
        super(Color.RED);
        width = 7;
        height = 7;
    }

    @Override
    public void setLife(int life) {
        super.setLife(life); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public BasicObject receiveAttack(BasicObject from) {
        setCurrentState(new Attack().config(from, 2));
        return this;
    }
    
    

}
