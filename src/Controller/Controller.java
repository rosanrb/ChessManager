/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BruinVeld;
import Model.Dambord;
import Model.Veld;
import Model.WitVeld;
import Model.WitteSchijf;
import Model.ZwarteSchijf;
import View.BewerkPanel;
import View.ButtonPanel;
import View.CompleetPanel;
import View.DiagramPanel;
import View.BordCompleetPanel;
import View.Window;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import javax.swing.WindowConstants;

/**
 *
 * @author ootje
 */
public class Controller {
    private FileHandler filehandler;
    private Window window;
    private CompleetPanel hoofdpanel;
    private ArrayList<Dambord> damborden;
    private Dambord huidigbord;
    private BordCompleetPanel huidigbcp;
    private int changed; //0 = niet aangepast - 1 = aangepast - >1 = eerder aangepast
    private String bestandtitel;
    
    public Controller()
    {
        filehandler = new FileHandler(this);
        window = new Window(this);
        damborden = new ArrayList<Dambord>();
        hoofdpanel = new CompleetPanel();
        bestandtitel = "Untitled";
        changed = 0;
    }
    
    public void create()
    {
        huidigbord = createDambordVelden();
        damborden.add(huidigbord);
        huidigbcp = damborden.get(0).getPanel();
        hoofdpanel.addBord(damborden.get(0).getPanel());
        hoofdpanel.addBordPanelen();
        setBewerk(huidigbord, huidigbcp, "W:B Een leeg bord", "");
        setBeginopstelling(huidigbord);
        setTitle("Untitled");
        huidigbcp.updatePanels();
        window.addScrollPane(hoofdpanel);
        huidigbcp.revalidate();
        huidigbcp.repaint();
        initialPosition(huidigbcp, huidigbord);
        setTitle("Untitled");
    }
    
    public Dambord createDambordVelden()
    {
        Dambord dambord = new Dambord(this);
        boolean bruinveld = false;
        Veld vorigveld = null;
        int teller = 1;
        for(int c = 0; c < 10; c++)
        {
            for(int r = 0; r < 10; r++)
            {
                Veld hveld = null;
                if(bruinveld == true)
                {
                    hveld = new BruinVeld();
                    dambord.setVeld(r, c, hveld);
                    ((BruinVeld)hveld).setVeldNummer(teller);
                    teller++;
                    bruinveld = false;
                }
                else if(bruinveld == false)
                {
                    hveld = new WitVeld();
                    dambord.setVeld(r, c, hveld);
                    bruinveld = true;
                }
                if(vorigveld != null)
                {
                    hveld.setVlinks(vorigveld);
                    vorigveld.setVrechts(hveld);
                }
                if(r >= 1 && r < 10)
                {
                    Veld bveld = dambord.getVeld(r-1, c);
                    hveld.setVboven(bveld);
                    bveld.setVonder(hveld);
                }     
                vorigveld = hveld;
            }
            if(bruinveld == true)
            {
                bruinveld = false;
            }
            else if(bruinveld == false)
            {
                bruinveld = true;
            }
        }
        return dambord;
    }
    
    public Dambord getHbord()
    {
        return huidigbord;
    }
    
    public void setBeginopstelling(Dambord bord)
    {
        for(int r = 0; r < 10; r++)
        {
            for(int c = 0; c < 10; c++)
            {
                Veld hveld = bord.getVeld(r, c);
                if (hveld instanceof BruinVeld)
                {
                    if(((BruinVeld)hveld).getVeldNummer() < 21)
                    {
                        ((BruinVeld)hveld).setSchijf(new ZwarteSchijf());
                    }
                    else if(((BruinVeld)hveld).getVeldNummer() > 30)
                    {
                        ((BruinVeld)hveld).setSchijf(new WitteSchijf());
                    }
                }
            }
        }
    }
    
