package Platform.DashConsole;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

public interface OI_TreeEncoder {

	MutableTreeNode decodeToTree(String userObject);

	Object encodeFreomTree(DefaultMutableTreeNode node);

}
