package edu.hcmiu.minesweeper;

import javafx.scene.layout.VBox;

public class Game extends VBox {
    private final int row;
    private final int column;
    private final double mineDensity;
    private final boolean allowGuessing;
    private final boolean allowUndo;
    private final boolean perlinNoise;

    public Game(int row, int column, double mineDensity, boolean allowGuessing, boolean allowUndo, boolean perlinNoise) {
        this.row = row;
        this.column = column;
        this.mineDensity = mineDensity;
        this.allowGuessing = allowGuessing;
        this.allowUndo = allowUndo;
        this.perlinNoise = perlinNoise;
    }
}
