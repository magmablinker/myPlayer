package util;

import java.io.File;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import model.AllowedFileFilter;
import model.FileTreeItem;

public class Util {

	public static void createDirectoryView(File[] fileList, TreeItem<String> node) {
		for (File file : fileList) {
			Util.createDirectoryTreeView(node, file);
		}
	}

	private static void createDirectoryTreeView(TreeItem<String> root, File file) {
		if (file.isDirectory()) {
			TreeItem<String> node = new FileTreeItem(file);
			root.getChildren().add(node);

			for (File f : file.listFiles(new AllowedFileFilter())) {
				createDirectoryTreeView(node, f);
			}
		} else {    // ===============================
					// AllowedFileFilter does this now
					// ===============================
					// if (Arrays.asList(Permissions.FILETYPES_ALLOWED)
					// .contains(file.getName().substring(file.getName().lastIndexOf(".") + 1,
					// file.getName().length()))) {
			root.getChildren().add(new FileTreeItem(file));
		}
	}

	public static String formatDecimalToMinutes(double val) {
		int hours = (int) val / 3600;
		int remainder = (int) val - hours * 3600;
		int mins = remainder / 60;
		remainder = remainder - mins * 60;
		int secs = remainder;

		return (hours > 0) ? String.format("%d:%d:%02d", hours, mins, secs) : String.format("%d:%02d", mins, secs);
	}

	public static boolean isAlreadyInTreeView(TreeView<String> directoryView, File file) {
		for (TreeItem<String> item : directoryView.getRoot().getChildren())
			if (item instanceof FileTreeItem && ((FileTreeItem) item).getPath().equals(file.getAbsolutePath()))
				return true;

		return false;
	}

	public static boolean hasFiles(FileTreeItem item) {

		if (item.getChildren().size() > 0) {
			for (TreeItem<String> c : item.getChildren()) {
				FileTreeItem child = (FileTreeItem) c;

				if (!child.isDirectory()) {
					return true;
				}

			}

		}

		return false;

	}

}
