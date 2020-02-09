package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class PopupText extends HBox {

	public PopupText(Text text) {
		super();
		Label label = new Label(text.getText());
		label.setAlignment(Pos.CENTER);
		label.setFont(Font.font(16));
		HBox.setMargin(this, new Insets(12));
		this.getChildren().add(label);
	}

}
