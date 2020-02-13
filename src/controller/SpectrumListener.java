package controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.paint.Color;
import ressource.References;
import view.VisualizerPane;

public class SpectrumListener implements AudioSpectrumListener {

	private VisualizerPane pane;
	private GraphicsContext gc;

	public SpectrumListener(VisualizerPane pane) {
		this.pane = pane;
		this.gc = pane.getGc();
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		
		gc.setFill(Color.FORESTGREEN);
		
		pane.clearCanvas();
		
		for (int i = 0; i < magnitudes.length; i++) {
			gc.fillRect(75 * i, 
						pane.getCanvas().getHeight() - 20,
					   (pane.getCanvas().getWidth() / magnitudes.length) - 15,
						magnitudes[i] - References.mediaPlayer.getAudioSpectrumThreshold() * 2 + 20);
		}

	}

}
