/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import Model.BruinVeld;
import Model.Dambord;
import Model.Veld;
import Model.WitVeld;
import Model.WitteSchijf;
import Model.ZwarteSchijf;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ootje
 */
public class DiagramPanel extends JPanel implements MouseListener {
    Controller controller;
    Dambord bord;
    BordCompleetPanel panel; 
    
    public DiagramPanel(Dambord bord, Controller controller, BordCompleetPanel panel)
    {
        this.controller = controller;
        this.bord = bord;
        this.panel = panel;
        this.setLayout(new GridLayout(10, 10));
        for(int r = 0; r < 10; r++)
        {
            for(int c = 0; c < 10; c++)
            {
                ImageIcon image = null;
                Veld huidigveld = bord.getVeld(c, r);
                if(huidigveld instanceof BruinVeld)
                {
                    if(((BruinVeld) huidigveld).getSchijf() != null)
                    {
                        if(((BruinVeld) huidigveld).getSchijf().getSchijfIsDam() == true)
                        {
                            if(((BruinVeld) huidigveld).getSchijf() instanceof ZwarteSchijf)
                            {
                                image = new ImageIcon(getClass().getResource("/View/images/bk.gif"));  
                            }
                            else if(((BruinVeld) huidigveld).getSchijf() instanceof WitteSchijf)
                            {
                                image = new ImageIcon(getClass().getResource("/View/images/wk.gif"));  
                            }
                        }
                        else if(((BruinVeld) huidigveld).getSchijf().getSchijfIsDam() == false)
                        {
                            if(((BruinVeld) huidigveld).getSchijf() instanceof ZwarteSchijf)
                            {
                                image = new ImageIcon(getClass().getResource("/View/images/bm.gif"));  
                            }
                            else if(((BruinVeld) huidigveld).getSchijf() instanceof WitteSchijf)
                            {
                                image = new ImageIcon(getClass().getResource("/View/images/wm.gif"));  
                            }
                        }
                    }
                    else if(((BruinVeld) huidigveld).getSchijf() == null)
                    {
                        image = new ImageIcon(getClass().getResource("/View/images/sqb.gif"));
                    }
                }
                else if(huidigveld instanceof WitVeld)
                {
                    image = new ImageIcon(getClass().getResource("/View/images/sqw.gif"));
                }
                JLabel label = new JLabel(image);
                label.setBackground(Color.red);
                label.setOpaque(true);
                label.setVisible(true);
                label.addMouseListener(this);
                this.add(label);
            }
        }
        this.setOpaque(true);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        // bepaal vakje 23 pixel
        int vakjeXarray = me.getComponent().getX() / 23;
        int vakjeYarray = me.getComponent().getY() / 23;
        controller.klikVakje(vakjeXarray, vakjeYarray, me.isShiftDown(), bord, panel);
    }

    @Override
    public void mousePressed(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
