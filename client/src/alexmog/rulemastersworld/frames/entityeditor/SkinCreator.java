package alexmog.rulemastersworld.frames.entityeditor;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import alexmog.rulemastersworld.Main;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class SkinCreator extends JFrame {
    public static String IMAGES_PATH = "/res/images/creatures/parts/";
    private static final long serialVersionUID = 5787355244429636522L;
    private JPanel contentPane;
    private JTree tree;
    private JList<JLabel> list;
    private DefaultListModel<JLabel> listModel = new DefaultListModel<JLabel>();
    public static File classFile = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath());
    private int pos = 0;
    private SkinUpdater updater;
    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String[] str = {"shoulders/shoulders1.png", "body/Female/BlackHuman.png", "cloaks/cloak7.png"};
                    SkinCreator frame = new SkinCreator(new SkinUpdater() {
                        
                        public void updateSkin(String[] images) {
                            for (int i = 0; i < images.length; ++i) {
                                System.out.println(images[i]);
                            }
                        }
                    }, str);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public SkinCreator(SkinUpdater updater, String[] last) {
        this.updater = updater;
        setTitle("Editeur de skins");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 800, 530);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        Path path = null;
        
        try {
            path = Paths.get(Main.class.getResource(IMAGES_PATH).toURI());
        } catch (URISyntaxException e) {
            System.out.println("ERROR");
            System.exit(1);
        }
        TreeModel model = new FileTreeModel(path.toFile());
        tree = new JTree(model);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        JButton btnAjouterUnCalque = new JButton("Ajouter un calque");
        btnAjouterUnCalque.setBounds(293, 331, 196, 25);
        contentPane.add(btnAjouterUnCalque);
        btnAjouterUnCalque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addToSkin();
            }
        });
        
        JButton btnSupprimerCalque = new JButton("Supprimer calque");
        btnSupprimerCalque.setBounds(293, 369, 196, 25);
        contentPane.add(btnSupprimerCalque);
        btnSupprimerCalque.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteImage();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(501, 14, 269, 457);
        contentPane.add(scrollPane);
        
        scrollPane.setViewportView(tree);
        

        final JLabel image = new JLabel("");
        image.setIcon(null);
        image.setBounds(289, 33, 200, 143);
        contentPane.add(image);
        
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                TreePath path = e.getPath();
                File f = (File)path.getLastPathComponent();
                try {
                    if (f.isFile()) {
                        image.setIcon(new ImageIcon(ImageIO.read(f)));
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(12, 13, 269, 458);
        contentPane.add(scrollPane_1);
        
        list = new JList<JLabel>(listModel);
        scrollPane_1.setViewportView(list);
        
        JButton btnSauvegarderLeSkin = new JButton("Fin de la création");
        btnSauvegarderLeSkin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Enumeration<JLabel> elem = listModel.elements();
                String[] ret = new String[listModel.size()];
                int i = 0;
                while (elem.hasMoreElements()) {
                    JLabel l = elem.nextElement();
                    ret[i++] = l.toString();
                }
                SkinCreator.this.updater.updateSkin(ret);
                SkinCreator.this.dispose();
            }
        });
        btnSauvegarderLeSkin.setBounds(293, 446, 196, 25);
        contentPane.add(btnSauvegarderLeSkin);
        
        tree.setCellRenderer(new DefaultTreeCellRenderer() {

            /**
             * 
             */
            private static final long serialVersionUID = -2619793590940881207L;

            @Override
            public Component getTreeCellRendererComponent(
                    JTree tree,
                    Object value,
                    boolean sel,
                    boolean expanded,
                    boolean leaf,
                    int row,
                    boolean hasFocus) {

                // Call parent rendering to keep the default behaviour
                super.getTreeCellRendererComponent(
                        tree, value, sel,
                        expanded, leaf, row,
                        hasFocus);

                // And now specific stuff
                File currentFile = (File) value;
                // If the current node is a directory, and if it has no child,
                // or if they are not accessible, change the icon.
                setText(currentFile.getName());
                if (currentFile.isDirectory() && (currentFile.list() == null || currentFile.list().length == 0)) {
                    if (expanded) {
                        setIcon(openIcon);
                    } else {
                        setIcon(closedIcon);
                    }
                }

                return this;
            }
        });
        
        // Add images
        if (last == null) return;
        for (int i = 0; i < last.length; ++i) {
            try {
                Path p = Paths.get(Main.class.getResource(IMAGES_PATH + File.separatorChar + last[i]).toURI());
                addToSkin(p.toFile());
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * Create the frame.
     */
    public SkinCreator(SkinUpdater updater) {
        this(updater, new String[0]);
    }
    
    public void addToSkin(final File f) {
        if (f == null || f.isDirectory()) return;
        
        JLabel img = new JLabel() {
            /**
             * 
             */
            private static final long serialVersionUID = 5165416183843488246L;
            private String identicator = f.getAbsolutePath().replace(classFile.getPath() + IMAGES_PATH.replace("/", "\\"), "");
            
            public String toString() {
                return identicator.replace("\\", "/");
            }
        };
        try {
            img.setBounds(289, 182, 200, 143);
            img.setIcon(new ImageIcon(ImageIO.read(f)));
            listModel.addElement(img);
            contentPane.add(img);
            int p = listModel.size() - pos;
            contentPane.setComponentZOrder(img, (p < 0 ? 0 : p));
            pos++;
            contentPane.updateUI();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public void addToSkin() {
        if (listModel.size() >= 10) {
            JOptionPane.showMessageDialog(this, "Vous ne pouvez pas rajouter plus de 10 calques");
            return;
        }
        File f = (File)(tree.getSelectionPath() != null ? tree.getSelectionPath().getLastPathComponent() : null);

        addToSkin(f);
    }
    
    public void deleteImage() {
        JLabel img = list.getSelectedValue();
        if (img == null) return;
        listModel.remove(list.getSelectedIndex());
        pos = listModel.getSize();
        contentPane.remove(img);
        contentPane.updateUI();
    }
    
    public void updateList() {
        
    }
}
