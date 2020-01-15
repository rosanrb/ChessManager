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
public class Veld { 
    private Veld vrechts;
    private Veld vlinks;
    private Veld vonder;
    private Veld vboven;
    
    public Veld()
    {
        
    }
    
    public void setVrechts(Veld veld)
    {
        vrechts = veld;
    }
    public void setVlinks(Veld veld)
    {
        vlinks = veld;
    }
    public void setVonder(Veld veld)
    {
        vonder = veld;
    }
    public void setVboven(Veld veld)
    {
        vboven = veld;
    }
    
    public Veld getVrechts()
    {
        return vrechts;
    }
    public Veld getVlinks()
    {
        return vlinks;
    }
    public Veld getVonder()
    {
        return vonder;
    }
    public Veld getVboven()
    {
        return vboven;
    }
}
