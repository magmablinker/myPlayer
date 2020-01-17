package view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MusicPanel extends BorderPane {

	private Slider volumeSlider;
	private Label songPlaying;
	
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
		// TODO Auto-generated method stub
		return null;
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
	
}
