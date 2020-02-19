package controller;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import view.PopupText;

public class PopupTextBuilder {

	public PopupTextBuilder(Stage parent, String message, double duration, String bgColor) {
		Stage stage = new Stage();
		stage.initStyle(StageStyle.TRANSPARENT);
		
		Text text = new Text(message);
		text.setFont(Font.font(16));
		
		final double textWidth = text.getBoundsInLocal().getWidth() + 16; // + 16 => for padding (8px on all sides)
		final double textHeight = text.getBoundsInLocal().getHeight() + 16;
		
		Rectangle rect = new Rectangle(textWidth, textHeight);
		rect.setArcHeight(textHeight / 4);
		rect.setArcWidth(textHeight / 4);
		
		stage.setWidth(textWidth);
		stage.setHeight(textHeight);
		
		PopupText root = new PopupText(text, bgColor);
		
		root.setAlignment(Pos.CENTER);
		root.setClip(rect);
		
		Scene scene = new Scene(root);
		scene.setFill(Color.TRANSPARENT);
		
		PauseTransition delay = new PauseTransition(Duration.seconds(duration));
		
		FadeTransition fadeIn = new FadeTransition(Duration.millis(200), root);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		
		FadeTransition fadeOut = new FadeTransition(Duration.millis(400), root);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		
		stage.setScene(scene);
		stage.setY(parent.getY() + (parent.getHeight() / 3));
		stage.setX(parent.getX() + (parent.getWidth() / 4));
		stage.setAlwaysOnTop(true);
		stage.show();
		
		fadeIn.setOnFinished(event -> delay.play());
		delay.setOnFinished(event -> fadeOut.play());
		fadeOut.setOnFinished(event -> stage.close());
		
		fadeIn.play();
	}
	
}
