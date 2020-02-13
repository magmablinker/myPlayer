package controller;

import javafx.scene.media.AudioSpectrumListener;
import ressource.References;
import view.VisualizerPane;

public class SpectrumListener implements AudioSpectrumListener {
	
	private VisualizerPane pane;
	
	public SpectrumListener(VisualizerPane pane) {
		this.pane = pane;
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		pane.clearCanvas();
		
		for (int i = 0; i < magnitudes.length; i++) {
			pane.fillRect(75 * i, 
						0,//pane.getCanvas().getHeight() - 20,
					   (pane.getCanvas().getWidth() / magnitudes.length) - 15,
						(magnitudes[i] - References.mediaPlayer.getAudioSpectrumThreshold()) * 2 + 20);
		}

	}
	
	public void setToZero() {
		pane.clearCanvas();
		for (int i = 0; i < 10; i++) {
			pane.fillRect(75 * i,
					      0,
					      (pane.getCanvas().getWidth() / 10) - 15,
					      20);
		}
	}

}
