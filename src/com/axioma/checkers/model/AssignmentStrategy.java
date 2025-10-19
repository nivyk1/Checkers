package com.axioma.checkers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User-implemented strategy for computer move selection.
 */
public final class AssignmentStrategy implements ComputerStrategy {

    @Override
    public List<Move> chooseMoveSequence(Board board, GameService logic) {
        @SuppressWarnings("unchecked")
        List<Move>[][] longestCaptureSequences = (List<Move>[][]) new List[Board.SIZE][Board.SIZE];
        initLongestCaptureSequences(longestCaptureSequences);
        List<Move> longestCapturedSequence = findLongestCaptureSequence(board, logic, longestCaptureSequences);
        return longestCapturedSequence.isEmpty() ? chooseRandomQuietMove(board, logic) : longestCapturedSequence;
    }

    private void initLongestCaptureSequences(List<Move>[][] longestCaptureSequences) {
        for (int r = 0; r < Board.SIZE; r++) {
            for (int c = 0; c < Board.SIZE; c++) {
                longestCaptureSequences[r][c] = new ArrayList<>();
            }
        }
    }

    private List<Move> findLongestCaptureSequence(Board board, GameService logic, List<Move>[][] longestCaptureSequences) {
        Move leftMove;
        Move rightMove;
        for (int i = Board.SIZE - 3; i >= 0; i--) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.get(i, j) == null || board.get(i, j).getColor() == Color.BLACK) {
                    leftMove = computeLeftDiagonalCapture(board, i, j);
                    rightMove = computeRightDiagonalCapture(board, i, j);

                    if (leftMove != null && rightMove == null) {
                        longestCaptureSequences[i][j].add(leftMove);
                        if (i + 2 < Board.SIZE && j - 2 >= 0)
                            longestCaptureSequences[i][j].addAll(longestCaptureSequences[i + 2][j - 2]);
                    }

                    if (rightMove != null && leftMove == null) {
                        longestCaptureSequences[i][j].add(rightMove);
                        if (i + 2 < Board.SIZE && j + 2 < Board.SIZE)
                            longestCaptureSequences[i][j].addAll(longestCaptureSequences[i + 2][j + 2]);
                    }

                    if (leftMove != null && rightMove != null) {
                        if(longestCaptureSequences[i + 2][j + 2].size() > longestCaptureSequences[i + 2][j - 2].size()) {
                            longestCaptureSequences[i][j].add(rightMove);
                            longestCaptureSequences[i][j].addAll(longestCaptureSequences[i + 2][j + 2]);
                        } else {
                            longestCaptureSequences[i][j].add(leftMove);
                            longestCaptureSequences[i][j].addAll(longestCaptureSequences[i + 2][j - 2]);
                        }
                    }
                        
                }
            }
        }

        return getLongestCaptureSequence(board, longestCaptureSequences);
    }

    private Move computeLeftDiagonalCapture(Board board, int row, int col) {
        int landRow = row + 2;
        int landCol = col - 2;
        int capturedRow = row + 1;
        int capturedCol = col - 1;

        if (!board.isInside(landRow, landCol) || !board.isInside(capturedRow, capturedCol)) return null;
        if (board.get(capturedRow, capturedCol) == null || board.get(capturedRow, capturedCol).getColor() == Color.BLACK) return null;
        if (board.get(landRow, landCol) != null) return null;
        return new Move(row, col, landRow, landCol);
    }

    private Move computeRightDiagonalCapture(Board board, int row, int col) {
        int landRow = row + 2;
        int landCol = col + 2;
        int capturedRow = row + 1;
        int capturedCol = col + 1;

        if (!board.isInside(landRow, landCol) || !board.isInside(capturedRow, capturedCol)) return null;
        if (board.get(capturedRow, capturedCol) == null || board.get(capturedRow, capturedCol).getColor() == Color.BLACK) return null;
        if (board.get(landRow, landCol) != null) return null;
        return new Move(row, col, landRow, landCol);
    }

    private List<Move> getLongestCaptureSequence(Board board, List<Move>[][] longestCaptureSequence) {
        List<List<Move>> longestCaptureSequences = new ArrayList<>();
        int maxSequenceLength = 0;
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.get(i, j) != null && board.get(i, j).getColor() == Color.BLACK) {
                    List<Move> sequence = longestCaptureSequence[i][j];
                    if (sequence.size() > maxSequenceLength) {
                        maxSequenceLength = sequence.size();
                        longestCaptureSequences = new ArrayList<>();
                        longestCaptureSequences.add(sequence);
                    }
                    if (sequence.size() == maxSequenceLength) {
                        longestCaptureSequences.add(sequence);
                    }
                }
            }
        }
        if (!longestCaptureSequences.isEmpty()) {
            return chooseRandomSequence(longestCaptureSequences);
        }
        return new ArrayList<>();
    }

    private List<Move> chooseRandomSequence(List<List<Move>> longestCaptureSequences) {
        Random random = new Random();
        return longestCaptureSequences.get(random.nextInt(longestCaptureSequences.size()));
    }

    private List<Move> getQuietMovesForBlack(Board board) {
        List<Move> quietMoves = new ArrayList<>();
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                if (board.isInside(i + 1, j + 1) && board.isInside(i + 2, j + 2)) {
                    if (board.get(i, j) != null && board.get(i, j).getColor() == Color.BLACK) {
                        if (board.get(i + 1, j + 1) == null) {
                            if ((board.get(i + 2, j + 2) != null && board.get(i + 2, j + 2).getColor() != Color.WHITE) || board.get(i + 2, j + 2) == null) {
                                quietMoves.add(new Move(i, j, i + 1, j + 1));
                            }
                        }
                    }
                }
                if (board.isInside(i + 1, j - 1) && board.isInside(i + 2, j - 2)) {
                    if (board.get(i, j) != null && board.get(i, j).getColor() == Color.BLACK) {
                        if (board.get(i + 1, j - 1) == null) {
                            if ((board.get(i + 2, j - 2) != null && board.get(i + 2, j - 2).getColor() != Color.WHITE) || board.get(i + 2, j - 2) == null) {
                                quietMoves.add(new Move(i, j, i + 1, j - 1));
                            }
                        }
                    }
                }
            }
        }
        return quietMoves;
    }

    private List<Move> chooseRandomQuietMove(Board board, GameService logic) {
        List<Move> quietMoves = getQuietMovesForBlack(board);
        Random random = new Random();
        List<Move> quietMovesResult = new ArrayList<>();
        if (!quietMoves.isEmpty()) {
            quietMovesResult.add(quietMoves.get(random.nextInt(quietMoves.size())));
        }
        return quietMovesResult;
    }
}


