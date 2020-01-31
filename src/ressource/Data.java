package ressource;

import java.util.ArrayList;

import model.EqualizerPreset;
import model.FileTreeItem;

public class Data {
	
	public static ArrayList<String> DIRECTORIES = new ArrayList<String>();
	public static ArrayList<String> PLAYLISTS = new ArrayList<String>();
	
	public static int SONG_QUEUE_POSITION = 0;
	public static ArrayList<FileTreeItem> SONG_QUEUE = new ArrayList<FileTreeItem>();
	
	public static EqualizerPreset currentPreset = new EqualizerPreset("Default", "0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;0.0;");
	
}
