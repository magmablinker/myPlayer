package model;

import javafx.scene.control.TreeItem;

public interface IDataHandler {

	public void load(TreeItem<String> root);
	
	public boolean save();
	
}
