package edu.hcmiu.minesweeper;

import javafx.beans.property.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {
    private final Stack<ArrayList<ArrayList<HashMap<String, Object>>>> undoStack = new Stack<>();
    private final IntegerProperty row = new SimpleIntegerProperty();
    private final IntegerProperty column = new SimpleIntegerProperty();
    private final DoubleProperty mineChance = new SimpleDoubleProperty();
    private final BooleanProperty allowGuessing = new SimpleBooleanProperty();
    private final BooleanProperty allowUndo = new SimpleBooleanProperty();
    private ArrayList<ArrayList<HashMap<String, Object>>> grid = new ArrayList<>();
    private boolean locked = false;
    private boolean firstSelect;

    public Grid() {
    }

    public void regen(Integer row, Integer column, Double mineChance, boolean allowUndo, boolean allowGuessing) {
        grid.clear();
        undoStack.clear();

        this.row.set(row);
        this.column.set(column);
        this.mineChance.set(mineChance);
        this.allowUndo.set(allowUndo);
        this.allowGuessing.set(allowGuessing);
        this.firstSelect = false;
        this.locked = false;

        for (int i = 0; i < row; i++) {
            ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
            for (int j = 0; j < column; j++) {
                HashMap<String, Object> valueMap = new HashMap<>();
                valueMap.put("row", i);
                valueMap.put("column", j);
                valueMap.put("hidden", true);
                valueMap.put("neighbor", 0);

                int randomIndex = ThreadLocalRandom.current().nextInt(1, 101);
                valueMap.put("bomb", randomIndex <= mineChance);

                arrayList.add(valueMap);
            }
            grid.add(arrayList);
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                grid.get(i).get(j).put("neighbor", countNeighbor(i, j));
            }
        }
    }

    public void select(Integer row, Integer column) {
        if (locked)
            return;
        if (!firstSelect && !allowGuessing.get()) {
            updateTile(row, column, "bomb", false);
            firstSelect = true;
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    grid.get(i).get(j).put("neighbor", countNeighbor(i, j));
                }
            }
        }

        if (allowUndo.get())
            addUndo();

        if (isBomb(row, column))
            return;

        reveal(0, row, column);
    }

    public void addUndo() {
        // perform deep copy
        ArrayList<ArrayList<HashMap<String, Object>>> newGrid = new ArrayList<>();
        for (ArrayList<HashMap<String, Object>> arrayList : grid) {
            ArrayList<HashMap<String, Object>> newArrayList = new ArrayList<>();
            for (HashMap<String, Object> valueMap : arrayList) {
                newArrayList.add(new HashMap<>(valueMap));
            }
            newGrid.add(newArrayList);
        }

        undoStack.push(newGrid);
    }

    public void undo() {
        if (undoStack.size() == 0)
            return;

        if (isLocked()) {
            setLocked(false);
        }

        // perform deep copy
        ArrayList<ArrayList<HashMap<String, Object>>> newGrid = new ArrayList<>();
        for (ArrayList<HashMap<String, Object>> arrayList : undoStack.pop()) {
            ArrayList<HashMap<String, Object>> newArrayList = new ArrayList<>();
            for (HashMap<String, Object> valueMap : arrayList) {
                newArrayList.add(new HashMap<>(valueMap));
            }
            newGrid.add(newArrayList);
        }

        grid = newGrid;
    }

    public void reveal(Integer depth, Integer row, Integer column) {
        if (row < 0 || row > this.row.get() - 1 || column < 0 || column > this.row.get() - 1)
            return;
        if (isHidden(row, column) || isBomb(row, column) || depth > 0)
            return;

        updateTile(row, column, "hidden", false);

        int _depth = depth;
        if (getNeighbor(row, column) != 0)
            _depth++;
        else
            _depth = 0;

        reveal(_depth, row + 1, column);
        reveal(_depth, row + 1, column + 1);
        reveal(_depth, row, column + 1);
        reveal(_depth, row - 1, column + 1);
        reveal(_depth, row - 1, column);
        reveal(_depth, row - 1, column - 1);
        reveal(_depth, row, column - 1);
        reveal(_depth, row + 1, column - 1);
    }

    public void updateTile(Integer row, Integer column, String valueName, Object value) {
        grid.get(row).get(column).put(valueName, value);
    }

    public int countNeighbor(Integer row, Integer column) {
        int neighbor = 0;
        if (isBomb(row + 1, column))
            neighbor++;
        if (isBomb(row + 1, column + 1))
            neighbor++;
        if (isBomb(row, column + 1))
            neighbor++;
        if (isBomb(row - 1, column + 1))
            neighbor++;
        if (isBomb(row - 1, column))
            neighbor++;
        if (isBomb(row - 1, column - 1))
            neighbor++;
        if (isBomb(row, column - 1))
            neighbor++;
        if (isBomb(row + 1, column - 1))
            neighbor++;
        return neighbor;
    }

    public int getNeighbor(Integer row, Integer column) {
        return (int) grid.get(row).get(column).get("neighbor");
    }

    public boolean isBomb(Integer row, Integer column) {
        if (row < 0 || row > this.row.get() - 1 || column < 0 || column > this.row.get() - 1)
            return false;
        return (boolean) grid.get(row).get(column).get("bomb");
    }

    public boolean isHidden(Integer row, Integer column) {
        return !((boolean) grid.get(row).get(column).get("hidden"));
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isWin() {
        for (ArrayList<HashMap<String, Object>> arrayList : grid) {
            for (HashMap<String, Object> valueMap : arrayList) {
                if ((Boolean) valueMap.get("hidden") && !(Boolean) valueMap.get("bomb"))
                    return false;
            }
        }
        return true;
    }

    public boolean isAllowUndo() {
        return allowUndo.get();
    }

    public IntegerProperty rowProperty() {
        return row;
    }

    public IntegerProperty columnProperty() {
        return column;
    }

    public DoubleProperty mineChanceProperty() {
        return mineChance;
    }

    public BooleanProperty allowGuessingProperty() {
        return allowGuessing;
    }

    public BooleanProperty allowUndoProperty() {
        return allowUndo;
    }
}
