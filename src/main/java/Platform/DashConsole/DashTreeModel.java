package Platform.DashConsole;

import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import Platform.util.OI_TreeEncoder;
import Platform.util.StringNodeEncoder;

public class DashTreeModel extends DefaultTreeModel  implements TreeModelListener{

	OV_DashDao dao = new OV_DashDao();
	
	
	public DashTreeModel(TreeNode arg0) {
		super(arg0);
		dao.loading(this);
		this.addTreeModelListener(this);
		// MutableTreeNode root = (MutableTreeNode) this.getRoot();
		// add(root, "kim");
		// add(root, "hong");
		// add(root, "tree");
	}

	public void add(MutableTreeNode parent, String n) {
		MutableTreeNode newChild = new DefaultMutableTreeNode(n);
		this.insertNodeInto(newChild, parent, parent.getChildCount());
	}
	@Override
	public void treeNodesChanged(TreeModelEvent arg0) {
		System.out.println("treeNodesChanged(TreeModelEvent arg0)");
		
	}

	@Override
	public void treeNodesInserted(TreeModelEvent arg0) {
		
		System.out.println("treeNodesInserted(TreeModelEvent arg0)");
		TreePath path = arg0.getTreePath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		System.out.println("INSERT.parent = "+node.getUserObject());
		
		DefaultMutableTreeNode node2 =(DefaultMutableTreeNode) arg0.getChildIndices();
		System.out.println("INSERT.node ="+ node.getUserObject());
	}

	@Override
	public void treeNodesRemoved(TreeModelEvent arg0) {
		System.out.println("treeNodesRemoved(TreeModelEvent arg0)");
		TreePath path = arg0.getTreePath();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
		System.out.println("REMOVE = "+node.getUserObject());
		
	}

	@Override
	public void treeStructureChanged(TreeModelEvent arg0) {
		System.out.println("treeStructureChanged(TreeModelEvent arg0)");
		
	}
}
