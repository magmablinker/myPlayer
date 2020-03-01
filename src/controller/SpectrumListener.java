package controller;

import java.util.Arrays;

import javafx.scene.media.AudioSpectrumListener;
import javafx.scene.paint.LinearGradient;
import ressource.Data;
import ressource.References;
import view.VisualizerPane;

public class SpectrumListener implements AudioSpectrumListener {

	private VisualizerPane pane;
	private int minHeight = 25;
	private LinearGradient lg = LinearGradient
			.valueOf("linear-gradient(from 0% 0% to 100% 100%, Crimson 0%, DodgerBlue 90%)");

	float[] buffer = createFilledBuffer(Data.currentPreset.getBands().size(), -50);

	private double x = 0;
	private double y = 0;
	private double w = 0;
	private double h = 0;

	public SpectrumListener(VisualizerPane pane) {
		this.pane = pane;
	}

	@Override
	public void spectrumDataUpdate(double timestamp, double duration, float[] magnitudes, float[] phases) {
		pane.clearCanvas();

		pane.drawCurrentlyPlayingItemText();

		for (int i = 0; i < magnitudes.length; i++) {
			
			x = (pane.getCanvas().getWidth() / magnitudes.length) * i + 5;
			w = (pane.getCanvas().getWidth() / magnitudes.length) - 10;
			h = pane.getCanvas().getHeight() - 20;
			
			if(magnitudes[i] >= buffer[i]) {
				buffer[i] = magnitudes[i];
			
				y = (pane.getCanvas().getHeight() - 20 - minHeight
						- (magnitudes[i] - References.mediaPlayer.getAudioSpectrumThreshold()) * 4 + minHeight);
			} else {
				y = (pane.getCanvas().getHeight() - 20 - minHeight
						- (buffer[i] - References.mediaPlayer.getAudioSpectrumThreshold()) * 4 + minHeight);
				buffer[i] -= 1.75;
			}

			pane.fillRect(x, y, w, h, lg);
		}

	}

	public void setToZero() {
		pane.clearCanvas();

		if (References.mediaPlayer != null)
			pane.drawCurrentlyPlayingItemText();

		for (int i = 0; i < 10; i++) {
			pane.fillRect((pane.getCanvas().getWidth() / 10) * i + 5, pane.getCanvas().getHeight() - 20,
					(pane.getCanvas().getWidth() / 10) - 10, pane.getCanvas().getHeight() - 20, lg);
		}
	}
	
	private float[] createFilledBuffer(int size, float fillValue) {
		float[] floats = new float[size];
		Arrays.fill(floats, fillValue);
		return floats;
	}

}