    public void setBewerk(Dambord bord, BordCompleetPanel panel, String beschrijving, String fout)
    {
        //ArrayList<BordCompleetPanel> panels = hoofdpanel.getBordpanelen();
        //BordCompleetPanel  panel = huidigbcp;
        panel.getBewerkPanel().setBeschrijving(beschrijving);
        String witfen = "";
        String zwartfen = "";
        for(int c = 0; c < 10; c++)
        {
            for(int r = 0; r < 10; r++)
            {
                Veld hveld = bord.getVeld(r, c);
                if (hveld instanceof BruinVeld)
                {
                    if(((BruinVeld)hveld).getSchijf() != null)
                    {
                        if(((BruinVeld)hveld).getSchijf() instanceof ZwarteSchijf)
                        {
                            if(zwartfen.equals(""))
                            {
                                zwartfen = zwartfen + ((BruinVeld)hveld).getVeldNummer();
                            }
                            else
                            {
                                zwartfen = zwartfen + "," + ((BruinVeld)hveld).getVeldNummer();
                            }
                        }
                        else if(((BruinVeld)hveld).getSchijf() instanceof WitteSchijf)
                        {
                            if(witfen.equals(""))
                            {
                                witfen = witfen + ((BruinVeld)hveld).getVeldNummer();
                            }
                            else
                            {
                                witfen = witfen + "," + ((BruinVeld)hveld).getVeldNummer();
                            }
                        }
                    }
                }
            }
        }
        panel.getBewerkPanel().setFenWit(witfen);
        panel.getBewerkPanel().setFenZwart(zwartfen);
        panel.getBewerkPanel().setFouten(fout);
    }
    
    public void setTitle(String titel)
    {
        String aantald = "";
        String diagramnummer = "";
        int aantaldint = hoofdpanel.getBordpanelen().size();
        aantald = Integer.toString(aantaldint);
        int teller = 1;
        for(int t = 0; t < aantaldint; t++)
        {
            BordCompleetPanel hpanel = hoofdpanel.getBordpanelen().get(t);
            if(hpanel.equals(huidigbcp))
            {
                window.setTitle(titel + ", diagram " + teller + " of " + aantald);
            }
            teller++;
        }
        //window.setTitle(titel);
    }
    
    public void setHuidigbcp(BordCompleetPanel panel)
    {
        huidigbcp = panel;
    }
    
    public void changed()
    {
        changed++;
        if(changed == 3)
        {
            window.setTitle("* " + window.getTitle());
            window.enableItems(true);
        }
    }
    
    public void emptyBoard(BordCompleetPanel panel, Dambord bord)
    {
        for(int r = 0; r < 10; r++)
        {
            for(int k = 0; k < 10; k++)
            {
                Veld veld = bord.getVeld(r, k);
                if(veld instanceof BruinVeld)
                {
                    ((BruinVeld) veld).setSchijf(null);
                }
            }
        }
        panel.updatePanels();
        panel.revalidate();
        panel.repaint();
        changed();
    }
    
    public void initialPosition(BordCompleetPanel panel, Dambord bord)
    {
        emptyBoard(panel, bord);
        setBeginopstelling(bord);
        panel.updatePanels();
        panel.revalidate();
        panel.repaint();
        setBewerk(bord, panel, "W:B Een leeg bord", "");
        changed();
    }
    
