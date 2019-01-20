package Platform.DashConsole;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import com.google.gson.reflect.TypeToken;

import Platform.DashConsole.model.OV_Issue;
import Platform.util.OI_TreeEncoder;

public class OV_IssueEncoder extends OI_TreeEncoder {

	HashMap<Integer, DefaultMutableTreeNode> map = new HashMap<Integer, DefaultMutableTreeNode>();

	@Override
	public MutableTreeNode decodeToTree(String jString) {
		map.clear();
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type listType = new TypeToken<List<OV_Issue>>() {
		}.getType();
		List<OV_Issue> list = gson.fromJson(jString, listType);
		System.out.println("decodeNode. =" + list.size());
		OV_Issue task = list.get(0);
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(list.get(0));
		if (task.getId() > 0) {
			map.put(task.getId(), top);
		}
		for (int i = 1; i < list.size(); i++) {
			task = list.get(i);
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(task);
			if (task.getId() > 0) {
				map.put(task.getId(), node);
			}
			if (task.getParent_id() > 0) {
				DefaultMutableTreeNode pNode = map.get(task.getParent_id());
				if (pNode != null) {
					pNode.add(node);
					continue;
				}
			}
			top.add(node);
		}
		return top;
	}

	@Override
	public String encodeFromTree(DefaultMutableTreeNode node) {
		Object userObject = node.getUserObject();
		String s = gson.toJson(userObject, OV_Issue.class);
		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node.getChildAt(i);
			System.out.println("encode count=" + node.getChildCount());
			s += "," + encodeFromTree(child);
		}
		return new String(s); // 마지막에 []
	}

}
