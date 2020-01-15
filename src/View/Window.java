/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Controller.Controller;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import static java.time.zone.ZoneRulesProvider.refresh;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

/**
 *
 * @author ootje
 */
public class Window extends JFrame implements ActionListener {
    private JMenuBar menubar;
    private JMenu mfile;
    private JMenu medit;
    private JMenu mextra;
    private JMenuItem mfnew;
    private JMenuItem mfopen;
    private JMenuItem mfsave;
    private JMenuItem mfsaveas;
    private JMenuItem mfrevert;
    private JMenuItem mfquit;
    private Controller controller;
    
    public Window(Controller controller)
    {
        this.controller = controller;
        menubar = new JMenuBar();
        mfile = new JMenu("File");
        medit = new JMenu("Edit");
        mextra = new JMenu("Extra");
        mfnew = new JMenuItem("New");
        mfopen = new JMenuItem("Open...");
        mfsave = new JMenuItem("Save");
        mfsaveas = new JMenuItem("Save as...");
        mfrevert = new JMenuItem("Revert");
        mfquit = new JMenuItem("Quit");
        
        menubar.add(mfile);
        menubar.add(medit);
        menubar.add(mextra);
        
        mfile.add(mfnew);
        mfile.add(mfopen);
        mfile.add(mfsave);
        mfile.add(mfsaveas);
        mfile.add(mfrevert);
        mfile.add(mfquit);
        
        mfnew.addActionListener(this);
        mfopen.addActionListener(this);
        mfsave.addActionListener(this);
        mfsaveas.addActionListener(this);
        mfrevert.addActionListener(this);
        mfquit.addActionListener(this);
        
        mfnew.setAccelerator(KeyStroke.getKeyStroke('N', KeyEvent.CTRL_DOWN_MASK));
        mfopen.setAccelerator(KeyStroke.getKeyStroke('O', KeyEvent.CTRL_DOWN_MASK));
        mfsave.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK));
        mfsaveas.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
        mfrevert.setAccelerator(KeyStroke.getKeyStroke('R', KeyEvent.CTRL_DOWN_MASK));
        mfquit.setAccelerator(KeyStroke.getKeyStroke('Q', KeyEvent.CTRL_DOWN_MASK));
        
        enableItems(false);
        
        this.setJMenuBar(menubar);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    
    public void addScrollPane(JPanel allenOmvattend)
    {
        JScrollPane scroll = new JScrollPane(allenOmvattend, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        JScrollBar hbar = scroll.getVerticalScrollBar();
        hbar.addAdjustmentListener(new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent ae)
            {
                //System.out.println(ae.getAdjustmentType());
                //System.out.println("hoogte: " + controller.getHbcp().getSize().height + " & breedte: " + controller.getHbcp().getSize().width);
                int hoogte = controller.getHbcp().getSize().height;
                if(ae.getValue() > 0)
                {
                    double vvalue = (double) ae.getValue();
                    double aantalpanelsverschoven = (vvalue / (double) hoogte);
                    //System.out.println(aantalpanelsverschoven);
                    controller.scrollBorden(aantalpanelsverschoven);
                }
                //System.out.println(ae.getValue());
            }
        });
        scroll.setBackground(Color.yellow);
        scroll.setSize(allenOmvattend.getSize());
        scroll.setOpaque(true);
        scroll.setVisible(true);
        this.setContentPane(scroll);
        refresh();
        this.pack();
        this.revalidate();
        this.repaint();
    }
    
    public boolean optionPane(String actie)
    {
        int optionpane = 0;
        if(actie.endsWith("new"))
        {
            optionpane = JOptionPane.showConfirmDialog(this, "Starting a new file means losing your changes.\nDo you really want that?", "alert", JOptionPane.OK_CANCEL_OPTION);
        }
        else if(actie.equals("open"))
        {
            optionpane = JOptionPane.showConfirmDialog(this, "Opening a new/other file means losing your changes.\nDo you really want that?", "alert", JOptionPane.OK_CANCEL_OPTION);
        }
        else if(actie.equals("revert"))
        {
            optionpane = JOptionPane.showConfirmDialog(this, "Reverting the file means losing your changes.\nDo you really want that?", "alert", JOptionPane.OK_CANCEL_OPTION);
        }
        else if(actie.equals("quit"))
        {
            optionpane = JOptionPane.showConfirmDialog(this, "Quiting the application means losing your changes.\nDo you really want that?", "alert", JOptionPane.OK_CANCEL_OPTION);
        }
        if(optionpane == JOptionPane.OK_OPTION)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void enableItems(boolean enable)
    {
        mfsave.setEnabled(enable);
        mfrevert.setEnabled(enable);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        System.out.println(ae.getActionCommand());
        if(ae.getActionCommand().equals("New"))
        {
            controller.menuNew();
        }
        else if(ae.getActionCommand().equals("Open..."))
        {
            try {
                controller.menuOpen();
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(ae.getActionCommand().equals("Save"))
        {
            try {
                controller.menuSave();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(ae.getActionCommand().equals("Save as..."))
        {
            try {
                controller.menuSaveAs();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(ae.getActionCommand().equals("Revert"))
        {
            try {
                controller.menuRevert();
            } catch (IOException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(ae.getActionCommand().equals("Quit"))
        {
            controller.menuQuit();
        }
    }
}
