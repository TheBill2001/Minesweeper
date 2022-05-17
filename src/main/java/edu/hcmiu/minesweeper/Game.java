package edu.hcmiu.minesweeper;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class Game extends BorderPane {
    private final int row;
    private final int column;
    private final double mineDensity;
    private final boolean allowGuessing;
    private final boolean allowUndo;
    private final boolean perlinNoise;
    private final long startTimeMillis;
    private long elapsedTimeMillis;

    public Game(int row, int column, double mineDensity, boolean allowGuessing, boolean allowUndo, boolean perlinNoise) {
        this.row = row;
        this.column = column;
        this.mineDensity = mineDensity;
        this.allowGuessing = allowGuessing;
        this.allowUndo = allowUndo;
        this.perlinNoise = perlinNoise;
        this.startTimeMillis = System.currentTimeMillis();

        createUI();
    }

    private void createUI() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(1));
        gridPane.setHgap(1);
        gridPane.setVgap(1);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Button button = new Button();
                button.setPrefSize(30,30);
                gridPane.add(button, j, i);
            }
        }

        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.maxHeightProperty().bind(gridPane.heightProperty().add(2));
        scrollPane.maxWidthProperty().bind(gridPane.widthProperty().add(2));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        HBox controlBox = new HBox(10);
        Label timerLabel = new Label();

        new AnimationTimer() {
            @Override
            public void handle(long l) {
                elapsedTimeMillis = (System.currentTimeMillis() - startTimeMillis) / 1000;
                timerLabel.setText(String.format("Elapsed time: %02d:%02d:%02d", elapsedTimeMillis / 3600, (elapsedTimeMillis % 3600) / 60, (elapsedTimeMillis % 3600) % 60));
            }
        }.start();

        Region paddingRegion = new Region();

        controlBox.setPadding(new Insets(10,10,5,10));
        controlBox.setAlignment(Pos.CENTER);

        Button backButton = new Button("Back");
        backButton.setOnAction(actionEvent -> getScene().setRoot(new GameSetting()));

        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");

        HBox.setHgrow(paddingRegion, Priority.ALWAYS);
        controlBox.getChildren().addAll(timerLabel, paddingRegion, undoButton, redoButton, backButton);

        HBox statusBox = new HBox(10);

        Label rowLabel = new Label("Row: " + getRow());
        Label columnLabel = new Label("Column: " + getColumn());
        Label mineDensityLabel = new Label("Mine density: " + getMineDensity() + "%");
        Label allowGuessingLabel = new Label("Allow guessing: " + isAllowGuessing());
        Label allowUndoLabel = new Label("Allow undo: " + isAllowUndo());
        Label perlinLabel = new Label("Perlin noise: " + isPerlinNoise());

        statusBox.getChildren().addAll(rowLabel, columnLabel, mineDensityLabel, allowGuessingLabel, allowUndoLabel, perlinLabel);
        statusBox.setPadding(new Insets(5,10,10,10));
        statusBox.setAlignment(Pos.CENTER);

        setTop(controlBox);
        setCenter(scrollPane);
        setBottom(statusBox);
        setMargin(scrollPane, new Insets(5,10,5,10));
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public double getMineDensity() {
        return mineDensity;
    }

    public boolean isAllowGuessing() {
        return allowGuessing;
    }

    public boolean isAllowUndo() {
        return allowUndo;
    }

    public boolean isPerlinNoise() {
        return perlinNoise;
    }

    public long getStartTimeMillis() {
        return startTimeMillis;
    }

    public long getElapsedTimeMillis() {
        return elapsedTimeMillis;
    }
}
