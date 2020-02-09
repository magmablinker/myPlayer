package controller;

import javafx.animation.PauseTransition;
import javafx.geometry.Bounds;
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
		
		stage.setScene(scene);
		stage.setY(parent.getY() + (parent.getWidth() / 4));
		stage.setX(parent.getX() + (parent.getHeight() / 2));
		stage.show();
		
		delay.setOnFinished(event -> stage.close());
		delay.play();
	}
	
}
