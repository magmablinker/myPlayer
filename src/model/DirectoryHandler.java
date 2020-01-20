package model;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;

import controller.DirectoryWatchService;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.Permissions;
import util.Util;

public class DirectoryHandler implements IDataHandler {

	private DirectoryWatchService directoryWatchService;

	public DirectoryHandler(DirectoryWatchService watchService) {
		super();
		this.directoryWatchService = watchService;
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

				this.directoryWatchService.registerWatchService(directory.toPath(), node);

				File[] fileList = directory.listFiles();

				// TODO fix weird bug with empty directory appearing as parent???
				// Problem: 2 directories have the same name
				for (File file : fileList) {
					createTreeView(node, file);
				}

			} else {
				root.getChildren().add(new TreeItem<String>("Directory '" + directory.getName() + "' not found"));
			}
		}

	}

	private void createTreeView(TreeItem<String> root, File file) {

		if (file.isDirectory()) {
			Path dir = file.toPath();

			TreeItem<String> node = Util.generateTreeNode(file);
			root.getChildren().add(node);

			this.directoryWatchService.registerWatchService(dir, node);

			for (File f : file.listFiles()) {
				createTreeView(node, f);
			}
		} else if (Arrays.asList(Permissions.FILETYPES_ALLOWED)
				.contains(file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()))) {
			root.getChildren().add(Util.generateTreeNode(file));
		}
	}

	@Override
	public boolean save() {
		return false;
	}

}
