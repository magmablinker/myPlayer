package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.scene.media.EqualizerBand;

public class EqualizerPreset implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String preset;
	
	public EqualizerPreset(String name, String preset) {
		this.name = name;
		this.preset = preset;
	}
	
	
	public String getName() {
		return name;
	}
	
	public ArrayList<EqualizerBand> getBands() {
		ArrayList<EqualizerBand> bands = new ArrayList<EqualizerBand>();
		StringTokenizer tokenizer = new StringTokenizer(this.preset, ";");
		
		String token;
		while(tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			EqualizerBand band = new EqualizerBand();
			band.setGain(Double.valueOf(token));
			bands.add(band);
		}
		
		return bands;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
