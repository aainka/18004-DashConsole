package Platform.util;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public class StringNodeEncoder extends OI_TreeEncoder {

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
