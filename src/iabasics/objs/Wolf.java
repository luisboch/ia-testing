/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics.objs;

import java.awt.Color;

/**
 *
 * @author carlos.boch
 */
public class Wolf extends BasicObject{

    public Wolf() {
        super(Color.BLUE);
        width = 5;
        height = 5;
        viewLimit = 80;
    }
    
}
