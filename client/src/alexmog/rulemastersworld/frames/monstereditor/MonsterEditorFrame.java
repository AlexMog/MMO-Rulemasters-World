package alexmog.rulemastersworld.frames.monstereditor;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MonsterEditorFrame extends JFrame {
    private static final long serialVersionUID = 4426439241888196624L;

    public MonsterEditorFrame() {
        setTitle("Editeur de monstres");
        setSize(800, 600);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnMonstre = new JMenu("Monstre");
        menuBar.add(mnMonstre);
        
        JMenuItem mntmNouveauMonstre = new JMenuItem("Nouveau monstre");
        mnMonstre.add(mntmNouveauMonstre);
        
        JMenuItem mntmChargerUnMonstre = new JMenuItem("Charger un monstre");
        mnMonstre.add(mntmChargerUnMonstre);
        
        JMenuItem mntmSauvegarder = new JMenuItem("Sauvegarder");
        mnMonstre.add(mntmSauvegarder);
    }
}
