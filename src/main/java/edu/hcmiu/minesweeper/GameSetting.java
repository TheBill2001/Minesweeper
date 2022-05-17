package edu.hcmiu.minesweeper;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class GameSetting extends VBox {
    public GameSetting() {
        Text title = new Text();
        title.setText("Game setting");
        title.setStyle("-fx-font: normal bold 50px 'serif' ");
        title.setFill(Color.DARKGREEN);
        title.setStrokeWidth(1);
        title.setStroke(Color.GRAY);

        Button returnButton = new Button("Return");
        returnButton.setOnAction(actionEvent -> Minesweeper.changeScene("MainMenu"));

        Button beginButton = new Button("Begin!");

        GridPane gridPane = new GridPane();

        Text rowText = new Text("Row");
        GridPane.setConstraints(rowText, 0, 0);

        Spinner<Integer> rowSpinner = new Spinner<>(10, 100, 10);
        rowSpinner.setEditable(true);
        GridPane.setConstraints(rowSpinner, 1, 0);

        Text columnText = new Text("Column");
        GridPane.setConstraints(columnText, 0, 1);

        Spinner<Integer> columnSpinner = new Spinner<>(10, 100, 10);
        columnSpinner.setEditable(true);
        GridPane.setConstraints(columnSpinner, 1, 1);

        Text mineDensityText = new Text("Mine density (%)");
        GridPane.setConstraints(mineDensityText, 0, 2);

        Spinner<Double> mineDensitySpinner = new Spinner<>(0.0, 100.0, 25.0, 0.01);
        mineDensitySpinner.setEditable(true);
        GridPane.setConstraints(mineDensitySpinner, 1, 2);

        Text undoText = new Text("Allow undo");
        GridPane.setConstraints(undoText, 0, 3);

        CheckBox undoCheckBox = new CheckBox();
        undoCheckBox.setSelected(true);
        GridPane.setConstraints(undoCheckBox, 1, 3);

        Text guessingText = new Text("Allow guessing");
        GridPane.setConstraints(guessingText, 0, 4);

        Text perlinText = new Text("Use perlin noise");
        GridPane.setConstraints(perlinText, 0, 5);

        CheckBox perlinBox = new CheckBox();
        perlinBox.setSelected(true);
        GridPane.setConstraints(perlinBox, 1, 5);

        gridPane.setHgap(5);
        gridPane.setVgap(5);
        gridPane.getChildren().addAll(rowText, rowSpinner, columnText, columnSpinner, mineDensityText, mineDensitySpinner, undoText, undoCheckBox, guessingText, perlinText, perlinBox);
        gridPane.setAlignment(Pos.CENTER);

        beginButton.setOnAction((ActionEvent event) -> {
            Minesweeper.changeScene("Game");
            Game game = (Game) Minesweeper.sceneMap.get("Game");
            game.start(rowSpinner.getValue(),columnSpinner.getValue(), mineDensitySpinner.getValue(), undoCheckBox.isSelected(), perlinBox.isSelected());
        });

        super.getChildren().addAll(title, gridPane, beginButton, returnButton);

        setMargin(title, new Insets(50, 50, 50, 50));
        setMargin(gridPane, new Insets(5, 20, 5, 20));
        setMargin(beginButton, new Insets(50, 20, 10, 20));
        setMargin(returnButton, new Insets(10, 20, 50, 20));
        setAlignment(Pos.CENTER);
    }
}
