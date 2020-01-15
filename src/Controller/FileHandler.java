/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Dambord;
import View.BewerkPanel;
import View.BordCompleetPanel;
import View.CompleetPanel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.ArrayList;
import javafx.scene.shape.Path;
import javax.swing.JFileChooser;

/**
 *
 * @author ootje
 */
public class FileHandler {
    private Controller controller;
    private JFileChooser filechooser;
    private File currentfile;
    
    public FileHandler(Controller controller)
    {
        this.controller = controller; 
        filechooser = new JFileChooser();
        filechooser.setCurrentDirectory(new File("./src/draughtsmanager23102017"));
    }
    
    public void openFile()
    {
        filechooser.showOpenDialog(filechooser);
        currentfile = filechooser.getSelectedFile();
    }
    public ArrayList<Dambord> readFile() throws FileNotFoundException, UnsupportedEncodingException, IOException
    {
        ArrayList damborden = new ArrayList<Dambord>();
        InputStreamReader isr = new InputStreamReader(new FileInputStream(currentfile), "utf-8");
        BufferedReader breader = new BufferedReader(isr);
        String regel = breader.readLine();
        while(regel != null)
        {
            Dambord dbord = controller.createDambordVelden();
            String omschrijving = "";
            String [] arrayeen = null;
            String [] arraytwee;
            if(regel.contains(" "))
            {
                arrayeen = regel.split(" ");
                arraytwee =  arrayeen[0].split(":");
                int regela = 1;
                while(regela < arrayeen.length)
                {
                    omschrijving = omschrijving + " " + arrayeen[regela];
                    regela++;
                }
                dbord.setBeschrijving(omschrijving);
            }
            else
            {
                arraytwee = regel.split(":");
            }
            String witsubstring = arraytwee[0].substring(1);
            String zwartsubstring = arraytwee[1].substring(1);
            //System.out.println(witsubstring);
            //System.out.println(zwartsubstring);
            controller.updateFENbord(witsubstring, zwartsubstring, omschrijving, dbord, dbord.getPanel());
            damborden.add(dbord);
            regel = breader.readLine();
        }
        return damborden;
    }
    
    public String getfileName()
    {
        //System.out.println(currentfile.getName());
        return currentfile.getName();
    }
    
    public void saveFileAs(String bestandnaam, CompleetPanel panel) throws FileNotFoundException, UnsupportedEncodingException
    {
        filechooser.showSaveDialog(filechooser);
        currentfile = filechooser.getSelectedFile();
        saveFile(currentfile.getName(), panel);
    }
    
    public void saveFile(String bestandnaam, CompleetPanel panel) throws FileNotFoundException, UnsupportedEncodingException
    {
        String dir = System.getProperty("user.dir");
        //System.out.println(dir);
        File filel = null;
        if(currentfile != null)
        {
            if(!currentfile.getAbsolutePath().endsWith(".dmd"))
            {
                filel = new File(currentfile.getAbsolutePath() + ".dmd");
            }
            else if(currentfile.getAbsolutePath().endsWith(".dmd"))
            {
                filel = new File(currentfile.getAbsolutePath());
            }
        }
        else if(currentfile == null)
        {
            filel = new File(dir + "/src/draughtsmanager23102017" ,bestandnaam);
        }
        PrintWriter writer = new PrintWriter(filel, "utf-8");
        ArrayList<BordCompleetPanel> bordpanelen = panel.getBordpanelen();
        int abord = 0;
        while(abord < bordpanelen.size())
        {
            BordCompleetPanel bord = bordpanelen.get(abord);
            BewerkPanel bpanel = bord.getBewerkPanel();
            String FENwit = bpanel.getFenWit();
            String FENzwart = bpanel.getFenZwart();
            String beschrijving = bpanel.getBeschrijving();
            writer.println("W" + FENwit + ":Z" + FENzwart + " " + beschrijving);
            abord++;
        }
        writer.close();
    }
}
