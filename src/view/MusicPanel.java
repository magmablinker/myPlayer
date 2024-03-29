package view;

import controller.CheckBoxCheckListener;
import controller.NextHandler;
import controller.PlayActionHandler;
import controller.PreviousHandler;
import controller.ProgressMouseEventHandler;
import controller.VolumeChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ressource.Icons;
import ressource.References;

public class MusicPanel extends BorderPane {

	public MusicPanel() {
		super();
		this.setCenter(createCenter());
		this.setBottom(createBottom());
	}

	private Node createCenter() {
		BorderPane pane = new BorderPane();
		ImageView cover = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.DEFAULT_COVER)));

		cover.fitHeightProperty().bind(References.stage.heightProperty().divide(2));
		cover.fitWidthProperty().bind(References.stage.widthProperty().divide(2));
		cover.setPreserveRatio(true);

		References.coverImage = cover;
		
		Reflection reflection = new Reflection();
		reflection.setTopOpacity(0.4);
		reflection.setTopOffset(5);
		reflection.setBottomOpacity(0.0);
		reflection.setFraction(0.4);

		References.coverImage.setEffect(reflection);

		GridPane bottomPane = new GridPane();

		Label songPlayingAlbum = new Label("");
		References.songPlayingAlbum = songPlayingAlbum;

		Label songPlaying = new Label("No song playing");
		References.songPlayingTitleLabel = songPlaying;

		Label songPlayingArtist = new Label("");
		References.songPlayingArtistLabel = songPlayingArtist;

		Label labelTimeIndicator = new Label("00:00 / 00:00");

		ProgressBar progressBar = new ProgressBar();
		progressBar.setPrefWidth(410);
		progressBar.setProgress(0);
		progressBar.setOnMouseClicked(new ProgressMouseEventHandler());

		References.labelTimeIndicator = labelTimeIndicator;
		References.mediaProgressBar = progressBar;

		bottomPane.add(songPlaying, 1, 1);
		bottomPane.add(songPlayingAlbum, 1, 2);
		bottomPane.add(songPlayingArtist, 1, 3);
		bottomPane.add(labelTimeIndicator, 1, 4);
		bottomPane.add(progressBar, 1, 5);
		bottomPane.getStyleClass().add("margin-8-no-border");
		bottomPane.setAlignment(Pos.CENTER);

		pane.setCenter(cover);
		pane.setBottom(bottomPane);

		return pane;
	}

	private Node createBottom() {
		GridPane grid = new GridPane();
		grid.getStyleClass().add("margin-8-no-border");
		
		GridPane checkBoxPane = new GridPane();

		CheckBox cbShuffle = new CheckBox("Shuffle");
		cbShuffle.getStyleClass().add("margin-8-no-border");

		cbShuffle.setOnAction(new CheckBoxCheckListener());

		CheckBox cbRepeat = new CheckBox("Repeat");
		cbRepeat.getStyleClass().add("margin-8-no-border");

		References.checkBoxShuffle = cbShuffle;
		References.checkBoxRepeat = cbRepeat;

		checkBoxPane.add(cbShuffle, 1, 1);
		checkBoxPane.add(cbRepeat, 1, 2);

		// Buttons
		Button bPrev = new Button();
		ImageView imageViewPrev = new ImageView(new Image(Icons.ICON_PREVIOUS));
		imageViewPrev.setFitHeight(50);
		imageViewPrev.setFitWidth(50);
		imageViewPrev.setPreserveRatio(true);
		bPrev.setGraphic(imageViewPrev);
		bPrev.getStyleClass().addAll("margin-8", "button-icon");
		bPrev.setOnAction(new PreviousHandler());

		Button bPlay = new Button();
		ImageView imageViewPlay = new ImageView(new Image(Icons.class.getResourceAsStream(Icons.ICON_PLAY)));
		imageViewPlay.setFitHeight(50);
		imageViewPlay.setFitWidth(50);
		imageViewPlay.setPreserveRatio(true);
		bPlay.setGraphic(imageViewPlay);
		bPlay.getStyleClass().addAll("margin-8", "button-icon");
		bPlay.setOnAction(new PlayActionHandler());
		References.bPlay = bPlay;

		Button bNext = new Button();
		ImageView imageViewNext = new ImageView(new Image(Icons.ICON_NEXT));
		imageViewNext.setFitWidth(50);
		imageViewNext.setFitHeight(50);
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
		volumeSlider.setOnScroll(e -> {
			if(e.getDeltaY() < 0) {
				volumeSlider.setValue(volumeSlider.getValue() - 5);
			} else {
				volumeSlider.setValue(volumeSlider.getValue() + 5);	
			}
		});
		
		volumeSlider.setValue(30);

		volumeControlPane.getChildren().addAll(volumeSliderLabel, volumeSlider);

		grid.add(checkBoxPane, 1, 1);
		grid.add(bPrev, 2, 1);
		grid.add(bPlay, 3, 1);
		grid.add(bNext, 4, 1);
		grid.add(volumeControlPane, 5, 1);

		grid.setAlignment(Pos.CENTER);
		
		return grid;
	}

}
