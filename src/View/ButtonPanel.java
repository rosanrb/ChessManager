/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author ootje
 */
public class ButtonPanel extends JPanel implements MouseListener{
    private JButton emptyboard;
    private JButton initialposition;
    private Controller controller;
    private BordCompleetPanel panel;
    
    public ButtonPanel(Controller controller, BordCompleetPanel panel)
    {
        Font font = new Font("Dialog", 10, 10);
        emptyboard = new JButton("Empty board");
        emptyboard.setFont(font);
        emptyboard.setPreferredSize(new Dimension(160, 16));
        emptyboard.setMaximumSize(new Dimension(160, 16));
        emptyboard.setOpaque(true);
        emptyboard.setVisible(true);
        initialposition = new JButton("Initial position");
        initialposition.setFont(font);
        initialposition.setPreferredSize(new Dimension(23*10, 16));
        initialposition.setMaximumSize(new Dimension(23*10, 16));
        initialposition.setOpaque(true);
        initialposition.setVisible(true);
        emptyboard.addMouseListener(this);
        initialposition.addMouseListener(this);
        this.setLayout(new GridLayout(2, 1, 0, 16));
        this.add(emptyboard);
        //this.add(Box.createRigidArea(new Dimension(160, 16)));
        this.add(initialposition);
        this.setMaximumSize(new Dimension(23*10, 48));
        this.setOpaque(true);
        this.setVisible(true);
        this.controller = controller;
        this.panel = panel;
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(me.getComponent().equals(emptyboard))
        {
            System.out.println("empty");
            controller.emptyBoard(panel, panel.getBord());
        }
        else if(me.getComponent().equals(initialposition))
        {
            System.out.println("initial");
            controller.initialPosition(panel, panel.getBord());
        }
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
