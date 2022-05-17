package edu.hcmiu.minesweeper;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.util.concurrent.ThreadLocalRandom;

public class Game extends BorderPane {
    private final GridPane gridPane = new GridPane();
    private final AnimationTimer elapseTimer;
    private int row = 0;
    private int column = 0;
    private double mineChance = 0.0;
    private boolean allowUndo = false;
    private boolean perlinNoise = true;
    private long startTimeMillis = 0;

    public Game() {
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.maxHeightProperty().bind(gridPane.heightProperty().add(2));
        scrollPane.maxWidthProperty().bind(gridPane.widthProperty().add(2));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        Label timerLabel = new Label();
        elapseTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                long elapsedTimeMillis = (System.currentTimeMillis() - startTimeMillis) / 1000;
                timerLabel.setText(String.format("Elapsed time: %02d:%02d:%02d", elapsedTimeMillis / 3600, (elapsedTimeMillis % 3600) / 60, (elapsedTimeMillis % 3600) % 60));
            }
        };

        Region paddingRegion = new Region();

        HBox controlBox = new HBox(10);
        controlBox.setPadding(new Insets(10, 10, 5, 10));
        controlBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.setOnAction(actionEvent -> {
            clear();
            Minesweeper.changeScene("GameSetting");
        });

        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");

        HBox.setHgrow(paddingRegion, Priority.ALWAYS);
        controlBox.getChildren().addAll(timerLabel, paddingRegion, undoButton, redoButton, backButton);

        HBox statusBox = new HBox(10);

        Label rowLabel = new Label("Row: " + row);
        Label columnLabel = new Label("Column: " + column);
        Label mineDensityLabel = new Label("Mine density: " + mineChance + "%");
        Label allowUndoLabel = new Label("Allow undo: " + allowUndo);
        Label perlinLabel = new Label("Perlin noise: " + perlinNoise);

        statusBox.getChildren().addAll(rowLabel, columnLabel, mineDensityLabel, allowUndoLabel, perlinLabel);
        statusBox.setPadding(new Insets(5, 10, 10, 10));
        statusBox.setAlignment(Pos.CENTER);

        setTop(controlBox);
        setCenter(scrollPane);
        setBottom(statusBox);
        setMargin(scrollPane, new Insets(5, 10, 5, 10));
    }

    private void genGrid(int exRow, int exCol) {

    }

    public void start(int row, int column, double mineChance, boolean allowUndo, boolean perlinNoise) {
        this.row = row;
        this.column = column;
        this.mineChance = mineChance;
        this.allowUndo = allowUndo;
        this.perlinNoise = perlinNoise;
        startTimeMillis = System.currentTimeMillis();
        elapseTimer.start();

        // Generate tiles
        if (!perlinNoise)
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    int randomIndex = ThreadLocalRandom.current().nextInt(1, 101);
                    gridPane.add(new Tile(i, j, randomIndex <= mineChance), i, j);
                }
            }
        else
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    int randomIndex = ThreadLocalRandom.current().nextInt(1, 101);
                    gridPane.add(new Tile(i, j, randomIndex <= mineChance), i, j);
                }
            }
    }

    public void clear() {
        gridPane.getChildren().clear();
        elapseTimer.stop();
    }
}
