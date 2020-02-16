package model;

import java.io.File;

import controller.DirectoryWatchService;
import javafx.scene.control.TreeItem;
import ressource.Data;
import ressource.References;
import util.Util;

public class DirectoryHandler implements ITreeDataHandler {

	public DirectoryHandler(DirectoryWatchService watchService) {
		super();
		References.directoryWatchService = watchService;
	}

	@Override
	public void load(TreeItem<String> root) {

		Data.DIRECTORIES.add("Z:\\musik");
		//Data.DIRECTORIES.add("D:\\pmma\\Benutzer\\Desktop");
		Data.DIRECTORIES.add("C:\\Users\\laurent\\Desktop\\music");

		for (int i = 0; i < Data.DIRECTORIES.size(); i++) {
			File directory = new File(Data.DIRECTORIES.get(i));

			if (directory.isDirectory()) {
				if(directory.listFiles(new AllowedFileFilter()).length > 0) {
					TreeItem<String> node = Util.generateTreeNode(directory);

					root.getChildren().add(node);

					File[] fileList = directory.listFiles(new AllowedFileFilter());

					Util.createDirectoryView(fileList, node);	
				}
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
