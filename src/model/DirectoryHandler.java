package model;

import java.io.File;

import controller.DirectoryWatchService;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.References;
import util.Util;

public class DirectoryHandler implements IDataHandler {

	public DirectoryHandler(DirectoryWatchService watchService) {
		super();
		References.directoryWatchService = watchService;
	}

	@Override
	public void load(TreeItem<String> root) {

		Data.DIRECTORIES.add("Z:\\musik");
		//Data.DIRECTORIES.add("C:\\Users\\laurent\\Desktop\\music");

		for (int i = 0; i < Data.DIRECTORIES.size(); i++) {
			File directory = new File(Data.DIRECTORIES.get(i));

			if (directory.isDirectory()) {
				TreeItem<String> node = Util.generateTreeNode(directory);

				root.getChildren().add(node);

				File[] fileList = directory.listFiles();

				Util.createDirectoryView(fileList, node);
			} else {
				root.getChildren().add(new TreeItem<String>("Directory '" + directory.getName() + "' not found"));
			}
		}

	}

	@Override
	public boolean save() {
		return false;
	}

}
