package com.axioma.checkers.controller;



import com.axioma.checkers.model.*;
import com.axioma.checkers.view.InputHandler;
import com.axioma.checkers.view.Renderer;

/**
 * Orchestrates the main game loop.
 */
public final class GameController {
    private final Board board;
    private final Game game;
    private final GameService logic;
    private final Renderer renderer;
    private final InputHandler input;
    private final ComputerStrategy ai;

    

    
    public GameController(Board board, Game game, GameService logic, Renderer renderer, InputHandler input, ComputerStrategy ai) {
        this.board = board;
        this.game = game;
        this.logic = logic;
        this.renderer = renderer;
        this.input = input;
        this.ai = ai;
    }
    

    /**
     * Runs the console game loop until it finishes.
     */
    public void run() {
        renderer.showMessage("Welcome to Checkers (Queen race variant)!");
        renderer.showMessage("First piece to reach the opponent's back rank wins immediately.");
        while (!game.isFinished()) {
            renderer.renderBoard(board);
            renderer.renderStatus(game);

            Color turn = game.getCurrentTurn();
            if (turn == Color.WHITE) {
                if (!handleHumanTurn()) break; // user may quit
            } else {
                handleComputerTurn();
            }

            if (logic.checkGameOver(board, game)) {
                renderer.renderBoard(board);
                announceWinner();
                break;
            }
            game.setCurrentTurn(game.getCurrentTurn().opposite());
        }
    }

    private boolean handleHumanTurn() {
        var legal = logic.getLegalMoves(board, Color.WHITE);
        if (legal.isEmpty()) {
            game.setFinished(true);
            game.setWinner(Color.BLACK);
            return true;
        }
        while (true) {
            String line = input.promptLine();
            if (line == null || line.equalsIgnoreCase("q")) {
                renderer.showMessage("Goodbye!");
                return false; // user quit
            }
            Move move = input.parseMove(line);
            if (move == null) {
                renderer.showMessage("Invalid input. Use format like A3->B4.");
                continue;
            }
            if (logic.isLegalMove(board, move, Color.WHITE)) {
                logic.applyMove(board, move);
                return true;
            }
            renderer.showMessage("Illegal move. Try again.");
        }
    }

    private void handleComputerTurn() {
      
        var sequence = ai.chooseMoveSequence(board, logic);
        if (sequence != null && !sequence.isEmpty()) {
            renderer.showMessage("Computer plays: " + formatSequence(sequence));
            for (var m : sequence) {
                logic.applyMove(board, m);
            }
        }
        if (sequence.isEmpty()) {
            game.setFinished(true);
            game.setWinner(Color.WHITE);
            
        }
    
        }
    

    private void announceWinner() {
        if (game.getWinner() == Color.WHITE) {
            renderer.showMessage("White wins by reaching the back rank! (You)");
        } else if (game.getWinner() == Color.BLACK) {
            renderer.showMessage("Black wins by reaching the back rank! (Computer)");
        } else {
            renderer.showMessage("Game over.");
        }
    }

    private String formatSequence(java.util.List<Move> sequence) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sequence.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(formatMove(sequence.get(i)));
        }
        return sb.toString();
    }

    private String formatMove(Move m) {
        char fromCol = (char) ('A' + m.getFromCol());
        int fromRow = Board.SIZE - m.getFromRow();
        char toCol = (char) ('A' + m.getToCol());
        int toRow = Board.SIZE - m.getToRow();
        return new StringBuilder()
                .append(fromCol).append(fromRow)
                .append("->")
                .append(toCol).append(toRow)
                .toString();
    }
}



