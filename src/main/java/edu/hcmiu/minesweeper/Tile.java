package edu.hcmiu.minesweeper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;

public class Tile extends Button {
    private final int row;
    private final int column;
    private final boolean bomb;
    private final BooleanProperty marked = new SimpleBooleanProperty();

    public Tile(Integer row, Integer column, Boolean bomb) {
        this.row = row;
        this.column = column;
        this.bomb = bomb;
        this.marked.setValue(false);

        setPrefSize(30,30);
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #0000ff;");

        marked.addListener(changeListener -> setStyle("-fx-background-color: " + (!isMarked() ? "#ffffff" : "#ffff00") + "; -fx-border-color: #0000ff;"));

        disableProperty().addListener(changeListener -> setStyle("-fx-background-color: " + (this.bomb ? "#ff0000" : "#ffffff") + "; -fx-border-color: #0000ff;"));

        setOnMouseClicked(mouseEvent -> {
            switch (mouseEvent.getButton()) {
                case PRIMARY -> {
                    if (!isMarked())
                        setDisable(true);
                }
                case SECONDARY -> marked.setValue(!isMarked());
            }
        });
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isBomb() {
        return bomb;
    }

    public boolean isMarked() {
        return marked.getValue();
    }

    public BooleanProperty markedProperty() {
        return marked;
    }

    public void setMarked(Boolean marked) {
        this.marked.setValue(marked);
    }

    public void setStates(Boolean enable, Boolean marked) {
        setDisable(!enable);
        setMarked(marked);
    }
}
