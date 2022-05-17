package edu.hcmiu.minesweeper;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainMenu extends VBox {
    public MainMenu() {
        Text title = new Text();
        title.setText("Minesweeper");
        title.setStyle("-fx-font: normal bold 70px 'serif' ");
        title.setFill(Color.BLUE);
        title.setStrokeWidth(1);
        title.setStroke(Color.GRAY);

        Button playButton = new Button("Play");
        playButton.setOnAction(actionEvent -> getScene().setRoot(new GameSetting()));

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(actionEvent -> Platform.exit());

        super.getChildren().addAll(title, playButton, quitButton);

        setMargin(title, new Insets(50, 50, 50, 50));
        setMargin(playButton, new Insets(5, 20, 5, 20));
        setMargin(quitButton, new Insets(5, 20, 50, 20));
        setAlignment(Pos.CENTER);
    }
}
