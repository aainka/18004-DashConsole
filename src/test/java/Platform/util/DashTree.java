package Platform.util;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

public class DashTree extends JTree {

	public DashTree() {
		super();
		this.setAutoscrolls(true);
		this.setRootVisible(true);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		this.setDragEnabled(true);

		this.setDropMode(DropMode.ON_OR_INSERT);
		this.setTransferHandler(new OV_DashTransHandler());
	}
}
