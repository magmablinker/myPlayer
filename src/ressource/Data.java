package ressource;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Data {
	
	public static ArrayList<String> DIRECTORIES = new ArrayList<String>();
	public static ArrayList<String> PLAYLISTS = new ArrayList<String>();
	public static ImageView FOLDER_ICON = new ImageView(new Image(Data.class.getResourceAsStream("img/directory.png")));
	
}
