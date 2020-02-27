package view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import controller.PopupTextBuilder;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.EqualizerBand;
import javafx.stage.Stage;
import model.Database;
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
		bAdd.setOnAction(e -> {
			TextInputDialog dialog = new TextInputDialog();
			DialogPane dialogPane = dialog.getDialogPane();
			dialogPane.getStylesheets().add(Main.class.getResource("css/application.css").toExternalForm());
			((Stage) dialogPane.getScene().getWindow()).getIcons().add(new Image(Data.class.getResourceAsStream("img/cheems.png")));
			dialog.setContentText("Preset Name");
			dialog.setHeaderText("Add Preset");
			dialog.showAndWait().ifPresent(text -> {
				EqualizerPreset newPreset = new EqualizerPreset(text, "0;0;0;0;0;0;0;0;0;0;");
				comboPreset.getItems().add(newPreset);
				comboPreset.getSelectionModel().select(newPreset);
				
				ArrayList<EqualizerBand> bands = newPreset.getBands();
				
				for (int i = 0; i < sliders.length; i++) {
					sliders[i].setValue(bands.get(i).getGain());

					if (References.mediaPlayer != null)
						References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(bands.get(i).getGain());

				}
				
			});
		});
		
		Button bSave = new Button("Save");
		bSave.setOnAction(e -> {
			if (comboPreset.getSelectionModel().getSelectedIndex() > -1) {
				EqualizerPreset selectedPreset = comboPreset.getSelectionModel().getSelectedItem();
				selectedPreset.setPreset(getPresetString());
				
				EqualizerDataHandler edh = new EqualizerDataHandler(comboPreset);
				edh.save();
				
				PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Config has been saved", 2, "green");
			}
		});
		bSave.getStyleClass().add("margin-4");
		bSave.setPrefWidth(100);

		Button bLoad = new Button("Load");
		bLoad.setPrefWidth(100);
		bLoad.getStyleClass().add("margin-4");
		bLoad.setOnAction(e -> {

			if (comboPreset.getSelectionModel().getSelectedIndex() > -1) {
				EqualizerPreset selectedPreset = comboPreset.getSelectionModel().getSelectedItem();
				
				selectedPreset.loadStringPreset(selectedPreset.getStringPreset());
				
				ArrayList<EqualizerBand> bands = selectedPreset.getBands();
				Data.currentPreset = selectedPreset;

				for (int i = 0; i < sliders.length; i++) {
					sliders[i].setValue(bands.get(i).getGain());

					if (References.mediaPlayer != null)
						References.mediaPlayer.getAudioEqualizer().getBands().get(i).setGain(bands.get(i).getGain());
				}
			}

		});
		
		Button bDelete = new Button("Delete");
		bDelete.getStyleClass().add("margin-4");
		bDelete.setPrefWidth(100);
		bDelete.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if(comboPreset.getSelectionModel().getSelectedIndex() > -1) {
					if(!comboPreset.getSelectionModel().getSelectedItem().getName().equals("Default")) {
						try {
							EqualizerPreset selectedPreset = comboPreset.getSelectionModel().getSelectedItem();
							
							Connection conn = Database.getInstance().getConn();
							String sql = "UPDATE equalizerPreset SET deleted = 1 WHERE name = ?";
							PreparedStatement pst = conn.prepareStatement(sql);
							pst.setString(1, selectedPreset.getName());
							
							pst.executeUpdate();
							pst.close();
							
							comboPreset.getItems().remove(comboPreset.getSelectionModel().getSelectedIndex());
						} catch (Exception e) {
							PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Failed to deleted the preset", 2, "red");
						}
					}
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

		Button bCopy = new Button("Copy");
		bCopy.setOnAction(e -> {
			if(comboPreset.getSelectionModel().getSelectedIndex() > -1) {
				Clipboard clipboard = Clipboard.getSystemClipboard();
				ClipboardContent content = new ClipboardContent();
				content.putString(getPresetString());
				clipboard.setContent(content);
				
				PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Config has been copied to clipboard", 2, "green");
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
				
				PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Successfully loaded config", 2, "green");
			} catch(Exception ex) {
				Data.currentPreset.loadStringPreset(Data.currentPreset.getStringPreset());
				PopupTextBuilder builder = new PopupTextBuilder(References.equalizerPaneStage, "Invalid config string!", 2, "red");
			}
		
		});
		bPaste.getStyleClass().add("margin-4");
		bPaste.setPrefWidth(100);

		box.getChildren().addAll(bAdd, bSave, bLoad, bDelete, bReset, bCopy, bPaste);

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
