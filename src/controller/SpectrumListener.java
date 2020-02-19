package controller;

import javafx.scene.media.AudioSpectrumListener;
import ressource.References;
import view.VisualizerPane;

public class SpectrumListener implements AudioSpectrumListener {
	
	private VisualizerPane pane;
	private int minHeight = 25;
	
	public SpectrumListener(VisualizerPane pane) {
		this.pane = pane;
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		pane.clearCanvas();
		
		for (int i = 0; i < magnitudes.length; i++) {
			pane.fillRect((pane.getCanvas().getWidth() / magnitudes.length) * i, 
					     (pane.getCanvas().getHeight() - 20 - minHeight - (magnitudes[i] - References.mediaPlayer.getAudioSpectrumThreshold()) * 3 + minHeight),
					     (pane.getCanvas().getWidth() / magnitudes.length) - 15,
					      pane.getCanvas().getHeight() - 20);
		}

	}
	
	public void setToZero() {
		pane.clearCanvas();
		for (int i = 0; i < 10; i++) {
			pane.fillRect((pane.getCanvas().getWidth() / 10) * i,
						   pane.getCanvas().getHeight() - 20,
					      (pane.getCanvas().getWidth() / 10) - 15,
					       pane.getCanvas().getHeight() - 20);
		}
	}

}
