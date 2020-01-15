/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ootje
 */
public class BewerkPanel extends JPanel implements KeyListener{
    private JTextField beschrijving;
    private JPanel witpanel;
    private JLabel ww;
    private JTextField FENwit;
    private JPanel zwartpanel;
    private JLabel bb;
    private JTextField FENzwart;
    private JTextArea fouten;
    private Controller controller;
    private BordCompleetPanel panel;
    
    public BewerkPanel(Controller controller, BordCompleetPanel panel)
    {
        this.controller = controller;
        this.panel = panel;
        Font font = new Font("Dialog", 10, 10);
        beschrijving = new JTextField();
        beschrijving.setFont(font);
        //System.out.println(beschrijving.getFont().getFamily());
        //System.out.println(beschrijving.getFont().getSize());
        beschrijving.setPreferredSize(new Dimension(336, 16));
        beschrijving.setOpaque(true);
        beschrijving.setVisible(true);
        beschrijving.setText("");
        witpanel = new JPanel();
        witpanel.setOpaque(true);
        witpanel.setVisible(true);
        ww = new JLabel("W");
        ww.setFont(font);
        FENwit = new JTextField();
        FENwit.setFont(font);
        FENwit.setPreferredSize(new Dimension(470, 16));
        FENwit.setOpaque(true);
        FENwit.setVisible(true);
        witpanel.add(ww);
        witpanel.add(FENwit);
        zwartpanel = new JPanel();
        zwartpanel.setOpaque(true);
        zwartpanel.setVisible(true);
        bb = new JLabel("B");
        bb.setFont(font);
        FENzwart = new JTextField();
        FENzwart.setFont(font);
        FENzwart.setPreferredSize(new Dimension(470 , 16));
        FENzwart.setOpaque(true);
        FENzwart.setVisible(true);
        zwartpanel.add(bb);
        zwartpanel.add(FENzwart);
        fouten = new JTextArea();
        fouten.setFont(font);
        fouten.setPreferredSize(new Dimension(336, 48));
        fouten.setOpaque(true);
        fouten.setVisible(true);
        beschrijving.addKeyListener(this);
        FENwit.addKeyListener(this);
        FENzwart.addKeyListener(this);
        fouten.addKeyListener(this);
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(beschrijving);
        //this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(witpanel);
        //this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(zwartpanel);
        //this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(fouten);
        this.setOpaque(true);
        this.setVisible(true);
    }
    
    public void setBeschrijving(String beschrijving)
    {
        this.beschrijving.setText(beschrijving);
    }
    
    public String getBeschrijving()
    {
        return beschrijving.getText();
    }
    
    public void setFenWit(String FENnotatie)
    {
        FENwit.setText(FENnotatie);
    }
    
    public String getFenWit()
    {
        return FENwit.getText();
    }
    
    public void setFenZwart(String FENnotatie)
    {
        FENzwart.setText(FENnotatie);
    }
    
    public String getFenZwart()
    {
        return FENzwart.getText();
    }
    
    public void setFouten(String fout)
    {
        fouten.setText(fout);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println(ke.getKeyCode());
        if(ke.getKeyCode() == KeyEvent.VK_ENTER)
        {
            System.out.println("enter");
        }
        if(ke.getComponent().equals(beschrijving))
        {
            System.out.println("beschrijving");
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int key = ke.getKeyCode();
        if(key == KeyEvent.VK_ENTER)
        {
            if(ke.getComponent().equals(FENwit) || ke.getComponent().equals(FENzwart))
            {
                controller.updateFENbord(FENwit.getText(), FENzwart.getText(), beschrijving.getText(), panel.getBord(), panel);
            }
            else if(ke.getComponent().equals(beschrijving))
            {
                controller.setBewerk(panel.getBord(), panel, beschrijving.getText(), fouten.getText());
            }
        }
    }
}
