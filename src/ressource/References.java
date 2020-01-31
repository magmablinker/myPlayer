package ressource;

import controller.DirectoryWatchService;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.AudioEqualizer;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import model.FileTreeItem;
import view.MusicPanel;

public class References {
	
	public static Stage stage = null;

	public static TreeView<String> directoryView = null;
	public static TreeView<String> playlistView = null;

	public static MusicPanel musicPanel = null;
	
	public static Slider volumeSlider = null;
	
	public static FileTreeItem currentlyPlayingItem = null;
	
	public static Button bPlay = null;
	
	public static Label songPlayingAlbum;
	public static Label songPlayingTitleLabel = null;
	public static Label songPlayingArtistLabel = null;
	
	public static StackPane mediaStack = null;
	public static ImageView coverImage = null;
	
	public static MediaPlayer mediaPlayer = null;
	
	public static CheckBox checkBoxShuffle = null;
	public static CheckBox checkBoxRepeat = null;
	
	public static Label labelTimeIndicator = null;
	public static ProgressBar mediaProgressBar = null;

	public static DirectoryWatchService directoryWatchService = null;

	public static AudioEqualizer audioEqualizer = null;
	
}
