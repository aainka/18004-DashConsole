package Platform.util;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import Platform.DashConsole.OV_Issue;

public class StringNodeEncoder extends OI_TreeEncoder{

	@Override
	public MutableTreeNode decodeToTree(String userObject) {
		MutableTreeNode newChild = new DefaultMutableTreeNode(userObject);
		return newChild;
	}

	@Override
	public Object encodeFreomTree(DefaultMutableTreeNode node) {
		return node.getUserObject();
	}
	


}
