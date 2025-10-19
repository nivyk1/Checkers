package com.axioma.checkers.model;

/**
 * Represents a single checkers piece on the board.
 */
public final class Piece {
    private final Color color;
    private boolean queen;

    /**
     * Creates a piece with the given color and queen status.
     * @param color non-null color of the piece
     * @param queen whether the piece is a queen
     */
    public Piece(Color color, boolean queen) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        this.color = color;
        this.queen = queen;
    }

    /**
     * Creates a non-king piece of the given color.
     * @param color non-null color of the piece
     */
    public Piece(Color color) {
        this(color, false);
    }

    /**
     * @return the color of this piece
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return true if this piece has been crowned as a king
     */
    public boolean isQueen() {
        return queen;
    }

    /**
     * Crowns this piece as a king.
     */
    public void makeQueen() {
        this.queen = true;
    }

    @Override
    public String toString() {
        return (color == Color.WHITE ? "W" : "B") + (queen ? "K" : "P");
    }
}




