package com.axioma.checkers.model;

/**
 * Represents the piece color in the game.
 */
public enum Color {
    WHITE,
    BLACK;

    /**
     * Returns the opposite color.
     * @return the opposite {@link Color}
     */
    public Color opposite() {
        return this == WHITE ? BLACK : WHITE;
    }
}




