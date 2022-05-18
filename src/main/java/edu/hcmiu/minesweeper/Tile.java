package edu.hcmiu.minesweeper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Button;

public class Tile extends Button {
    private final int row;
    private final int column;
    private boolean detonated = false;
    private final BooleanProperty marked = new SimpleBooleanProperty();

    public Tile(Integer row, Integer column) {
        this.row = row;
        this.column = column;
        this.marked.setValue(false);

        setPrefSize(30, 30);
        setStyle("-fx-background-color: #ffffff; -fx-border-color: #0000ff; -fx-font-weight: bold");

        marked.addListener(changeListener -> setStyle("-fx-background-color: " + (!isMarked() ? "#ffffff" : "#ffff00") + "; -fx-border-color: #0000ff; -fx-font-weight: bold"));
        disabledProperty().addListener(changeListener -> {
            if (!disabledProperty().getValue())
                setText("");
        });
    }

    public void setDetonate(boolean val) {
        detonated = val;
        if (val)
            setStyle("-fx-background-color: #ff0000; -fx-border-color: #0000ff; -fx-font-weight: bold");
        else
            setStyle("-fx-background-color: #ffffff; -fx-border-color: #0000ff; -fx-font-weight: bold");
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isMarked() {
        return marked.getValue();
    }

    public boolean isDetonated() {
        return detonated;
    }

    public void setMarked(Boolean marked) {
        this.marked.setValue(marked);
    }
}
