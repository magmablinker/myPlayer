package model;

import javafx.scene.control.TreeItem;

public interface ITreeDataHandler  {

	public void load(TreeItem<String> root);
	
	public void save();
	
	public boolean isAlreadySaved(String item);
	
}
