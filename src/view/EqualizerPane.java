package view;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.EqualizerBand;
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
		comboPreset.getItems().add(Data.defaultPreset);
		comboPreset.getItems().add(new EqualizerPreset("Test", "100;100;100;0.1;0.1;0.1;0.1;0.1;0.1;0.1;"));
		comboPreset.getItems().add(new EqualizerPreset("Zwei", "8;3;10;0.1;0.1;0.1;0.1;0.1;0.1;0.1;"));
		comboPreset.setPrefWidth(200);
		comboPreset.getStyleClass().add("margin-4");
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
		bAdd.setOnAction(e -> {
			TextInputDialog dialog = new TextInputDialog();
			// TODO: Custom Dialog, preserve newly added items
			dialog.setContentText("Preset Name");
			dialog.showAndWait().ifPresent(text -> {
				EqualizerPreset newPreset = new EqualizerPreset(text, "0;0;0;0;0;0;0;0;0;0;");
				comboPreset.getItems().add(newPreset);
				comboPreset.getSelectionModel().select(newPreset);
			});
		});

		Button bLoad = new Button("Load");
		bLoad.setPrefWidth(100);
		bLoad.getStyleClass().add("margin-4");
		bLoad.setOnAction(e -> {

			if (comboPreset.getSelectionModel().getSelectedIndex() > -1) {
				EqualizerPreset selectedPreset = comboPreset.getSelectionModel().getSelectedItem();
				ArrayList<EqualizerBand> bands = selectedPreset.getBands();
				Data.currentPreset = selectedPreset;

				for (int i = 0; i < sliders.length; i++) {
					sliders[i].setValue(bands.get(i).getGain());

					if (References.mediaPlayer != null)
						References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(bands.get(i).getGain());
				}
			}

		});


		Button bReset = new Button("Reset");
		bReset.getStyleClass().add("margin-4");
		bReset.setPrefWidth(100);
		bReset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				for (int i = 0; i < 10; i++) {
					sliders[i].setValue(0);
				}
			}

		});

		Button bSave = new Button("Save");
		bSave.setOnAction(e -> {
			if (comboPreset.getSelectionModel().getSelectedIndex() > -1) {
				EqualizerPreset selectedPreset = comboPreset.getSelectionModel().getSelectedItem();
				selectedPreset.setPreset(getPresetString());
			}
		});
		bSave.getStyleClass().add("margin-4");
		bSave.setPrefWidth(100);
		
		Button bCopy = new Button("Copy");
		bCopy.setOnAction(e -> {
			if(comboPreset.getSelectionModel().getSelectedIndex() > -1) {
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putString(getPresetString());
				clipboard.setContent(content);
				
				Alert alert = new Alert(AlertType.INFORMATION, "The config string has been copied.", ButtonType.OK);
				alert.show();	
			}
		});
		bCopy.getStyleClass().add("margin-4");
		bCopy.setPrefWidth(100);
		
		Button bPaste = new Button("Paste");
		bPaste.setOnAction(e -> {
			String configString = Clipboard.getSystemClipboard().getString();
			
			try {
				Data.currentPreset.loadStringPreset(configString);
				
				for(int i = 0; i < 10; i++) {
					Double gain = Data.currentPreset.getBands().get(i).getGain();
					sliders[i].setValue(gain);
					if(References.mediaPlayer != null)
						References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(gain);
				}
				
			} catch(Exception ex) {
				Alert alert = new Alert(AlertType.ERROR, "Invalid config string", ButtonType.YES);
				alert.show();
			}
			
		});
		bPaste.getStyleClass().add("margin-4");
		bPaste.setPrefWidth(100);

		box.getChildren().addAll(bAdd, bSave, bLoad, bReset, bCopy, bPaste);

		return box;
	}
	
	private String getPresetString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < sliders.length; i++) {
			sb.append(sliders[i].getValue() + ";");
		}
		
		return sb.toString();
	}

}
