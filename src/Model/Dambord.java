/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import View.BordCompleetPanel;

/**
 *
 * @author ootje
 */
public class Dambord {
    private final int aantalVelden = 100;
    private Veld[][] velden;
    private String beschrijving;
    private BordCompleetPanel panel;
    
    public Dambord(Controller controller)
    {
        velden = new Veld[10][10];
        panel = new BordCompleetPanel(this, controller);
    }
    
    public void setVeld(int veldrij, int veldcolom, Veld veld)
    {
        velden[veldrij][veldcolom] = veld;
    }
    
    public Veld getVeld(int veldrij, int veldcolom)
    {
        return velden[veldrij][veldcolom];
    }
    
    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving = beschrijving;
    }

    public String getBeschrijving()
    {
        return beschrijving;
    }
    
    public BordCompleetPanel getPanel()
    {
        return panel;
    }
}
