package alexmog.rulemastersworld.frames.entityeditor;

import java.io.File;
import java.util.Arrays;

import javax.swing.tree.TreeModel;

public class FileTreeModel implements TreeModel {

    private File root;

    public FileTreeModel(File root) {
        this.root = root;
    }

    public void addTreeModelListener(javax.swing.event.TreeModelListener l) {
        //do nothing
    }

    public Object getChild(Object parent, int index) {
        File f = (File) parent;
        return f.listFiles()[index];
    }

    public int getChildCount(Object parent) {
        File f = (File) parent;
        if (!f.isDirectory()) {
            return 0;
        } else {
            return f.list().length;
        }
    }

    public int getIndexOfChild(Object parent, Object child) {
        File par = (File) parent;
        File ch = (File) child;
        return Arrays.asList(par.listFiles()).indexOf(ch);
    }

    public Object getRoot() {
        return root;
    }

    public boolean isLeaf(Object node) {
        File f = (File) node;
        return !f.isDirectory();
    }

    public void removeTreeModelListener(javax.swing.event.TreeModelListener l) {
        //do nothing
    }

    public void valueForPathChanged(javax.swing.tree.TreePath path, Object newValue) {
        //do nothing
    }

}
