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
public class BruinVeld extends Veld{
    private int veldNummer;
    private Schijf schijf;
    
    public BruinVeld()
    {
        
    }
    
    public void setVeldNummer(int nummer)
    {
        veldNummer = nummer;
    }
    
    public int getVeldNummer()
    {
        return veldNummer;
    }
    
    public void setSchijf(Schijf plaatsSchijf)
    {
        schijf = plaatsSchijf;
    }
    
    public Schijf getSchijf()
    {
        if(bevatSchijf() == true)
        {
            return schijf;
        }
        else
        {
            //System.out.println("Dit veld bevat geen schijf");
            return null;
        }
    }
    
    public boolean bevatSchijf()
    {
        if(schijf != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
