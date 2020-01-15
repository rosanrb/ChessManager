/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.Dambord;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

/**
 *
 * @author ootje
 */
public class BordCompleetPanel extends JPanel{
    private final int marge = 16;
    private Controller controller;
    private Dambord bord;
    private DiagramPanel diagram;
    private ButtonPanel buttons;
    private BewerkPanel bewerk;
    
    public BordCompleetPanel(Dambord dambord, Controller controller)
    {
        this.setLayout(new GridBagLayout());
        this.setOpaque(true);
        this.setVisible(true);
        bord = dambord;
        //controller.setBeginopstelling(bord);
        diagram = new DiagramPanel(bord, controller, this);
        buttons = new ButtonPanel(controller, this);
        bewerk = new BewerkPanel(controller, this); 
        addPanelen();
        this.controller = controller;
    }
/**    
    public void addDiagramPanel(DiagramPanel bordpanel)
    {
        diagram = bordpanel;
    }
    
    public void addButtons(ButtonPanel buttonpanel)
    {
        buttons = buttonpanel;
    }
    
    public void addBewerkpanel(BewerkPanel bewerkpanel)
    {
        bewerk = bewerkpanel;
    }
*/    
    public BewerkPanel getBewerkPanel()
    {
        return bewerk;
    }
    
    public void updatePanels()
    {
        this.removeAll();
        diagram = new DiagramPanel(bord, controller, this);
        buttons = new ButtonPanel(controller, this);
        bewerk = new BewerkPanel(controller, this);
        bewerk.setBeschrijving(bord.getBeschrijving());
        bewerk.setFenWit(controller.bepaalWFEN(bord));
        bewerk.setFenZwart(controller.bepaalZFEN(bord));
        addPanelen();
    }
    
    public Dambord getBord()
    {
        return bord;
    }
    
    public void addPanelen()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(16, 16, 0, 0);
        this.add(diagram, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(16, 16, 16, 16);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(buttons, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 16, 16, 16);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        this.add(bewerk, gbc);
        this.setSize(bewerk.getWidth() + 32, diagram.getHeight() + bewerk.getHeight());
    }
}
