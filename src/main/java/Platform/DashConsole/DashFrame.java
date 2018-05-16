package Platform.DashConsole;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.tree.DefaultMutableTreeNode;

import Platform.util.DashTree;

public class DashFrame extends JFrame {

	DashController controller = new DashController();

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

		// JEditorPane pan = new JEditorPane();
		// pan.setFont(new Font("���� ���", 0, 15));
		// pan.setText(new SPExcelLoader().test2());

		this.setVisible(true);

		// new DebugConsole();
		// for (int i = 0; i < 100; i++) {
		// DebugConsole.println("xxxx");
		// }
	}

	public void build(Container container) {

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Redmine");
		DashTreeModel treeModel = new DashTreeModel(root);
		DashTree tree = new DashTree();
		tree.setModel(treeModel);
		JScrollPane sp = new JScrollPane(tree);
		container.add(sp);

		// JTabbedPane tabbedPane = new JTabbedPane();
		// tabbedPane.addTab("aaa", controller.buildUi());
		// container.add(BorderLayout.CENTER, tabbedPane);
		// container.add(BorderLayout.NORTH, initShortCutOnTop());
	}

	JFrame frame = this;

	// private JComponent initShortCutOnTop() {
	// TopShortCut menubar = new TopShortCut();
	// menubar.add2("SAVE");
	// menubar.add2("PRINT");
	// menubar.add2("ETC");
	// menubar.add2("NOTE");
	// menubar.addActionListener(controller);
	// controller.setButtonMenu(menubar);
	// return menubar;
	// }

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			controller.closeWindow();
			System.exit(0);
		}
	}

	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

	public static final void main(String[] args) {
		// DataTreeModel treeModel = new StringTreeModel();
		new DashFrame().init();
	}
}