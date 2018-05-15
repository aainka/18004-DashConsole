package Platform.DashConsole;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public class DashTreeModel extends DefaultTreeModel {

	public DashTreeModel(TreeNode arg0) {
		super(arg0);
		// loading(this);
		MutableTreeNode root = (MutableTreeNode) this.getRoot();
		add(root, "kim");
		add(root, "hong");
		add(root, "tree");
	}

	public void add(MutableTreeNode parent, String n) {
		MutableTreeNode newChild = new DefaultMutableTreeNode(n);
		this.insertNodeInto(newChild, parent, parent.getChildCount());
	}

}
