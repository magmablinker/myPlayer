package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.scene.media.EqualizerBand;

public class EqualizerPreset implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String name;
	private String preset;
	
	private ArrayList<EqualizerBand> bands = new ArrayList<EqualizerBand>();
	
	public EqualizerPreset(String name, String preset) {
		this.name = name;
		this.loadStringPreset(preset);
	}
	
	
	public void loadStringPreset(String preset) {
		StringTokenizer tokenizer = new StringTokenizer(preset, ";");
		
		if(bands.size() > 0)
			bands.clear();
		
		String token;
		while(tokenizer.hasMoreTokens()) {
			token = tokenizer.nextToken();
			EqualizerBand band = new EqualizerBand();
			band.setGain(Double.valueOf(token));
			bands.add(band);
		}
		
		this.preset = preset;
	}


	public String getName() {
		return name;
	}
	
	public ArrayList<EqualizerBand> getBands() {
		return bands;
	}
	
	public void setPreset(String preset) {
		this.preset = preset;
		// ?? loadStringPreset(preset);
	}
	
	@Override
	public String toString() {
		return name;
	}

	public void setBands(ArrayList<EqualizerBand> bands) {
		this.bands = bands;
	}
	
	public String getStringPreset() {
		return this.preset;
	}
	
}
