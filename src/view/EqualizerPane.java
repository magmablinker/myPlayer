package view;

import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class EqualizerPane extends BorderPane {

	public EqualizerPane() {
		super();
		
		this.setTop(new Label("Equalizer"));
		this.setCenter(createEqualizerSlides());
	}

	private Node createEqualizerSlides() {
		GridPane panel = new GridPane();
		
		Slider slide = new Slider();
		slide.setOrientation(Orientation.VERTICAL);
		slide.setPrefHeight(280);
		
		Label lHz1 = new Label("1-10hz");
		
		panel.add(slide, 1, 1);
		panel.add(lHz1, 1, 2);
		
		return panel;
	}
	
}
