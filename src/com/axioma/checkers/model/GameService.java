
package com.axioma.checkers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides rules validation and move generation for Checkers.
 */
public final class GameService {

    /**
     * Returns all legal moves for a given player on the given board.
     */
    public List<Move> getLegalMoves(Board board, Color player) {
        List<Move> captureMoves = new ArrayList<>();
        List<Move> quietMoves = new ArrayList<>();

        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece piece = board.get(r, c);
                if (piece == null || piece.getColor() != player) continue;
                addPieceMoves(board, r, c, piece, captureMoves, quietMoves);
            }
        }
        List<Move> legalMoves = new ArrayList<>();
        legalMoves.addAll(captureMoves);
        legalMoves.addAll(quietMoves);
       return legalMoves;
    }


    /**
     * Checks if the provided move is legal for the player.
     */
    public boolean isLegalMove(Board board, Move move, Color player) {
        List<Move> legal = getLegalMoves(board, player);
        return legal.contains(move);
    }

    /**
     * Applies a legal move to the board, handles captures and crowning.
     */
    public void applyMove(Board board, Move move) {
        Piece moving = board.get(move.getFromRow(), move.getFromCol());
        board.set(move.getFromRow(), move.getFromCol(), null);
        board.set(move.getToRow(), move.getToCol(), moving);

        if (move.isCapture()) {
            int midRow = (move.getFromRow() + move.getToRow()) / 2;
            int midCol = (move.getFromCol() + move.getToCol()) / 2;
            board.set(midRow, midCol, null);
        }

        // No crowning: variant rule ends the game when a piece reaches last rank
    }

    /**
     * Updates the game finished state and winner if applicable.
     * Returns true if the game is over.
     */
    public boolean checkGameOver(Board board, Game game) {
        if (game.isFinished()) return true;

        // Variant rule: first to reach the opposite back rank wins immediately (queen condition)
        if (hasPieceOnRow(board, 0, Color.WHITE)) {
            game.setFinished(true);
            game.setWinner(Color.WHITE);
            return true;
        }
        if (hasPieceOnRow(board, Board.SIZE - 1, Color.BLACK)) {
            game.setFinished(true);
            game.setWinner(Color.BLACK);
            return true;
        }

        boolean whiteHasMoves = !getLegalMoves(board, Color.WHITE).isEmpty();
        boolean blackHasMoves = !getLegalMoves(board, Color.BLACK).isEmpty();

        boolean whiteHasPieces = hasPieces(board, Color.WHITE);
        boolean blackHasPieces = hasPieces(board, Color.BLACK);

        if (!whiteHasPieces || !whiteHasMoves) {
            game.setFinished(true);
            game.setWinner(Color.BLACK);
            return true;
        }
        if (!blackHasPieces || !blackHasMoves) {
            game.setFinished(true);
            game.setWinner(Color.WHITE);
            return true;
        }
        return false;
    }

    private boolean hasPieces(Board board, Color color) {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                Piece p = board.get(r, c);
                if (p != null && p.getColor() == color) return true;
            }
        }
        return false;
    }

    private boolean hasPieceOnRow(Board board, int row, Color color) {
        for (int c = 0; c < Board.SIZE; c++) {
            Piece p = board.get(row, c);
            if (p != null && p.getColor() == color) return true;
        }
        return false;
    }

    private void addPieceMoves(Board board, int r, int c, Piece piece, List<Move> captures, List<Move> quiets) {
        // Directions: for men, white moves up (-1), black moves down (+1); kings both ways
        addPieceCaptures(board,r,c,piece,captures);
        addPieceQuietMoves(board,r,c,piece,quiets);
        

    }

    private void addPieceCaptures(Board board, int r, int c, Piece piece, List<Move> captures) {
        // Forward-only captures for all pieces in this variant
        int forward = piece.getColor() == Color.WHITE ? -1 : 1;
        int[] dc = new int[]{-1, 1};
        for (int dcol : dc) {
            int midR = r + forward;
            int midC = c + dcol;
            int landR = r + 2 * forward;
            int landC = c + 2 * dcol;
            if (board.isInside(landR, landC) && board.isInside(midR, midC)) {
                Piece midPiece = board.get(midR, midC);
                if (midPiece != null && midPiece.getColor() != piece.getColor() && board.get(landR, landC) == null) {
                    captures.add(new Move(r, c, landR, landC));
                }
            }
        }
    }

    private void addPieceQuietMoves(Board board, int r, int c, Piece piece, List<Move> quiets) {
        // Forward-only quiet moves for all pieces in this variant
        int forward = piece.getColor() == Color.WHITE ? -1 : 1;
        int[] dc = new int[]{-1, 1};
        for (int dcol : dc) {
            int nr = r + forward;
            int nc = c + dcol;
            if (board.isInside(nr, nc) && board.get(nr, nc) == null) {
                quiets.add(new Move(r, c, nr, nc));
            }
        }
    }


}


