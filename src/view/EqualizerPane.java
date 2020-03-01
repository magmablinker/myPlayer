package view;

import controller.CopyPresetHandler;
import controller.DeletePresetHandler;
import controller.LoadPresetHandler;
import controller.NewPresetHandler;
import controller.PastePresetHandler;
import controller.SavePresetHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.EqualizerBand;
import model.EqualizerDataHandler;
import model.EqualizerPreset;
import ressource.Data;
import ressource.References;

public class EqualizerPane extends BorderPane {

	private Slider[] sliders;
	private ComboBox<EqualizerPreset> comboPreset;

	public EqualizerPane() {
		super();

		this.setTop(createPresetDropDown());
		this.setCenter(createEqualizerSlides());
		this.setBottom(createButtons());
	}

	private Node createEqualizerSlides() {
		HBox panel = new HBox();
		panel.setAlignment(Pos.CENTER);
		BorderPane[] panes = new BorderPane[10];
		sliders = new Slider[10];

		String[] hzTexts = { "19Hz", "39Hz", "78Hz", "156Hz", "312Hz", "625Hz", "1.25kHz", "2.5kHz", "5kHz", "10kHz" };

		for (int i = 0; i < 10; i++) {
			final int fi = i;
			panes[i] = new BorderPane();
			double currentValue = Data.currentPreset.getBands().get(fi).getGain();
			sliders[i] = new Slider();
			sliders[i].setPrefHeight(200);
			sliders[i].setPrefWidth(100);
			sliders[i].setMin(EqualizerBand.MIN_GAIN);
			sliders[i].setMax(EqualizerBand.MAX_GAIN);
			sliders[i].getStyleClass().add("margin-4-no-border");
			sliders[i].setOrientation(Orientation.VERTICAL);
			sliders[i].setValue(currentValue);
			sliders[i].setOnScroll(e -> {
				if(e.getDeltaY() > 0) {
					sliders[fi].setValue(sliders[fi].getValue() + 2);
				} else {
					sliders[fi].setValue(sliders[fi].getValue() - 2);
				}
			});
			
			sliders[i].valueProperty().addListener(new ChangeListener<Number>() {
				
				@Override
				public void changed(ObservableValue<? extends Number> arg0, Number oldValue, Number newValue) {
					if (References.mediaPlayer != null) {
						References.mediaPlayer.getAudioEqualizer().getBands().get(fi).setGain(newValue.doubleValue());
					}

					Data.currentPreset.getBands().get(fi).setGain(newValue.doubleValue());
				}
				
			});

			Label hzText = new Label(hzTexts[i]);
			hzText.getStyleClass().add("margin-4-no-border");
			
			panes[i].setCenter(sliders[i]);
			panes[i].setBottom(hzText);
		}

		panel.getChildren().addAll(panes);

		return panel;
	}
	
	private Node createPresetDropDown() {
		VBox panel = new VBox();
		comboPreset = new ComboBox<EqualizerPreset>();
		comboPreset.setPrefWidth(200);
		comboPreset.getStyleClass().add("margin-4");

		EqualizerDataHandler edh = new EqualizerDataHandler(comboPreset);
		edh.load();

		comboPreset.getSelectionModel().select(Data.currentPreset);
		
		panel.getChildren().addAll(comboPreset);

		return panel;
	}

	private Node createButtons() {
		HBox box = new HBox();
		box.setAlignment(Pos.CENTER_LEFT);
		
		Button bAdd = new Button("New");
		bAdd.setPrefWidth(100);
		bAdd.getStyleClass().add("margin-4");
		bAdd.setOnAction(new NewPresetHandler(this));
		
		Button bSave = new Button("Save");
		bSave.getStyleClass().add("margin-4");
		bSave.setPrefWidth(100);
		bSave.setOnAction(new SavePresetHandler(this));

		Button bLoad = new Button("Load");
		bLoad.setPrefWidth(100);
		bLoad.getStyleClass().add("margin-4");
		bLoad.setOnAction(new LoadPresetHandler(this));
		
		Button bDelete = new Button("Delete");
		bDelete.getStyleClass().add("margin-4");
		bDelete.setPrefWidth(100);
		bDelete.setOnAction(new DeletePresetHandler(this));

		Button bReset = new Button("Reset");
		bReset.getStyleClass().add("margin-4");
		bReset.setPrefWidth(100);
		bReset.setOnAction(e -> { for (int i = 0; i < 10; i++) sliders[i].setValue(0); });

		Button bCopy = new Button("Copy");
		bCopy.getStyleClass().add("margin-4");
		bCopy.setPrefWidth(100);
		bCopy.setOnAction(new CopyPresetHandler(this));
		
		Button bPaste = new Button("Paste");
		bPaste.getStyleClass().add("margin-4");
		bPaste.setPrefWidth(100);
		bPaste.setOnAction(new PastePresetHandler(this));

		box.getChildren().addAll(bAdd, bSave, bLoad, bDelete, bReset, bCopy, bPaste);

		return box;
	}
	
	public String getPresetString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sliders.length; i++) {
			sb.append(sliders[i].getValue() + ";");
		}
		
		return sb.toString();
	}

	public ComboBox<EqualizerPreset> getComboPreset() {
		return this.comboPreset;
	}

	public Slider[] getSliders() {
		return this.sliders;
	}
	
}
