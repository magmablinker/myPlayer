package controller;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
import ressource.References;

public class MetaDataChangeListener implements MapChangeListener<String, Object> {

	@Override
	public void onChanged(Change<? extends String, ? extends Object> c) {
		if (c.wasAdded()) {
			if ("artist".equals(c.getKey())) {
				References.songPlayingArtistLabel.setText(c.getValueAdded().toString());
			} else if ("title".equals(c.getKey())) {
				References.songPlayingTitleLabel.setText((String) c.getValueAdded().toString());
			} else if ("album".equals(c.getKey())) {
				References.songPlayingAlbum.setText(c.getValueAdded().toString());
			} else if ("image".equals(c.getKey())) {
				References.coverImage.setImage((Image) c.getValueAdded());
			}
		}
	}

}
