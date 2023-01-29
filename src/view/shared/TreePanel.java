package view.shared;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.tree.*;
import javax.swing.text.*;

/**
 * Panel that shows a tree view
 * 
 * @author Bhavuk Sikka bhavuk.sikka@estudiante.uam.es
 * @author Samuel de Lucas samuel.lucas@estudiante.uam.es
 **/
public class TreePanel extends JPanel {
    /** Serialized class */
    private static final long serialVersionUID = 1L;
    /** Tree to display */
    private JTree t;
    /** Whether composite can be selected */
    private boolean canSelectComposite;
    /** Whether the root can be selected */
    private boolean canSelectRoot;

    /**
     * Constructor
     * 
     * @param tree tree to show
     */
    public TreePanel(JTree tree) {
        this(tree, false, false);
    }

    /**
     * Constructor
     * 
     * @param tree               tree to show
     * @param canSelectComposite true if composite areas can also be selected
     * @param canSelectRoot true if composite areas can also be selected
     */
    public TreePanel(JTree tree, boolean canSelectComposite, boolean canSelectRoot) {
        this.t = tree;
        this.canSelectComposite = canSelectComposite;
        this.canSelectRoot = canSelectRoot;

        // Create the text field
        StyleContext context = new StyleContext();
        StyledDocument document = new DefaultStyledDocument(context);

        Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
        StyleConstants.setAlignment(style, StyleConstants.ALIGN_CENTER);

        try {
            document.insertString(document.getLength(), "Current Selection:\n\n None", style);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }

        JTextPane selectionText = new JTextPane(document);
        selectionText.setEditable(false);
        selectionText.setFont(new Font("SansSerif", Font.PLAIN, 15));
        selectionText.setBackground(this.getBackground());

        // Customize the tree
        tree.setFont(new Font("SansSerif", Font.PLAIN, 15));
        tree.setBackground(this.getBackground());
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        tree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        tree.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Make the tree not collapsable
        tree.addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {
                throw new ExpandVetoException(event, "Collapsing tree not allowed");
            }
        });

        // Change some visual stuff from the tree
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
        renderer.setBackground(this.getBackground());
        renderer.setBackgroundNonSelectionColor(this.getBackground());
        renderer.setBackgroundSelectionColor(Color.LIGHT_GRAY);

        // Start the tree with all nodes expanded
        expandAllNodes(tree, 0, tree.getRowCount());

        // Update the sellection when a leaf is clicked
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            if ((node.isLeaf() || canSelectComposite) && (!node.isRoot() || canSelectRoot)){
                String data = (String) node.getUserObject();
                selectionText.setText("Current Selection:\n\n" + data);
            }
        });

        JScrollPane treePanel = new JScrollPane(tree);
        treePanel.setBorder(new EmptyBorder(0, 0, 0, 0));

        JPanel outterPanel = new JPanel();
        outterPanel.setLayout(new BoxLayout(outterPanel, BoxLayout.X_AXIS));
        outterPanel.add(Box.createGlue());
        outterPanel.add(treePanel);

        JPanel textArea = new JPanel();
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        textArea.add(Box.createGlue());
        textArea.add(selectionText);
        textArea.add(Box.createGlue());

        this.setLayout(new GridLayout(1, 2));
        this.add(outterPanel);
        this.add(textArea);
    }

    /**
     * method to expand all the nodes inside the tree
     * 
     * @param tree          Tree to expand
     * @param startingIndex Starting index
     * @param rowCount      Number of rows to consider
     */
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount) {
        for (int i = startingIndex; i < rowCount; ++i) {
            tree.expandRow(i);
        }

        if (tree.getRowCount() != rowCount) {
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }

    /**
     * get the selected area
     * 
     * @return the name of the area
     */
    public String getSelection() {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) t.getLastSelectedPathComponent();
        if (node == null) {
            return null;
        }
        if ((node.isLeaf() || canSelectComposite) && (!node.isRoot() || canSelectRoot)) {
            String data = (String) node.getUserObject();
            if(data.contains("(")){
                return data.substring(0, data.indexOf(" (S"));
            }
            return data;
        }
        return null;
    }
}
