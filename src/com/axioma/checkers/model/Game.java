package com.axioma.checkers.model;

/**
 * Holds the mutable game state (turn, finished, winner) separate from rules.
 */
public final class Game {
    private Color currentTurn;
    private boolean finished;
    private Color winner; // may be null when not finished

    public Game() {
        this.currentTurn = Color.WHITE; // Human starts
        this.finished = false;
        this.winner = null;
    }

    public Color getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(Color currentTurn) {
        this.currentTurn = currentTurn;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Color getWinner() {
        return winner;
    }

    public void setWinner(Color winner) {
        this.winner = winner;
    }
}




