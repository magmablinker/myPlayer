package ressource;

import java.util.ArrayList;

import model.EqualizerPreset;
import model.FileTreeItem;

public class Data {
	
	public static ArrayList<String> DIRECTORIES = new ArrayList<String>();
	public static ArrayList<String> PLAYLISTS = new ArrayList<String>();
	
	public static EqualizerPreset defaultPreset = new EqualizerPreset("Default", "4.475675675675674;4.28108108108108;3.5027027027027025;2.140540540540538;-0.38918918918918877;-2.9189189189189193;-0.19459459459459794;1.556756756756755;0.9729729729729755;-1.1675675675675699;");
	public static EqualizerPreset currentPreset = defaultPreset;
}