    public void updateFENbord(String FENw, String FENz, String omschrijving, Dambord bord, BordCompleetPanel panel)
    {
        emptyBoard(panel, bord);
        String [] fenschijvenw = FENw.split(",");
        String [] fenschijvenz = FENz.split(",");
        String fout = "";
        for(int c = 0; c < 10; c++)
        {
            for(int r = 0; r < 10; r++)
            {
                Veld veld = bord.getVeld(r, c);
                if(veld instanceof BruinVeld)
                {
                    int veldnummer = ((BruinVeld) veld).getVeldNummer();
                    boolean schijfw = false;
                    boolean schijfz = false;
                    int nummers = 51;
                    boolean wbevatletter = false;
                    for(int x = 0; x < fenschijvenw.length; x++)
                    {
                        String stringnummer = fenschijvenw[x];
                        if(fenschijvenw[x].startsWith("K"))
                        {
                            stringnummer = fenschijvenw[x].substring(1);
                        }
                        char character;
                        boolean bevatletter = false;
                        for(int teller = 0; teller < stringnummer.length(); teller++)
                        {
                            character = stringnummer.charAt(teller);
                            if(!Character.isDigit(character))
                            {
                                System.out.println("fout karakter");
                                String foutchar = "Error in FEN-position: invalid character '" + character + "' detected";
                                panel.getBewerkPanel().setFouten(foutchar);
                                fout = foutchar;
                                bevatletter = true;
                            }
                        }
                        
                        if (!bevatletter)
                        {
                            int nummer = Integer.parseInt(stringnummer);
                            if(nummer >= 51)
                            {
                                String fouttekst = "Error in FEN-position: invalid square detected (" + stringnummer + "); number should be between 1 and 50";
                                panel.getBewerkPanel().setFouten(fouttekst);
                                fout = fouttekst;
                                //x = fenschijvenw.length;
                            }
                            if(veldnummer == nummer)
                            {
                                schijfw = true;
                                nummers = x;
                            }
                        }
                        else if(bevatletter)
                        {
                            x = fenschijvenw.length;
                            wbevatletter = true;
                        }
                    }
                    if(schijfw == true)
                    {
                        if(((BruinVeld) veld).bevatSchijf())
                        {
                            String foutbezet = "Error in FEN-position: square " + ((BruinVeld) veld).getVeldNummer() + " is already occupied by another piece";
                            panel.getBewerkPanel().setFouten(foutbezet);
                            fout = foutbezet;
                            //x = fenschijvenw.length;
                        }
                        else
                        {
                            WitteSchijf wschijf = new WitteSchijf();
                            if(nummers < 51 && fenschijvenw[nummers].startsWith("K"))
                            {
                                wschijf.setDam(true);
                                nummers = 51;
                            }
                            ((BruinVeld) veld).setSchijf(wschijf);
                        }
                    }
                    if(!wbevatletter)
                    {
                    for(int x = 0; x < fenschijvenz.length; x++)
                    {
                        String stringnummer = fenschijvenz[x];
                        if(fenschijvenz[x].startsWith("K"))
                        {
                            fenschijvenz[x] = fenschijvenz[x].substring(1);
                        }
                        char character;
                        for(int teller = 0; teller < stringnummer.length(); teller++)
                        {
                            character = stringnummer.charAt(teller);
                            if(!Character.isDigit(character))
                            {
                                System.out.println("fout karakter");
                                String foutchar = "Error in FEN-position: invalid character '" + character + "' detected";
                                //x = fenschijvenz.length;
                                fout = foutchar;
                            }
                            else
                            {
                                int nummer = Integer.parseInt(fenschijvenz[x]);
                                if(nummer >= 51)
                                {
                                    String fouttekst = "Error in FEN-position: invalid square detected (" + stringnummer + "); number should be between 1 and 50";
                                    panel.getBewerkPanel().setFouten(fouttekst);
                                    //x = fenschijvenz.length;
                                    fout = fouttekst;
                                }
                                if(veldnummer == nummer)
                                {
                                    schijfz = true;
                                    nummers = x;
                                }
                            }
                        }
                    }
                    if(schijfz == true)
                    {
                        if(((BruinVeld) veld).bevatSchijf())
                        {
                            String foutbezet = "Error in FEN-position: square " + ((BruinVeld) veld).getVeldNummer() + " is already occupied by another piece";
                            panel.getBewerkPanel().setFouten(foutbezet);
                            fout = foutbezet;
                            //x = fenschijvenz.length;
                        }
                        else
                        {
                            ZwarteSchijf zschijf = new ZwarteSchijf();
                            if(nummers < 51 && fenschijvenz[nummers].startsWith("K"))
                            {
                                zschijf.setDam(true);
                                nummers = 51;
                            }
                            ((BruinVeld) veld).setSchijf(zschijf);
                        }
                    }
                    if(schijfw == false && schijfz == false)
                    {
                        ((BruinVeld) veld).setSchijf(null);
                    }
                }
                }
            }
        }
        panel.updatePanels();
        panel.revalidate();
        panel.repaint();
        setBewerk(bord, panel, omschrijving, fout);
        panel.revalidate();
        panel.repaint();
        changed();
    }
    
    public void setBeschrijving(String beschrijving)
    {
        huidigbord.setBeschrijving(beschrijving);
    }
    
    public void menuNew()
    {
        boolean akkoord = true;
        if(changed >= 1)
        {
            akkoord = window.optionPane("new");
        }
        if(akkoord == true)
        {
            damborden = new ArrayList<>();
            hoofdpanel.removeAll();
            hoofdpanel.removeAllBorden();
            create();
            hoofdpanel.revalidate();
            hoofdpanel.repaint();
            changed = 0;
            window.enableItems(false);
        }
    }
    
