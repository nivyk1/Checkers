package com.axioma.checkers.model;

import java.util.Objects;

/**
 * Represents a single move from one square to another.
 */
public final class Move {
    private final int fromRow;
    private final int fromCol;
    private final int toRow;
    private final int toCol;

    /**
     * Creates a move from (fromRow, fromCol) to (toRow, toCol).
     */
    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public int getFromRow() { return fromRow; }
    public int getFromCol() { return fromCol; }
    public int getToRow() { return toRow; }
    public int getToCol() { return toCol; }

    /**
     * @return true if this move is a capture (jump) by distance
     */
    public boolean isCapture() {
        return Math.abs(toRow - fromRow) == 2 && Math.abs(toCol - fromCol) == 2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Move)) return false;
        Move move = (Move) o;
        return fromRow == move.fromRow && fromCol == move.fromCol && toRow == move.toRow && toCol == move.toCol;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromRow, fromCol, toRow, toCol);
    }

    @Override
    public String toString() {
        return "(" + fromRow + "," + fromCol + ") -> (" + toRow + "," + toCol + ")";
    }
}




