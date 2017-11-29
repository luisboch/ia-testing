/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.objs;

import java.awt.Color;

/**
 *
 * @author luis
 */
public class Food extends BasicObject{

    public Food() {
        super(Color.ORANGE);
        width = 2;
        height = 2;
        walkVel = 0;
        runVel = 0;
                
    }
    
}
