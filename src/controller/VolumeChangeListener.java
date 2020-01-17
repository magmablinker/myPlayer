package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ressource.References;

public class VolumeChangeListener implements ChangeListener<Number> {

	@Override
	public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
		References.mediaPlayer.setVolume((double) newValue / 100);
	}

}