    public void menuOpen() throws UnsupportedEncodingException, IOException
    {
        boolean akkoord = true;
        if(changed >= 1)
        {
            akkoord = window.optionPane("open");
        }
        if(akkoord == true)
        {
            filehandler.openFile();
            damborden = filehandler.readFile();
            hoofdpanel.removeAll();
            hoofdpanel.removeAllBorden();
            for(int teller = 0; teller < damborden.size(); teller++)
            {
                //BordCompleetPanel p = new BordCompleetPanel(damborden.get(teller), this);
                BordCompleetPanel p = damborden.get(teller).getPanel();
                setBewerk(damborden.get(teller), p, damborden.get(teller).getBeschrijving(), "");
                hoofdpanel.addBord(p);
                if(teller == 0)
                {
                    huidigbcp = p;
                    huidigbord = damborden.get(teller);
                }
            }
            hoofdpanel.addBordPanelen();
            hoofdpanel.revalidate();
            hoofdpanel.repaint();
            setTitle(filehandler.getfileName());
            bestandtitel = filehandler.getfileName();
            changed = 0;
            window.enableItems(false);
        }
    }
    
    public void menuSave() throws FileNotFoundException, UnsupportedEncodingException
    {
        if(bestandtitel.equals("Untitled"))
        {
            bestandtitel = bestandtitel + ".dmd";
        }
        filehandler.saveFile(bestandtitel, hoofdpanel);
    }
    
    public void menuSaveAs() throws FileNotFoundException, UnsupportedEncodingException
    {
        boolean akkoord = true;
        if(changed >= 1)
        {
            akkoord = window.optionPane("save");
        }
        if(akkoord == true)
        {
            filehandler.saveFileAs(bestandtitel, hoofdpanel);
        }
    }
    
    public void menuRevert() throws UnsupportedEncodingException, IOException
    {
        boolean akkoord = true;
        if(changed >= 1)
        {
            akkoord = window.optionPane("revert");
        }
        if(akkoord == true)
        {
            damborden = filehandler.readFile();
            hoofdpanel.removeAll();
            hoofdpanel.removeAllBorden();
            for(int teller = 0; teller < damborden.size(); teller++)
            {
                //BordCompleetPanel p = new BordCompleetPanel(damborden.get(teller), this);
                BordCompleetPanel p = damborden.get(teller).getPanel();
                setBewerk(damborden.get(teller), p, damborden.get(teller).getBeschrijving(), "");
                hoofdpanel.addBord(p);
                if(teller == 0)
                {
                    huidigbcp = p;
                    huidigbord = damborden.get(teller);
                }
            }
            hoofdpanel.addBordPanelen();
            hoofdpanel.revalidate();
            hoofdpanel.repaint();
            setTitle(filehandler.getfileName());
            bestandtitel = filehandler.getfileName();
            changed = 0;
            window.enableItems(false);
        }
    }
    
    public void menuQuit()
    {
        boolean akkoord = true;
        if(changed >= 1)
        {
            akkoord = window.optionPane("quit");
        }
        if(akkoord == true)
        {
            System.exit(0);
        }
    }

    public BordCompleetPanel getHbcp()
    {
        return huidigbcp;
    }

    public void scrollBorden(double aantalpanelsverschoven)
    {
        //System.out.println("double " + aantalpanelsverschoven);
        //System.out.println("integer " + (int) aantalpanelsverschoven)
        int a = (int) Math.round(aantalpanelsverschoven);
        //System.out.println(a);
        if(aantalpanelsverschoven > 0.1)
        {
        huidigbcp = hoofdpanel.getBordpanelen().get(a);
        huidigbord = damborden.get(a);
        setTitle(filehandler.getfileName());
        }
    }

