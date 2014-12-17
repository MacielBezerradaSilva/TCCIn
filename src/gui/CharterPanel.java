package gui;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import management.RequestManager;
import model.Charter;

/**
 *
 * @author heitor
 */
public class CharterPanel extends JPanel {
    Charter charter;
    private JLabel charterNameId;
    private JLabel charterObj;
    private JLabel charterRequirements;
    private JLabel charterSetup;
    private JLabel charterNotes;
    private JLabel charterKeyAreas;
    private JLabel charterIssues;
    private JLabel charterImpNotes;
    private JLabel charterMax;
    private JLabel charterMin;
    private final RequestManager rm;
    private static final int CHARTER_ROWS = 0, CHARTER_COLS = 2, CHARTER_H_GAP = 25, CHARTER_v_GAP = 15;

    public CharterPanel() {
        this(new Charter());
    }
    
    public CharterPanel(Charter charter) {
        super(new GridLayout(CHARTER_ROWS, CHARTER_COLS, CHARTER_H_GAP, CHARTER_v_GAP));
        this.charter = charter;
        rm = new RequestManager(new JFrame());
        createCharterPanel();
    }
    
    public void setCharter(Charter newCharter){
        charter=newCharter;
        
        String temp;
        if (charter.getName().length() > 30) {
            temp = charter.getName().substring(0, 30) + "...";
        } else {
            temp = charter.getName();
        }
        
//        charterNameId.setText(charter.getEtId()+" - " + charter.getName());
        charterNameId.setText(charter.getEtId()+" - " + temp);
        charterObj.setText(charter.getObjective());
        charterRequirements.setText(charter.getRequirements());
        charterSetup.setText(charter.getSetup());
        charterNotes.setText(charter.getNotes());
        charterKeyAreas.setText(charter.getKeyAreas());
        charterIssues.setText(charter.getIssuesToBeAware());
        charterImpNotes.setText(charter.getImportantNotes());
        charterMax.setText(charter.getMaxTime()+"");
        charterMin.setText(charter.getMinTime()+"");
    }
    
    private void createCharterPanel() {
        
        charterNameId = new JLabel(charter.getEtId()+" - "+charter.getName());
        charterObj = new JLabel(charter.getObjective());
        charterRequirements = new JLabel(charter.getRequirements());
        charterSetup = new JLabel(charter.getSetup());
        charterNotes = new JLabel(charter.getNotes());
        charterKeyAreas = new JLabel(charter.getKeyAreas());
        charterIssues = new JLabel(charter.getIssuesToBeAware());
        charterImpNotes = new JLabel(charter.getImportantNotes());
        charterMax = new JLabel(charter.getMaxTime()+"");
        charterMin = new JLabel(charter.getMinTime()+"");
        
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_info")));
        add(new JLabel());
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_name")));
        add(charterNameId);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_objective")));
        add(charterObj);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_requirements")));
        add(charterRequirements);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_setup")));
        add(charterSetup);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_notes")));
        add(charterNotes);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_key_areas")));
        add(charterKeyAreas);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_issues_to_be_aware")));
        add(charterIssues);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_important_notes")));
        add(charterImpNotes);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_max_time")));
        add(charterMax);
        add(new JLabel(rm.loadProperty(EntryPoint.getLanguage()+"_Charter_min_time")));
        add(charterMin);
    }
}
