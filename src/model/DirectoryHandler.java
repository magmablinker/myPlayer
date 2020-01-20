package model;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import controller.DirectoryWatchService;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.Permissions;
import ressource.References;
import util.Util;

public class DirectoryHandler implements IDataHandler {

	private DirectoryWatchService directoryWatchService;

	public DirectoryHandler(DirectoryWatchService watchService) {
		super();
		this.directoryWatchService = watchService;
		References.directoryWatchService = watchService;
	}

	@Override
	public void load(TreeItem<String> root) {

		// Data.DIRECTORIES.add("Z:\\musik");
		Data.DIRECTORIES.add("C:\\Users\\laurent\\Desktop\\music");

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