    public void klikVakje(int vakjeXarray, int vakjeYarray, boolean shiftDown, Dambord bord, BordCompleetPanel panel)
    {
        Veld gekliktveld = bord.getVeld(vakjeXarray, vakjeYarray);
        if(gekliktveld instanceof BruinVeld)
        {
            if(!shiftDown)
            {
                if(((BruinVeld) gekliktveld).bevatSchijf())
                {
                    if(((BruinVeld) gekliktveld).getSchijf().getSchijfIsDam())
                    {
                        ((BruinVeld) gekliktveld).setSchijf(null);
                    }
                    else
                    {
                        ((BruinVeld) gekliktveld).getSchijf().setDam(true);
                    }
                }
                else
                {
                    ((BruinVeld) gekliktveld).setSchijf(new WitteSchijf());
                }
            }
            else
            {
                if(((BruinVeld) gekliktveld).bevatSchijf())
                {
                    if(((BruinVeld) gekliktveld).getSchijf().getSchijfIsDam())
                    {
                        if(((BruinVeld) gekliktveld).getSchijf() instanceof WitteSchijf)
                        {
                            ZwarteSchijf nieuwzschijf = new ZwarteSchijf();
                            nieuwzschijf.setDam(true);
                            ((BruinVeld) gekliktveld).setSchijf(nieuwzschijf);
                        }
                        else
                        {
                            WitteSchijf nieuwwschijf = new WitteSchijf();
                            nieuwwschijf.setDam(true);
                            ((BruinVeld) gekliktveld).setSchijf(nieuwwschijf);
                        }
                    }
                    else
                    {
                        if(((BruinVeld) gekliktveld).getSchijf() instanceof WitteSchijf)
                        {
                            ((BruinVeld) gekliktveld).setSchijf(new ZwarteSchijf());
                        }
                        else
                        {
                            ((BruinVeld) gekliktveld).setSchijf(new WitteSchijf());
                        }
                    }
                }
                else
                {
                    ((BruinVeld) gekliktveld).setSchijf(new ZwarteSchijf());
                }
            }
            panel.updatePanels();
            panel.revalidate();
            panel.repaint();
            changed();
        }
    }
    
    public String bepaalWFEN(Dambord bord)
    {
        String WFENregel = "";
        for(int r = 0; r < 10; r++)
        {
            for(int c = 0; c < 10; c++)
            {
                Veld hveld = bord.getVeld(c, r);
                if(hveld instanceof BruinVeld)
                {
                    if (((BruinVeld) hveld).bevatSchijf())
                    {
                        if(((BruinVeld) hveld).getSchijf() instanceof WitteSchijf)
                        {
                            if(((BruinVeld) hveld).getSchijf().getSchijfIsDam())
                            {
                                if(WFENregel.equals(""))
                                {
                                    WFENregel = "K" + ((BruinVeld) hveld).getVeldNummer();
                                }
                                else
                                {
                                    WFENregel = WFENregel + ",K" + ((BruinVeld) hveld).getVeldNummer();
                                }
                            }   
                            else
                            {
                                 if(WFENregel.equals(""))
                                {
                                    WFENregel = "" + ((BruinVeld) hveld).getVeldNummer();
                                }
                                else
                                {
                                    WFENregel = WFENregel + "," + ((BruinVeld) hveld).getVeldNummer();
                                }
                            }
                        }
                    }
                }            
            }
       }
        return WFENregel;
    }
    
    public String bepaalZFEN(Dambord bord)
    {
        String ZFENregel = "";
        for(int r = 0; r < 10; r++)
        {
            for(int c = 0; c < 10; c++)
            {
                Veld hveld = bord.getVeld(c, r);
                if(hveld instanceof BruinVeld)
                {
                    if (((BruinVeld) hveld).bevatSchijf())
                    {
                        if(((BruinVeld) hveld).getSchijf() instanceof ZwarteSchijf)
                        {
                            if(((BruinVeld) hveld).getSchijf().getSchijfIsDam())
                            {
                                if(ZFENregel.equals(""))
                                {
                                    ZFENregel = "K" + ((BruinVeld) hveld).getVeldNummer();
                                }
                                else
                                {
                                    ZFENregel = ZFENregel + ",K" + ((BruinVeld) hveld).getVeldNummer();
                                }
                            }   
                            else
                            {
                                 if(ZFENregel.equals(""))
                                {
                                    ZFENregel = "" + ((BruinVeld) hveld).getVeldNummer();
                                }
                                else
                                {
                                    ZFENregel = ZFENregel + "," + ((BruinVeld) hveld).getVeldNummer();
                                }
                            }
                        }
                    }
                }            
            }
       }
        return ZFENregel;
    }
}
