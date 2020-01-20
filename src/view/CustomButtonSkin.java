package view;

import javafx.scene.control.skin.ButtonSkin;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class CustomButtonSkin extends ButtonSkin {

	public CustomButtonSkin(Button b) {
		super(b);
		
		final FadeTransition fadeIn = new FadeTransition(Duration.millis(250));
		fadeIn.setNode(b);
		fadeIn.setToValue(0.5);
		b.setOnMouseEntered(e -> fadeIn.playFromStart());
		
		final FadeTransition fadeOut = new FadeTransition(Duration.millis(250));
		fadeOut.setNode(b);
		fadeOut.setToValue(1);
		b.setOnMouseExited(e -> fadeOut.playFromStart());
	}

}
