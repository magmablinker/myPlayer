package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MusicPanel extends BorderPane {

	private Slider volumeSlider;
	private Label songPlaying;
	private ImageView cover;
	
	public MusicPanel() {
		super();
		
		this.setTop(createTop());
		this.setCenter(createCenter());
		this.setBottom(createBottom());
	}

	private Node createTop() {
		songPlaying = new Label("No song playing");
		songPlaying.getStyleClass().add("margin-8-no-border");
		return songPlaying;
	}

	private Node createCenter() {
		BorderPane pane = new BorderPane();
		cover = new ImageView(new Image(getClass().getResourceAsStream("../ressource/img/defaultcover.jpg")));
		
		cover.setFitWidth(256);
		cover.setFitHeight(256);
		
		pane.setCenter(cover);
		
		return pane;
	}

	private Node createBottom() {
		GridPane grid = new GridPane();
		
		CheckBox cbShuffle = new CheckBox("Shuffle");
		cbShuffle.getStyleClass().add("margin-8-no-border");
		
		CheckBox cbRepeat = new CheckBox("Repeat");
		cbRepeat.getStyleClass().add("margin-8-no-border");
		
		Button bPlay = new Button("Play");
		bPlay.getStyleClass().add("margin-8");
		bPlay.setOnAction(new PlayActionHandler());
		
		Button bPause = new Button("Pause");
		bPause.getStyleClass().add("margin-8");
		
		VBox volumeControlPane = new VBox();
		
		Label volumeSliderLabel = new Label("Volume");
		volumeSlider = new Slider();
		
		volumeControlPane.getChildren().addAll(volumeSliderLabel, volumeSlider);
		
		grid.add(cbShuffle, 1, 1);
		grid.add(cbRepeat, 2, 1);
		grid.add(bPlay, 1, 2);
		grid.add(bPause, 2, 2);
		grid.add(volumeControlPane, 3, 2);
		
		grid.setAlignment(Pos.CENTER);
		
		return grid;
	}
	
	public Label getSongPlayingLabel() {
		return this.songPlaying;
	}
	
	public ImageView getCover() {
		return this.cover;
	}
	
}
