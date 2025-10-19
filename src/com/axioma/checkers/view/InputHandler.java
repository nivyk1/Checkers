package com.axioma.checkers.view;

import com.axioma.checkers.model.Move;

import java.util.Locale;
import java.util.Scanner;

/**
 * Reads and parses user input for moves like "A3->B4".
 */
public final class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user and parses a move in the format "A3->B4".
     */
    public Move promptMove() {
        System.out.print("Enter your move (e.g., A3->B4), or 'q' to quit: ");
        String line = scanner.nextLine();
        if (line == null) return null;
        line = line.trim();
        if (line.equalsIgnoreCase("q")) return null;
        Move move = parseMove(line);
        if (move == null) {
            System.out.println("Invalid format. Use like A3->B4.");
        }
        return move;
    }

    /**
     * Prompts and returns the raw input line (trimmed). May return null on EOF.
     */
    public String promptLine() {
        System.out.print("Enter your move (e.g., A3->B4), or 'q' to quit: ");
        String line = scanner.nextLine();
        return line == null ? null : line.trim();
    }

    /**
     * Parses a move string like "A3->B4" into a Move.
     */
    public Move parseMove(String text) {
        if (text == null) return null;
        String s = text.trim().toUpperCase(Locale.ROOT);
        String[] parts = s.split("->");
        if (parts.length != 2) return null;
        int[] from = parseCell(parts[0]);
        int[] to = parseCell(parts[1]);
        if (from == null || to == null) return null;
        return new Move(from[0], from[1], to[0], to[1]);
    }

    // Converts like "A3" to (row, col) 0-based where A1 is bottom-left from White's perspective
    private int[] parseCell(String cell) {
        if (cell.length() < 2) return null;
        char colChar = cell.charAt(0);
        if (colChar < 'A' || colChar > 'H') return null;
        String rowPart = cell.substring(1);
        int rowNum;
        try {
            rowNum = Integer.parseInt(rowPart);
        } catch (NumberFormatException ex) {
            return null;
        }
        if (rowNum < 1 || rowNum > 8) return null;
        int col = colChar - 'A';
        int row = 8 - rowNum; // map display 1..8 to internal 7..0
        return new int[]{row, col};
    }
}




