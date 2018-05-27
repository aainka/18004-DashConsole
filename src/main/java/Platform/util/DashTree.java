package Platform.util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.DropMode;
import javax.swing.JTree;
import javax.swing.tree.TreeSelectionModel;

public class DashTree extends JTree {

	OV_DashTransHandler transHandler = new OV_DashTransHandler();

	public DashTree()   {
		super();
		this.setAutoscrolls(true);
		this.setRootVisible(true);
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		this.setDragEnabled(true);
		this.setDropMode(DropMode.ON_OR_INSERT);
		this.setTransferHandler(transHandler);
	
	}

}
