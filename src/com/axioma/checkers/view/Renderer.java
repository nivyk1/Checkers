package com.axioma.checkers.view;

import com.axioma.checkers.model.Board;
import com.axioma.checkers.model.Color;
import com.axioma.checkers.model.Game;
import com.axioma.checkers.model.Piece;

/**
 * Console renderer for the checkers board and messages.
 */
public final class Renderer {

    /**
     * Renders the board to the console with coordinates.
     */
    public void renderBoard(Board board) {
        System.out.println();
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println("  +---+---+---+---+---+---+---+---+");
        for (int r = 0; r < Board.SIZE; r++) {
            int displayRow = Board.SIZE - r;
            StringBuilder row = new StringBuilder();
            row.append(displayRow).append(" |");
            for (int c = 0; c < Board.SIZE; c++) {
                Piece piece = board.get(r, c);
                char cellChar = toCellChar(piece, r, c);
                row.append(" ").append(cellChar).append(" |");
            }
            row.append(" ").append(displayRow);
            System.out.println(row);
            System.out.println("  +---+---+---+---+---+---+---+---+");
        }
        System.out.println("    A   B   C   D   E   F   G   H");
        System.out.println();
    }

    /**
     * Prints a status line indicating whose turn it is.
     */
    public void renderStatus(Game game) {
        Color turn = game.getCurrentTurn();
        System.out.println("Turn: " + (turn == Color.WHITE ? "White (You)" : "Black (Computer)"));
    }

    /**
     * Prints an arbitrary message.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    private char toCellChar(Piece piece, int r, int c) {
        if (piece != null) {
            if (piece.getColor() == Color.WHITE) {
                return piece.isQueen() ? 'W' : 'w';
            } else {
                return piece.isQueen() ? 'B' : 'b';
            }
        }
        // Dark squares are playable where (r+c) is odd.
        return ((r + c) % 2 == 1) ? '.' : ' ';
    }
}




