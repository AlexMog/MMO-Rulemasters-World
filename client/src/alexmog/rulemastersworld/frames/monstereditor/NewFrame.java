package alexmog.rulemastersworld.frames.monstereditor;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTree;

import jsyntaxpane.DefaultSyntaxKit;

import javax.swing.JEditorPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.geom.Vector2f;

import alexmog.rulemastersworld.Game;
import alexmog.rulemastersworld.Main;
import alexmog.rulemastersworld.entity.LivingEntity;
import alexmog.rulemastersworld.entity.ScriptedEntity;
import alexmog.rulemastersworld.entity.StatsEntity;
import alexmog.rulemastersworld.frames.entityeditor.SkinCreator;
import alexmog.rulemastersworld.frames.entityeditor.SkinUpdater;
import alexmog.rulemastersworld.gamemodes.EntityTestMode;

import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class NewFrame extends JFrame implements SkinUpdater {
    private static final long serialVersionUID = 2579228615782660050L;
    private JTextField mobName;
    private JTextField mobLabel;
    private JTextField mobDamages;
    private JTextField mobCriticalStrike;
    private JTextField mobAgi;
    private JTextField mobStr;
    private JTextField mobEnd;
    private JTextField mobInt;
    private JTextField mobMana;
    private JTextField mobAtkSpeed;
    private JTextField mobArmor;
    private JTextField mobParry;
    private JTextField mobDodge;
    private JLabel lblEsquiveTotale;
    private JLabel lblArmureTotale;
    private JLabel lblParadeTotale;
    private JLabel lblDegatsTotal;
    private JLabel lblCcTotal;
    private JLabel lblHp;
    private JLabel lblMana;
    private JPanel skinPanel;
    private List<JLabel> skinImages = new ArrayList<JLabel>();
    private List<ImageIcon> iconsList = new ArrayList<ImageIcon>();
    private String[] skinList = null;
    private JSpinner skinWidth;
    private JSpinner skinHeight;
    private JToggleButton tglbtnMonstreScriptattention;
    private JEditorPane mobScript;
    private StatsEntity entity = new StatsEntity(new Vector2f(), new Vector2f(), null);
    private JTextField textField;
    private JTextField textField_1;
    private JSpinner mobSpeed;
    private JSpinner mobVision;
    
    private abstract class IntJTextFieldListener implements CaretListener
    {
        private String oldText = "";
        public abstract void onValidValue(int value);
        
        public void caretUpdate(CaretEvent e) {
            final JTextField f = (JTextField)e.getSource();
            
            if (f.getText().length() > 0 ) {
                if (verifyValue(f.getText())) {
                    oldText = f.getText();
                    onValidValue(Integer.parseInt(oldText));
                } else {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            f.setText(oldText);
                        }
                    });
                }
            }
        }
    }
    
    public void update(StatsEntity e) {
        lblEsquiveTotale.setText("% (" + StatsEntity.DODGE_MODIFICATOR + "% / pt d'agilité) Total: " + entity.getDodgePercent() + "%");
        lblArmureTotale.setText("Armure totale: " + entity.getArmor() + " (Dommages réduits: " + (entity.getDamagesReduction() * 100) + "%)");
        lblParadeTotale.setText("% (+" + StatsEntity.PARRY_MODIFICATOR + "% / pt de force) Total: " + entity.getParryPercent() + "%");
        lblDegatsTotal.setText("(+" + StatsEntity.DAMAGE_MODIFICATOR + " / pt de force) Total: " + entity.getDamages());
        lblCcTotal.setText("(+" + StatsEntity.CRITICAL_MODIFICATOR + "% / pt d'agilité) Total: " + entity.getCriticalPercent() + "%");
        lblHp.setText("HP: " + e.getMaxHp() + " (+" + StatsEntity.HP_MODIFICATOR + " / pt Endurence)");
        lblMana.setText("MP: " + e.getMaxMana() + " (+" + StatsEntity.MANA_MODIFICATOR + " / pt Intelligence)");
    }
    
    public NewFrame() {
        setTitle("Editeur de monstres");
        setSize(750, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        
        JPanel panel = new JPanel();
        tabbedPane.addTab("Informations", null, panel, null);
        panel.setLayout(null);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
        panel_1.setBounds(12, 13, 336, 497);
        panel.add(panel_1);
        
        JLabel lblBase = new JLabel("Base");
        lblBase.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblNomDuMonstre = new JLabel("Nom du monstre:");
        
        mobName = new JTextField();
        mobName.setColumns(10);
        
        JLabel lblDescriptionDuMonstre = new JLabel("Description du monstre:");
        
        JTextPane mobDescription = new JTextPane();
        
        JLabel lblStratgiePourTuer = new JLabel("Stratégie pour tuer le monstre:");
        
        JTextPane mobStrategy = new JTextPane();
        
        JLabel m = new JLabel("Titre du monstre:");
        
        mobLabel = new JTextField();
        mobLabel.setColumns(10);
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addComponent(mobStrategy, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                        .addComponent(mobDescription, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                        .addComponent(lblBase, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE)
                        .addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
                            .addComponent(lblNomDuMonstre)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(mobName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(lblDescriptionDuMonstre, Alignment.LEADING)
                        .addComponent(lblStratgiePourTuer, Alignment.LEADING)
                        .addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
                            .addComponent(m)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(mobLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(lblBase)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNomDuMonstre)
                        .addComponent(mobName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblDescriptionDuMonstre)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(mobDescription, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblStratgiePourTuer)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(mobStrategy, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                        .addComponent(m)
                        .addComponent(mobLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(17, Short.MAX_VALUE))
        );
        panel_1.setLayout(gl_panel_1);
        
        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
        panel_3.setBounds(360, 450, 355, 60);
        panel.add(panel_3);
        
        JLabel lblAvanc = new JLabel("Avancé");
        lblAvanc.setHorizontalAlignment(SwingConstants.CENTER);
        
        tglbtnMonstreScriptattention = new JToggleButton("Monstre scripté (Sans IA)");
        tglbtnMonstreScriptattention.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JToggleButton t = (JToggleButton)e.getSource();
                tabbedPane.setEnabledAt(2, t.isSelected());
                tabbedPane.setEnabledAt(4, !t.isSelected());
            }
        });
        
        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
            gl_panel_3.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
                        .addComponent(tglbtnMonstreScriptattention, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 328, Short.MAX_VALUE)
                        .addComponent(lblAvanc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                    .addContainerGap())
        );
        gl_panel_3.setVerticalGroup(
            gl_panel_3.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_3.createSequentialGroup()
                    .addComponent(lblAvanc)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(tglbtnMonstreScriptattention)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_3.setLayout(gl_panel_3);
        
        JPanel panel_6 = new JPanel();
        panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
        panel_6.setBounds(360, 13, 355, 432);
        panel.add(panel_6);
        panel_6.setLayout(null);
        
        JLabel lblVisuel = new JLabel("Visuel");
        lblVisuel.setHorizontalAlignment(SwingConstants.CENTER);
        lblVisuel.setBounds(12, 13, 331, 16);
        panel_6.add(lblVisuel);
        
        JButton button = new JButton("Editer le skin");
        button.setBounds(12, 42, 331, 25);
        button.addActionListener(new ActionListener() {
            
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    
                    public void run() {
                        new SkinCreator(NewFrame.this, skinList).setVisible(true);                        
                    }
                });
            }
        });
        panel_6.add(button);
        
        JLabel lblTaille = new JLabel("Taille:");
        lblTaille.setBounds(12, 83, 36, 16);
        panel_6.add(lblTaille);
        
        JLabel lblX = new JLabel("x");
        lblX.setBounds(118, 83, 14, 16);
        panel_6.add(lblX);
        
        skinWidth = new JSpinner(new SpinnerNumberModel(96, 0, 1024, 1));
        skinWidth.setBounds(60, 80, 57, 22);
        skinWidth.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateSize();
                    }
                });
            }
        });
        panel_6.add(skinWidth);
        
        skinHeight = new JSpinner(new SpinnerNumberModel(128, 0, 1024, 1));
        skinHeight.setBounds(128, 80, 57, 22);
        skinHeight.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateSize();
                    }
                });
            }
        });
        panel_6.add(skinHeight);
        
        JScrollPane skinScrollPane = new JScrollPane();
        skinScrollPane.setBounds(12, 112, 331, 307);
        panel_6.add(skinScrollPane);
        
        skinPanel = new JPanel();
        skinScrollPane.setViewportView(skinPanel);
        skinPanel.setLayout(null);
        
        JPanel panel_4 = new JPanel();
        tabbedPane.addTab("Sorts", null, panel_4, null);
        
        JLabel lblSortsExistants = new JLabel("Sorts du monstre");
        lblSortsExistants.setHorizontalAlignment(SwingConstants.CENTER);
        
        JList<?> list = new JList<Object>();
        
        JButton btnAjouterUnSort = new JButton("Ajouter un sort");
        
        JButton btnS = new JButton("Supprimer le sort selectionné");
        
        JButton btnCrerUnNouveau = new JButton("Créer un nouveau sort");
        
        JTree tree = new JTree();
        
        GroupLayout gl_panel_4 = new GroupLayout(panel_4);
        gl_panel_4.setHorizontalGroup(
            gl_panel_4.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_4.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addComponent(list, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSortsExistants))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(btnCrerUnNouveau, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAjouterUnSort, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnS, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(10)
                    .addComponent(tree, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addContainerGap())
        );
        gl_panel_4.setVerticalGroup(
            gl_panel_4.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_4.createSequentialGroup()
                    .addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
                                .addGroup(gl_panel_4.createSequentialGroup()
                                    .addComponent(lblSortsExistants)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(list, GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE))
                                .addGroup(gl_panel_4.createSequentialGroup()
                                    .addGap(54)
                                    .addComponent(btnAjouterUnSort)
                                    .addGap(6)
                                    .addComponent(btnS)
                                    .addGap(4)
                                    .addComponent(btnCrerUnNouveau))))
                        .addGroup(gl_panel_4.createSequentialGroup()
                            .addGap(28)
                            .addComponent(tree, GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        panel_4.setLayout(gl_panel_4);
        
        JPanel scriptPanel = new JPanel();
        tabbedPane.addTab("Script", null, scriptPanel, null);
        
        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_scriptPanel = new GroupLayout(scriptPanel);
        gl_scriptPanel.setHorizontalGroup(
            gl_scriptPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
        );
        gl_scriptPanel.setVerticalGroup(
            gl_scriptPanel.createParallelGroup(Alignment.LEADING)
                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
        );
        
        JLabel lblLaDocumentationEst = new JLabel("La documentation est disponible sur le site internet");
        lblLaDocumentationEst.setHorizontalAlignment(SwingConstants.CENTER);
        scrollPane.setColumnHeaderView(lblLaDocumentationEst);
        
        DefaultSyntaxKit.initKit();
        mobScript = new JEditorPane();
        scrollPane.setViewportView(mobScript);
        scriptPanel.setLayout(gl_scriptPanel);
        tabbedPane.setEnabledAt(2, false);

        mobScript.setContentType("text/javascript");
        try {
            mobScript.setText(new String(Files.readAllBytes(Paths.get(SkinCreator.classFile.getAbsolutePath()
                    + "/res/script_examples/monster_script.js")), StandardCharsets.UTF_8));
        } catch (IOException e1) {
            e1.printStackTrace();
            mobScript.setText("Script loading example error...");
        }
        
        JPanel panel_5 = new JPanel();
        tabbedPane.addTab("Statistiques", null, panel_5, null);
        
        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, null, null, null));
        
        JSeparator separator = new JSeparator();
        
        JSeparator separator_1 = new JSeparator();
        
        JLabel label = new JLabel("Statistiques");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        
        JSeparator separator_2 = new JSeparator();
        
        JSeparator separator_3 = new JSeparator();
        
        JLabel label_2 = new JLabel("Dégâts:");
        
        mobDamages = new JTextField();
        mobDamages.setColumns(10);
        mobDamages.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setBonusDamages(value);
                update(entity);
            }
        });
        
        JLabel label_3 = new JLabel("Coups Critiques:");
        
        mobCriticalStrike = new JTextField();
        mobCriticalStrike.setColumns(10);
        mobCriticalStrike.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setBonusCriticalHit(value);;
                update(entity);
            }
        });
        
        JLabel label_4 = new JLabel("Force:");
        
        JLabel label_5 = new JLabel("Agilité:");
        
        mobAgi = new JTextField();
        mobAgi.setText("0");
        mobAgi.setColumns(10);
        mobAgi.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setAgi(value);;
                update(entity);
            }
        });
        
        mobStr = new JTextField();
        mobStr.setText("0");
        mobStr.setColumns(10);
        mobStr.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setStr(value);
                update(entity);
            }
        });
        
        JLabel label_6 = new JLabel("Intelligence:");
        
        JLabel label_7 = new JLabel("Endurence:");
        
        mobEnd = new JTextField();
        mobEnd.setText("0");
        mobEnd.setColumns(10);
        mobEnd.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setEnd(value);
                update(entity);
            }
        });
        
        mobInt = new JTextField();
        mobInt.setText("0");
        mobInt.setColumns(10);
        mobInt.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setInt(value);
                update(entity);
            }
        });
        
        JLabel label_8 = new JLabel("Régénération de mana (Mana/s):");
        
        mobMana = new JTextField();
        mobMana.setColumns(10);
        mobMana.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setManaRegen(value);
                update(entity);
            }
        });
        
        JLabel lblVitesseattaquess = new JLabel("Vitesse (Attaques/s):");
        
        mobAtkSpeed = new JTextField();
        mobAtkSpeed.setColumns(10);
        mobAtkSpeed.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setAtkSpeed(value);
                update(entity);
            }
        });
        
        JLabel label_10 = new JLabel("Armure:");
        
        mobArmor = new JTextField();
        mobArmor.setColumns(10);
        mobArmor.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setArmor(value);
                update(entity);
            }
        });
        
        JLabel label_12 = new JLabel("Esquive:");
        
        JLabel label_13 = new JLabel("Parade:");
        
        mobParry = new JTextField();
        mobParry.setColumns(10);
        mobParry.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setBonusParry(value);
                update(entity);
            }
        });
        
        mobDodge = new JTextField();
        mobDodge.setColumns(10);
        mobDodge.addCaretListener(new IntJTextFieldListener() {
            @Override
            public void onValidValue(int value) {
                entity.setBonusDodge(value);
                update(entity);
            }
        });

        lblEsquiveTotale = new JLabel("");
        lblArmureTotale = new JLabel("");
        lblParadeTotale = new JLabel("");
        lblDegatsTotal = new JLabel("");
        lblCcTotal = new JLabel("");
        JLabel lbldgtsParade = new JLabel("(Dégâts + Parade)");
        
        JLabel lblesquiveCritiques = new JLabel("(Esquive + Critiques)");
        
        JLabel lbldgtsSorts = new JLabel("(Dégâts sorts)");
        
        JLabel lblpointsDeVie = new JLabel("(Points de vie)");
        
        lblHp = new JLabel("HP: 0");
        
        lblMana = new JLabel("");
        
        JLabel lblVitesse = new JLabel("Vitesse:");
        
        mobSpeed = new JSpinner(new SpinnerNumberModel(100, 0, 1024, 1));
        
        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
            gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addComponent(label, GroupLayout.DEFAULT_SIZE, 797, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                        .addComponent(label_4)
                                        .addComponent(label_5))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel_2.createSequentialGroup()
                                            .addComponent(mobStr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(lbldgtsParade))
                                        .addGroup(gl_panel_2.createSequentialGroup()
                                            .addComponent(mobAgi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(lblesquiveCritiques)))
                                    .addGap(127)
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblMana)
                                        .addComponent(lblHp)))
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                        .addComponent(label_6)
                                        .addComponent(label_7))
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel_2.createSequentialGroup()
                                            .addComponent(mobEnd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(lblpointsDeVie))
                                        .addGroup(gl_panel_2.createSequentialGroup()
                                            .addComponent(mobInt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(ComponentPlacement.RELATED)
                                            .addComponent(lbldgtsSorts)))))
                            .addGap(231))
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addComponent(label_10)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(mobArmor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblArmureTotale)
                            .addContainerGap())
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                .addComponent(label_12)
                                .addComponent(label_13))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addComponent(mobParry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(lblParadeTotale))
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addComponent(mobDodge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(lblEsquiveTotale)))
                            .addContainerGap())
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addComponent(separator_2, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(separator_3, GroupLayout.PREFERRED_SIZE, 678, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                                .addComponent(separator_1, GroupLayout.PREFERRED_SIZE, 671, GroupLayout.PREFERRED_SIZE)
                                .addComponent(separator, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE)
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addComponent(label_2)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(mobDamages, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(lblDegatsTotal))
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addComponent(label_3)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(mobCriticalStrike, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(lblCcTotal))
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addComponent(label_8)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(mobMana, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGroup(gl_panel_2.createSequentialGroup()
                                    .addComponent(lblVitesseattaquess)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(mobAtkSpeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addContainerGap(28, Short.MAX_VALUE))
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addComponent(lblVitesse)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(mobSpeed, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())))
        );
        gl_panel_2.setVerticalGroup(
            gl_panel_2.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_2.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(label, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_4)
                        .addComponent(mobStr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbldgtsParade)
                        .addComponent(lblHp))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_2.createSequentialGroup()
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(label_5)
                                .addComponent(mobAgi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblesquiveCritiques))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(label_6)
                                .addComponent(mobInt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbldgtsSorts))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                                .addComponent(label_7)
                                .addComponent(mobEnd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblpointsDeVie)))
                        .addComponent(lblMana))
                    .addGap(18)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
                        .addComponent(separator_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(separator_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_10)
                        .addComponent(mobArmor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblArmureTotale))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_12)
                        .addComponent(mobDodge, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEsquiveTotale))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_13)
                        .addComponent(mobParry, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblParadeTotale))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(separator_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_2)
                        .addComponent(mobDamages, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblDegatsTotal))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblVitesseattaquess)
                        .addComponent(mobAtkSpeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_3)
                        .addComponent(mobCriticalStrike, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCcTotal))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(label_8)
                        .addComponent(mobMana, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblVitesse)
                        .addComponent(mobSpeed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(96, Short.MAX_VALUE))
        );
        panel_2.setLayout(gl_panel_2);
        GroupLayout gl_panel_5 = new GroupLayout(panel_5);
        gl_panel_5.setHorizontalGroup(
            gl_panel_5.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_5.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 715, Short.MAX_VALUE)
                    .addContainerGap())
        );
        gl_panel_5.setVerticalGroup(
            gl_panel_5.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_5.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                    .addContainerGap())
        );
        panel_5.setLayout(gl_panel_5);
        

        JPanel panelIA = new JPanel();
        
        tabbedPane.addTab("IA", null, panelIA, null);
        
        JLabel lblQueFeraLe = new JLabel("Que fera le monstre qaund il sera attaqué?");
        
        JComboBox<String> comboBox = new JComboBox<String>();
        comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Devient agressif", "Fuit", "Ne fait rien"}));
        
        JLabel lblQueFaitLe = new JLabel("Que fait le monstre lorsqu'un joueur entre dans sa vision?");
        
        JComboBox<String> comboBox_1 = new JComboBox<String>();
        comboBox_1.setModel(new DefaultComboBoxModel<String>(new String[] {"Devient agressif", "Fuit", "Ne fait rien"}));
        
        JLabel lblChampDeVision = new JLabel("Rayon de vision:");
        
        mobVision = new JSpinner(new SpinnerNumberModel(1, 0, 1024, 1));
        
        JLabel lblCommentLeMonstre = new JLabel("Comment le monstre lance des sorts?");
        
        JComboBox<String> comboBox_3 = new JComboBox<String>();
        comboBox_3.setModel(new DefaultComboBoxModel<String>(new String[] {"Aléatoirement", "Dès qu'ils sont prêt", "Toutes les X secondes", "Jamais"}));
        
        JLabel lblValeurDeX = new JLabel("Valeur de X en secondes:");
        
        JSpinner spinner_1 = new JSpinner();
        
        JLabel lblQuiLeMonstre = new JLabel("Qui le monstre focus?");
        
        JComboBox<String> comboBox_4 = new JComboBox<String>();
        comboBox_4.setModel(new DefaultComboBoxModel<String>(new String[] {"Celui qui le provoque", "Celui qui provoque le plus de menace", "Celui qui fait le plus de dégâts", "Celui qui soigne le plus", "Aléatoire"}));
        
        JLabel lblFaction = new JLabel("Faction:");
        
        JComboBox<String> comboBox_5 = new JComboBox<String>();
        comboBox_5.setModel(new DefaultComboBoxModel<String>(new String[] {"Neutre", "Bleu", "Rouge", "Jaune", "Vert"}));
        
        JLabel lbldeuxMonstresDe = new JLabel("(deux monstres de factions différentes s'attaqueront)");
        GroupLayout gl_panelIA = new GroupLayout(panelIA);
        gl_panelIA.setHorizontalGroup(
            gl_panelIA.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelIA.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelIA.createSequentialGroup()
                            .addComponent(lblQueFeraLe)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panelIA.createSequentialGroup()
                            .addComponent(lblQueFaitLe)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panelIA.createSequentialGroup()
                            .addComponent(lblChampDeVision)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(mobVision, 0, 0, Short.MAX_VALUE)
                            .addGap(208))
                        .addGroup(gl_panelIA.createSequentialGroup()
                            .addComponent(lblCommentLeMonstre)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lblValeurDeX)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panelIA.createSequentialGroup()
                            .addComponent(lblQuiLeMonstre)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(gl_panelIA.createSequentialGroup()
                            .addComponent(lblFaction)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(comboBox_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(lbldeuxMonstresDe)))
                    .addContainerGap(155, Short.MAX_VALUE))
        );
        gl_panelIA.setVerticalGroup(
            gl_panelIA.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panelIA.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblQueFeraLe)
                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblQueFaitLe)
                        .addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(36)
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblChampDeVision)
                        .addComponent(mobVision, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCommentLeMonstre)
                        .addComponent(comboBox_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblValeurDeX)
                        .addComponent(spinner_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblQuiLeMonstre)
                        .addComponent(comboBox_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panelIA.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblFaction)
                        .addComponent(comboBox_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbldeuxMonstresDe))
                    .addContainerGap(326, Short.MAX_VALUE))
        );
        panelIA.setLayout(gl_panelIA);
        
        JPanel panel_8 = new JPanel();
        tabbedPane.addTab("Loot table", null, panel_8, null);
        
        JPanel panel_9 = new JPanel();
        tabbedPane.addTab("Save", null, panel_9, null);
        panel_9.setLayout(null);
        
        JLabel lblSauvegarderVotreMonstre = new JLabel("Sauvegarder votre monstre");
        lblSauvegarderVotreMonstre.setHorizontalAlignment(SwingConstants.CENTER);
        lblSauvegarderVotreMonstre.setBounds(12, 13, 715, 16);
        panel_9.add(lblSauvegarderVotreMonstre);
        
        JLabel lblCatgorie = new JLabel("Catégorie:");
        lblCatgorie.setBounds(220, 42, 83, 16);
        panel_9.add(lblCatgorie);
        
        textField = new JTextField();
        textField.setBounds(311, 39, 116, 22);
        panel_9.add(textField);
        textField.setColumns(10);
        
        JLabel lblIdentifiant = new JLabel("Identifiant:");
        lblIdentifiant.setBounds(220, 71, 83, 16);
        panel_9.add(lblIdentifiant);
        
        textField_1 = new JTextField();
        textField_1.setBounds(311, 68, 116, 22);
        panel_9.add(textField_1);
        textField_1.setColumns(10);
        
        JButton btnSauvegarder = new JButton("Sauvegarder");
        btnSauvegarder.setEnabled(false);
        btnSauvegarder.setBounds(12, 115, 715, 25);
        panel_9.add(btnSauvegarder);
        
        JButton btnNewButton = new JButton("Tester le monstre");
        btnNewButton.setBounds(12, 153, 715, 25);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (skinList == null || skinList.length <= 0) {
                    JOptionPane.showMessageDialog(null, "Vous devez créer un skin pour votre créature.", "Skin error", 0);
                    return;
                }
                
                LivingEntity en;
                if (tglbtnMonstreScriptattention.isSelected()) {
                    en = new ScriptedEntity(new Vector2f(), new Vector2f((Integer)skinWidth.getValue() / 3,
                            (Integer)skinHeight.getValue() / 4), 5, null, 5);
                    ((ScriptedEntity)en).setScript(mobScript.getText());
                } else {
                    en = new LivingEntity(new Vector2f(), new Vector2f((Integer)skinWidth.getValue() / 3,
                            (Integer)skinHeight.getValue() / 4), 5, 5, null);
                }
                en.setSpeed((Integer)mobSpeed.getValue() / 100);
                en.setSkinParts(skinList);
                en.setStats(entity);
                en.setHp(en.getMaxHp());
                en.setVision((Integer)mobVision.getValue());
                final EntityTestMode mode = new EntityTestMode(en);
                for (String skin : skinList) {
//                    mode.addEntitySkin(skin);
                }
                new Thread(new Runnable() {
                    
                    public void run() {
                        Game g = new Game("Test", mode);
                        try {
//                          Log.set(Log.LEVEL_DEBUG);
//                          loginFrame.setVisible(true);
                          AppGameContainer app = new AppGameContainer(g);
                          app.setDisplayMode(Main.WIDTH, Main.HEIGHT, false);
                          app.setTargetFrameRate(Main.FPS);
                          app.setShowFPS(true);
                          app.setForceExit(false);
                          app.start();
                      }catch (Exception ex) {
                          ex.printStackTrace();
                      }
                    }
                }).start();


            }
        });
        panel_9.add(btnNewButton);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        update(entity);
    }
    
    private boolean verifyValue(String e) {
        boolean ret = true;
        
        try {
            Integer.parseInt(e);
        } catch (Exception ex) {
            ret = false;
        }
        
        if (!ret) {
            JOptionPane.showMessageDialog(null, "La valeur doit être numérique et positive.");
        }
        return ret;
    }
    
    public static void main(String[] arg) {
        new NewFrame().setVisible(true);
    }

    public void updateSkin(String[] images) {
        for (JLabel e : skinImages) {
            skinPanel.remove(e);
        }
        
        skinList = images;
        skinImages.clear();
        iconsList.clear();
        
        for (String img : images) {
            try {
                JLabel l = new JLabel();
                ImageIcon icon = new ImageIcon(ImageIO.read(Paths.get(
                        Main.class.getResource(SkinCreator.IMAGES_PATH + File.separatorChar + img).toURI()).toFile()));
                icon = new ImageIcon(icon.getImage().getScaledInstance((Integer)skinWidth.getValue(), (Integer)skinHeight.getValue(), Image.SCALE_SMOOTH));
                l.setIcon(icon);
                l.setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
                skinPanel.add(l);
                skinImages.add(l);
                iconsList.add(icon);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (URISyntaxException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        updateOrder();
        skinPanel.updateUI();
    }
    
    private void updateSize() {
        for (int i = 0; i < skinImages.size(); ++i) {
            ImageIcon icon = new ImageIcon(iconsList.get(i).getImage().getScaledInstance((Integer)skinWidth.getValue(),
                    (Integer)skinHeight.getValue(), Image.SCALE_SMOOTH));
            skinImages.get(i).setIcon(icon);
            skinImages.get(i).setSize((Integer)skinWidth.getValue(), (Integer)skinHeight.getValue());
        }
    }
    
    private void updateOrder() {
        int i = 0;
        for (JLabel l : skinImages) {
            int p = skinImages.size() - i - 1;
            skinPanel.setComponentZOrder(l, (p < 0 ? 0 : p));
            ++i;
        }        
        skinPanel.updateUI();
    }
}
