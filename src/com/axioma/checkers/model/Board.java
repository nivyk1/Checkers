package com.axioma.checkers.model;

import java.util.Arrays;

/**
 * Represents the 8x8 board with nullable {@link Piece} cells.
 */
public final class Board {
    public static final int SIZE = 8;

    private final Piece[][] cells;

    /**
     * Creates an empty board.
     */
    public Board() {
        this.cells = new Piece[SIZE][SIZE];
    }

    /**
     * Copy constructor for defensive copying.
     */
    public Board(Board other) {
        this();
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Piece piece = other.cells[r][c];
                if (piece != null) {
                    this.cells[r][c] = new Piece(piece.getColor(), piece.isQueen());
                }
            }
        }
    }

    /**
     * Initializes the board to the standard starting setup.
     */
    public void setupInitial() {
        for (int r = 0; r < SIZE; r++) {
            Arrays.fill(cells[r], null);
        }
        // Place black pieces on rows 0,1,2 on dark squares
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < SIZE; c++) {
                if ((r + c) % 2 == 1) {
                    cells[r][c] = new Piece(Color.BLACK);
                }
            }
        }
        // Place white pieces on rows 5,6,7 on dark squares
        for (int r = SIZE - 3; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if ((r + c) % 2 == 1) {
                    cells[r][c] = new Piece(Color.WHITE);
                }
            }
        }
    }

    public Piece get(int row, int col) {
        return cells[row][col];
    }

    public void set(int row, int col, Piece piece) {
        cells[row][col] = piece;
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE;
    }
}




