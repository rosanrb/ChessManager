/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author ootje
 */
public class CompleetPanel extends JPanel{
    private ArrayList<BordCompleetPanel> bordpanelen; 
    
    public CompleetPanel()
    {
        bordpanelen = new ArrayList<BordCompleetPanel>();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }
    
    public void addBord(BordCompleetPanel panel)
    {
        bordpanelen.add(panel);
    }
    
    public void removeBord(BordCompleetPanel panel)
    {
        bordpanelen.remove(panel);
    }
    
    public void removeAllBorden()
    {
        bordpanelen = new ArrayList<BordCompleetPanel>();
    }
    
    public ArrayList<BordCompleetPanel> getBordpanelen()
    {
        return bordpanelen;
    }
    
    public void addBordPanelen()
    {
        this.setSize(bordpanelen.get(0).getSize());
        this.setOpaque(true);
        this.setVisible(true);
        for(int teller = 0; teller < bordpanelen.size(); teller++)
        {
            this.add(bordpanelen.get(teller));
        }
    }
}
