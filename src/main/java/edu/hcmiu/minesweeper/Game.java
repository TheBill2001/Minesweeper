package edu.hcmiu.minesweeper;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

public class Game extends BorderPane {
    private final GridPane gridPane = new GridPane();
    private final AnimationTimer elapseTimer;
    private final Grid grid = new Grid();
    Button undoButton;
    Label timerLabel;
    private long startTimeMillis = 0;

    public Game() {
        ScrollPane scrollPane = new ScrollPane(gridPane);
        scrollPane.maxHeightProperty().bind(gridPane.heightProperty().add(2));
        scrollPane.maxWidthProperty().bind(gridPane.widthProperty().add(2));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        timerLabel = new Label();
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

        undoButton = new Button("Undo");
        undoButton.setOnAction(actionEvent -> {
            grid.undo();
            timerLabel.setText(timerLabel.getText().replace(". You lost!", ""));
            elapseTimer.start();
            updateVisual(true);
        });

        HBox.setHgrow(paddingRegion, Priority.ALWAYS);
        controlBox.getChildren().addAll(timerLabel, paddingRegion, undoButton, backButton);

        HBox statusBox = new HBox(10);

        Label rowLabel = new Label();
        rowLabel.textProperty().bind(Bindings.concat("Row: ", grid.rowProperty().asString()));
        Label columnLabel = new Label();
        columnLabel.textProperty().bind(Bindings.concat("Column: ", grid.columnProperty().asString()));
        Label mineChanceLabel = new Label();
        mineChanceLabel.textProperty().bind(Bindings.concat("Mine density: ", grid.mineChanceProperty(), "%"));
        Label allowGuessingLabel = new Label();
        allowGuessingLabel.textProperty().bind(Bindings.concat("Allow guessing: ", grid.allowGuessingProperty().asString()));
        Label allowUndoLabel = new Label("Allow undo: " + grid.isAllowUndo());
        allowUndoLabel.textProperty().bind(Bindings.concat("Allow undo: ", grid.allowUndoProperty().asString()));

        statusBox.getChildren().addAll(rowLabel, columnLabel, mineChanceLabel, allowGuessingLabel, allowUndoLabel);
        statusBox.setPadding(new Insets(5, 10, 10, 10));
        statusBox.setAlignment(Pos.CENTER);

        setTop(controlBox);
        setCenter(scrollPane);
        setBottom(statusBox);
        setMargin(scrollPane, new Insets(5, 10, 5, 10));
    }

    public void start(int row, int column, double mineChance, boolean allowUndo, boolean allowGuess) {
        startTimeMillis = System.currentTimeMillis();
        elapseTimer.start();

        grid.regen(row, column, mineChance, allowUndo, allowGuess);
        undoButton.setDisable(!allowUndo);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Tile tile = new Tile(i, j);
                tile.setOnMouseClicked(eventHandler -> {
                    switch (eventHandler.getButton()) {
                        case PRIMARY -> {
                            if (!tile.isMarked()) {
                                grid.select(tile.getRow(), tile.getColumn());
                                if (!grid.isLocked() && grid.isBomb(tile.getRow(), tile.getColumn())) {
                                    grid.setLocked(true);
                                    tile.setDetonate(true);
                                    detonate();
                                }
                                updateVisual(false);
                                checkWin();
                            }
                        }
                        case SECONDARY -> tile.setMarked(!tile.isMarked());
                    }
                });
                gridPane.add(tile, j, i);
            }
        }

        updateVisual(false);
    }

    public void detonate() {
        elapseTimer.stop();
        timerLabel.setText(timerLabel.getText() + ". You lost! Try again.");
    }

    public void checkWin() {
        if (grid.isWin()) {
            grid.setLocked(true);
            elapseTimer.stop();
            undoButton.setDisable(true);
            timerLabel.setText(timerLabel.getText() + ". You win!");
        }
    }

    private void updateVisual(boolean undo) {
        for (Node node : gridPane.getChildren()) {
            Tile tile = (Tile) node;
            tile.setDisable(grid.isHidden(tile.getRow(), tile.getColumn()));
            if (grid.isHidden(tile.getRow(), tile.getColumn())) {
                tile.setDisable(true);
                int value = grid.getNeighbor(tile.getRow(), tile.getColumn());
                if (value > 0)
                    tile.setText(String.valueOf(value));
            }
            if (undo)
                tile.setDetonate(false);
        }
    }

    public void clear() {
        gridPane.getChildren().clear();
        elapseTimer.stop();
    }
}
