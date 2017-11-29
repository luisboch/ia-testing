/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iabasics;

/**
 *
 * @author luis
 */
public class UID {
    private static long CURRENT = 1;
    public static synchronized long next(){
        return CURRENT++;
    }
}
