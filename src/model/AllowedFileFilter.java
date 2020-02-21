package model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import ressource.Permissions;

public class AllowedFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return new File(dir.getPath() + File.separator + name).isDirectory() || Arrays.asList(Permissions.FILETYPES_ALLOWED)
				.contains(name.substring(name.lastIndexOf(".") + 1, name.length()));
	}

}
