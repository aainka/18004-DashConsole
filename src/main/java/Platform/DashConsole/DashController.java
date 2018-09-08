package Platform.DashConsole;

import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import Platform.util.DashTree;


public class DashController implements MouseListener{
	
	DefaultMutableTreeNode root = new DefaultMutableTreeNode("Redmine");
	DashTreeModel treeModel = new DashTreeModel(root);

	public void closeWindow() {
		// TODO Auto-generated method stub

	}

	public JComponent buildUi() {
		DashTree tree = new DashTree();
		tree.setModel(treeModel);
		tree.addMouseListener(this);
		JScrollPane sp = new JScrollPane(tree);
		return sp;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		JTree tree = (JTree) arg0.getSource();
		DefaultMutableTreeNode toNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		System.out.println("Clicked "+toNode.getUserObject());
		OV_Issue issue = (OV_Issue) toNode.getUserObject();
		String sUrl = "http://redmine.ericssonlg.com/redmine/issues/"+issue.getId();
		
	    try {
			Desktop.getDesktop().browse(new URI(sUrl));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
