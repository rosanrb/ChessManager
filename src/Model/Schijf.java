/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ootje
 */
public class Schijf {
    private boolean isDam;
    
    public Schijf()
    {
        isDam = false;
    }
    
    public void setDam(boolean maakDam)
    {
        isDam = maakDam;
    }
    
    public boolean getSchijfIsDam()
    {
        return isDam;
    }
}
