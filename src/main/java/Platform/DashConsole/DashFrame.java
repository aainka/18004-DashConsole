package Platform.DashConsole;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.tree.DefaultMutableTreeNode;

import Platform.util.DashTree;

public class DashFrame extends JFrame {

//	DashController controller = new DashController();
	JTable tableView = new JTable();

	public void init() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 400;
		int height = (int) screenSize.getHeight() - 50;

		Dimension windowSize = new Dimension(width, height);
		this.setSize(windowSize);

		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		this.setLocation(screenSize.width - width, 0);
		this.setResizable(true);
		build(this.getContentPane());
		this.setTitle("즉시2,반드시,끝까지,5Y,80:20,마감");
		this.setVisible(true);
		
	}

	public void build(Container container) {
	 
		RedTableModel tableModel = new RedTableModel( );
		tableModel.test();
		container.setLayout(new BorderLayout());
		tableView.setModel(tableModel);
		container.add(BorderLayout.CENTER, new JScrollPane(tableView));
	}

	JFrame frame = this;

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			//controller.closeWindow();
			System.exit(0);
		}
	}

	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

	public static final void main(String[] args) {
		System.out.println("RED XLS");
	 	new DashFrame().init();
	}
}