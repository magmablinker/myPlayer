package util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

	public static boolean isEmptyDirectory(Path dir) {
	    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(dir)) {
	        return !dirStream.iterator().hasNext();
	    } catch (IOException e) {
	    	return false;
		}
	}
	
}
