package com.axioma.checkers.model;

/**
 * Represents a single checkers piece on the board.
 */
public final class Piece {
    private final Color color;
    

    /**
     * Creates a piece with the given color and queen status.
     * @param color non-null color of the piece
     
     */
    public Piece(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("color cannot be null");
        }
        this.color = color;
        
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


   

    @Override
    public String toString() {
        return (color == Color.WHITE ? "W" : "B") ;
    }
}




