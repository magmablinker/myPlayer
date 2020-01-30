package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.EqualizerBand;
import ressource.References;

public class EqualizerPane extends BorderPane {

	private Slider[] sliders;
	
	public EqualizerPane() {
		super();

		this.setCenter(createEqualizerSlides());
		this.setBottom(createButtons());
	}

	private Node createEqualizerSlides() {
		HBox panel = new HBox();
		panel.setAlignment(Pos.CENTER);
		BorderPane[] panes = new BorderPane[10];
		sliders = new Slider[10];

		for (int i = 0; i < 10; i++) {
			final int fi = i;
			panes[i] = new BorderPane();
			double currentValue = (References.mediaPlayer == null) ? 0 : References.mediaPlayer.getAudioEqualizer().getBands().get(fi).getGain(); 
			//sliders[i] = new Slider(EqualizerBand.MIN_GAIN, EqualizerBand.MAX_GAIN);
			sliders[i] = new Slider();
			sliders[i].setPrefHeight(200);
			sliders[i].setMin(EqualizerBand.MIN_GAIN);
			sliders[i].setMax(EqualizerBand.MAX_GAIN);
			sliders[i].getStyleClass().add("margin-8");
			sliders[i].setOrientation(Orientation.VERTICAL);
			sliders[i].setValue(currentValue);
			sliders[i].valueProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
					if (References.mediaPlayer != null) {
						References.mediaPlayer.getAudioEqualizer().getBands().get(fi).setGain(newValue.doubleValue());
					}
				}
			});
			
			Label hzText = new Label("Hurensohn");
			hzText.getStyleClass().add("margin-8-no-border");
			
			panes[i].setCenter(sliders[i]);
			panes[i].setBottom(hzText);
		}

		panel.getChildren().addAll(panes);

		return panel;
	}
	
	private Node createButtons() {
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER_LEFT);
		
		Button bReset = new Button("Reset");
		bReset.getStyleClass().add("margin-8");
		bReset.setPrefWidth(100);
		bReset.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent arg0) {
				for(int i = 0; i < 10; i++) {
					sliders[i].setValue(0);
				}
			}
			
		});
		
		Button bSave = new Button("Save");
		bSave.getStyleClass().add("margin-8");
		bSave.setPrefWidth(100);
		
		box.getChildren().addAll(bReset, bSave);
		
		return box;
	}

}
