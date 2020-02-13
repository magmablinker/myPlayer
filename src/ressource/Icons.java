package ressource;

import java.io.InputStream;

public class Icons {
	
	public static String ICON_DIR = "img/directory.png";
	public static String ICON_FILE = "img/file.png";
	public static String ICON_SPEAKER = "img/speaker.png";
	
	public static String DEFAULT_COVER = "img/defaultcover.jpg";
	
	public static String ICON_PLAY = "img/play.png";
	public static String ICON_PAUSE = "img/pause.png";
	public static InputStream ICON_NEXT = Icons.class.getResourceAsStream("img/next.png");
	public static InputStream ICON_PREVIOUS = Icons.class.getResourceAsStream("img/previous.png");
	
	public static InputStream ICON_SEARCH = Icons.class.getResourceAsStream("img/search.png");
		
}
