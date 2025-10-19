package com.axioma.checkers.model;

import java.util.List;

/**
 * Strategy for computer move selection.
 */
public interface ComputerStrategy {
    /**
     * Chooses a full move sequence for this turn. If captures exist, returns the longest
     * capture sequence for some piece; otherwise returns a single quiet move.
     */
    List<Move> chooseMoveSequence(Board board, GameService logic);
}





