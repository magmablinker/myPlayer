package view;

import controller.NextHandler;
import controller.PauseActionHandler;
import controller.PlayActionHandler;
import controller.PreviousHandler;
import controller.VolumeChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
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
		
		this.setCenter(createCenter());
		this.setBottom(createBottom());
	}

	private Node createCenter() {
		BorderPane pane = new BorderPane();
		ImageView cover = new ImageView(new Image(getClass().getResourceAsStream("../ressource/img/defaultcover.jpg")));
		
		cover.fitHeightProperty().bind(References.stage.heightProperty().divide(2));
		cover.fitWidthProperty().bind(References.stage.widthProperty().divide(2));
		cover.setPreserveRatio(true);
		
		References.coverImage = cover;
		
		GridPane bottomPane = new GridPane();
		
		Label songPlaying = new Label("No song playing");
		References.songPlayingTitleLabel = songPlaying;
		
		Label songPlayingArtist = new Label("");
		References.songPlayingArtistLabel = songPlayingArtist;		
		
		Label labelTimeIndicator = new Label("00:00 / 00:00");
		
		ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefWidth(430);
		progressBar.setProgress(0);
		
		References.labelTimeIndicator = labelTimeIndicator;
		References.mediaProgressBar = progressBar;
		
		bottomPane.add(songPlaying, 1, 1);
		bottomPane.add(songPlayingArtist, 1, 2);
		bottomPane.add(labelTimeIndicator, 1, 3);
		bottomPane.add(progressBar, 1, 4);
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
		
		// Buttons
		Button bPrev = new Button();
		ImageView imageViewPrev = new ImageView(new Image(MusicPanel.class.getResourceAsStream("../ressource/img/previous.png")));
		imageViewPrev.setFitHeight(32);
		imageViewPrev.setFitWidth(32);
		imageViewPrev.setPreserveRatio(true);
		bPrev.setGraphic(imageViewPrev);
		bPrev.getStyleClass().addAll("margin-8", "button-icon");
		bPrev.setOnAction(new PreviousHandler());
		
		Button bPlay = new Button();
		ImageView imageViewPlay = new ImageView(new Image(MusicPanel.class.getResourceAsStream("../ressource/img/play.png")));
		imageViewPlay.setFitHeight(32);
		imageViewPlay.setFitWidth(32);
		imageViewPlay.setPreserveRatio(true);
		bPlay.setGraphic(imageViewPlay);
		bPlay.getStyleClass().addAll("margin-8", "button-icon");
		bPlay.setOnAction(new PlayActionHandler());
		
		Button bPause = new Button();
		ImageView imageViewPause = new ImageView(new Image(MusicPanel.class.getResourceAsStream("../ressource/img/pause.png")));
		imageViewPause.setFitHeight(32);
		imageViewPause.setFitWidth(32);
		imageViewPause.setPreserveRatio(true);
		bPause.setGraphic(imageViewPause);
		bPause.getStyleClass().addAll("margin-8", "button-icon");
		bPause.setOnAction(new PauseActionHandler());
		
		Button bNext = new Button();
		ImageView imageViewNext = new ImageView(new Image(MusicPanel.class.getResourceAsStream("../ressource/img/next.png")));
		imageViewNext.setFitHeight(32);
		imageViewNext.setFitWidth(32);
		imageViewNext.setPreserveRatio(true);
		bNext.setGraphic(imageViewNext);
		bNext.getStyleClass().addAll("margin-8", "button-icon");
		bNext.setOnAction(new NextHandler());
		
		VBox volumeControlPane = new VBox();
		volumeControlPane.setAlignment(Pos.CENTER_LEFT);
		
		Label volumeSliderLabel = new Label("Volume");
		Slider volumeSlider = new Slider();
		References.volumeSlider = volumeSlider;
		
		volumeSlider.valueProperty().addListener(new VolumeChangeListener());
		volumeSlider.setValue(50);
		
		volumeControlPane.getChildren().addAll(volumeSliderLabel, volumeSlider);
		
		grid.add(cbShuffle, 1, 1);
		grid.add(cbRepeat, 2, 1);
		grid.add(bPrev, 1, 2);
		grid.add(bPause, 2, 2);
		grid.add(bPlay, 3, 2);
		grid.add(bNext, 4, 2);
		grid.add(volumeControlPane, 5, 2);
		
		grid.setAlignment(Pos.CENTER);
		
		return grid;
	}
	

}
