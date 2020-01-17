package view;

import controller.PlayActionHandler;
import controller.VolumeChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ressource.References;

public class MusicPanel extends BorderPane {
	
	public MusicPanel() {
		super();
		
		this.setTop(createTop());
		this.setCenter(createCenter());
		this.setBottom(createBottom());
	}

	private Node createTop() {
		GridPane topPane = new GridPane();
		topPane.getStyleClass().add("margin-8-no-border");
		
		Label songPlaying = new Label("No song playing");
		References.songPlayingTitleLabel = songPlaying;
		
		Label songPlayingArtist = new Label("No artist");
		References.songPlayingArtistLabel = songPlayingArtist;
		
		topPane.add(songPlaying, 1, 1);
		topPane.add(songPlayingArtist, 1, 2);
		
		return topPane;
	}

	private Node createCenter() {
		BorderPane pane = new BorderPane();
		ImageView cover = new ImageView(new Image(getClass().getResourceAsStream("../ressource/img/defaultcover.jpg")));
		
		cover.fitHeightProperty().bind(References.stage.heightProperty().divide(2));
		cover.fitWidthProperty().bind(References.stage.widthProperty().divide(2));
		cover.setPreserveRatio(true);
		
		References.coverImage = cover;
		
		GridPane bottomPane = new GridPane();
		
		Label labelTimeIndicator = new Label("00:00 / 00:00");
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefWidth(430);
		progressBar.setProgress(0);
		
		References.labelTimeIndicator = labelTimeIndicator;
		References.mediaProgressBar = progressBar;
		
		bottomPane.add(labelTimeIndicator, 1, 1);
		bottomPane.add(progressBar, 1, 2);
		bottomPane.getStyleClass().add("margin-8-no-border");
		bottomPane.setAlignment(Pos.CENTER);
		
		pane.setCenter(cover);
		pane.setBottom(bottomPane);
		
		return pane;
	}

	private Node createBottom() {
		GridPane grid = new GridPane();
		
		CheckBox cbShuffle = new CheckBox("Shuffle");
		cbShuffle.getStyleClass().add("margin-8-no-border");
		
		CheckBox cbRepeat = new CheckBox("Repeat");
		cbRepeat.getStyleClass().add("margin-8-no-border");
		
		References.checkBoxShuffle = cbShuffle;
		References.checkBoxRepeat = cbRepeat;
		
		Button bPlay = new Button("Play");
		bPlay.getStyleClass().add("margin-8");
		bPlay.setOnAction(new PlayActionHandler());
		
		Button bPause = new Button("Pause");
		bPause.getStyleClass().add("margin-8");
		
		VBox volumeControlPane = new VBox();
		
		Label volumeSliderLabel = new Label("Volume");
		Slider volumeSlider = new Slider();
		References.volumeSlider = volumeSlider;
		
		volumeSlider.valueProperty().addListener(new VolumeChangeListener());
		volumeSlider.setValue(100);
		
		volumeControlPane.getChildren().addAll(volumeSliderLabel, volumeSlider);
		
		grid.add(cbShuffle, 1, 1);
		grid.add(cbRepeat, 2, 1);
		grid.add(bPlay, 1, 2);
		grid.add(bPause, 2, 2);
		grid.add(volumeControlPane, 3, 2);
		
		grid.setAlignment(Pos.CENTER);
		
		return grid;
	}
	

}
