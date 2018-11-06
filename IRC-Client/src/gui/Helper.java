package gui;

import javafx.scene.control.TreeItem;

class Helper {
	
	public static boolean isEmptyOrNull(final String s) {
		return s == null || s.trim().isEmpty();		
	}
	
	//Generates a new branch for the server-treeview
	public static TreeItem<String> makeBranch(String title, TreeItem<String> parent){
			TreeItem<String> item = new TreeItem<String>(title);
			parent.getChildren().add(item);
			return item;
	}

}
